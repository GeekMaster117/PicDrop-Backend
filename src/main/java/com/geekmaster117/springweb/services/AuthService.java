package com.geekmaster117.springweb.services;

import com.geekmaster117.springweb.enums.AuthProviders;
import com.geekmaster117.springweb.models.CredentialsDTO;
import com.geekmaster117.springweb.models.User;
import com.geekmaster117.springweb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService
{
    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public boolean registerUser(CredentialsDTO credentialsDTO)
    {
        if(this.repo.existsById(credentialsDTO.getUsername()))
            return false;

        User user = new User(credentialsDTO.getUsername(),
                credentialsDTO.getPassword(),
                AuthProviders.LOCAL);

        user.setPassword(this.encoder.encode(user.getPassword()));

        this.repo.save(user);

        return true;
    }
}
