package com.application.piunivesp.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private Long id;

    private String name;

    private String email;

    private String access_token;

    private String refresh_token;
}
