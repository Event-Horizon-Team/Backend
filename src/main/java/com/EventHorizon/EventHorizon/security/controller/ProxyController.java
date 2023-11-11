package com.EventHorizon.EventHorizon.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proxy")
@RequiredArgsConstructor
public class ProxyController {

    private final ProxyService proxyService;

    @PostMapping("/basicSignUp")
    public ResponseEntity<AuthenticationResponse> basicSignUp(
            @RequestBody RegisterRequest registerRequest
    ){
        return new ResponseEntity<AuthenticationResponse>(proxyService.signUp(registerRequest),HttpStatus.OK);
    }
    @PostMapping("/basicSignIn")
    public ResponseEntity<AuthenticationResponse> basicSignIn(
            @RequestBody AuthenticationRequest authenticationRequest
    ){
        return new ResponseEntity<AuthenticationResponse>(proxyService.signIn(authenticationRequest),HttpStatus.OK);
    }


}
