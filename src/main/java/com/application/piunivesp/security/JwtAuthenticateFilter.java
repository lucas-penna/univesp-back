package com.application.piunivesp.security;

import com.application.piunivesp.model.User;
import com.application.piunivesp.security.model.AuthenticationResponse;
import com.application.piunivesp.security.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticateFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticateFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), user.getAuthorities()));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar usuário", e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        ModelMapper modelMapper = new ModelMapper();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        User user = (User) authResult.getPrincipal();
        TokenUtil tokenUtil = new TokenUtil();
        String access_token = tokenUtil.generateToken(user);

        String refresh_token = tokenUtil.generateRefreshToken(user);;

        AuthenticationResponse authenticationResponse = modelMapper.map(user, AuthenticationResponse.class);
        authenticationResponse.setAccess_token(access_token);
        authenticationResponse.setRefresh_token(refresh_token);

        response.setContentType("application/json");

        response.getWriter().write(ow.writeValueAsString(authenticationResponse));
        response.getWriter().flush();
    }

}
