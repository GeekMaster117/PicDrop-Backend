package com.geekmaster117.springweb.services;

import com.geekmaster117.springweb.enums.AuthProviders;
import com.geekmaster117.springweb.models.User;
import com.geekmaster117.springweb.models.UserPrincipal;
import com.geekmaster117.springweb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService
{
    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<User> userOptional = this.repo.findByUsernameAndAuthProvider(username, AuthProviders.LOCAL);
        if(userOptional.isEmpty())
            throw new UsernameNotFoundException("Username: " + username + "not found");

        User user = userOptional.get();
        return new UserPrincipal(user);
    }
}
