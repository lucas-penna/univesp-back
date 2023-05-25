package com.application.piunivesp.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthControler {

    @GetMapping(value = "/check-token")
    public ResponseEntity<Void> checkToken() {
        return ResponseEntity.ok().build();
    }

}
