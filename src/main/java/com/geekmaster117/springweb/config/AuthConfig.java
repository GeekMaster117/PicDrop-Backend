package com.geekmaster117.springweb.config;

import com.geekmaster117.springweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AuthConfig
{
    @Autowired
    private UserService service;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Bean
    public AuthenticationManager authManager()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(this.service);
        provider.setPasswordEncoder(encoder);

        return (new ProviderManager(provider));
    }
}
