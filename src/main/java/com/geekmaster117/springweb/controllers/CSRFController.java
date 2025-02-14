package com.geekmaster117.springweb.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("csrf")
public class CSRFController
{
    @GetMapping
    public ResponseEntity<CsrfToken> getCsrfToken(HttpServletRequest request)
    {
        return new ResponseEntity<>((CsrfToken) request.getAttribute("_csrf"), HttpStatus.OK);
    }
}