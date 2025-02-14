package com.geekmaster117.springweb.controllers;

import com.geekmaster117.springweb.models.CredentialsDTO;
import com.geekmaster117.springweb.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController
{
    @Autowired
    private AuthService service;

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody CredentialsDTO credentialsDTO)
    {
        if(this.service.registerUser(credentialsDTO))
            return new ResponseEntity<>(
                    "Username: " + credentialsDTO.getUsername() + " has been successfully registered",
                    HttpStatus.OK);
        return new ResponseEntity<>("Username: " + credentialsDTO.getUsername() + " already exists",
                HttpStatus.CONFLICT);
    }
}
