package com.application.piunivesp.security;

import com.application.piunivesp.security.util.TokenUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public static final String HEADER_ATTRIBUTE = "Authorization";

    public static final String PREFIX_ATTRIBUTE = "Bearer ";

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String atributte = request.getHeader(HEADER_ATTRIBUTE);
        if (atributte == null) {
            chain.doFilter(request, response);
            return;
        }

        if (!atributte.startsWith(PREFIX_ATTRIBUTE)) {
            chain.doFilter(request, response);
            return;
        }

        String token = atributte.replace(PREFIX_ATTRIBUTE, "");

        UsernamePasswordAuthenticationToken authenticationToken = null;
        try {
            authenticationToken = getAuthenticationToken(token);
        } catch (TokenExpiredException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {

        TokenUtil tokenUtil = new TokenUtil();

        String email = tokenUtil.getEmailFromToken(token);
        if (email == null) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
    }
}
