package com.geekmaster117.springweb.data;

import com.geekmaster117.springweb.enums.AuthProviders;
import com.geekmaster117.springweb.models.User;
import com.geekmaster117.springweb.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder
{
    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostConstruct
    public void seedData()
    {
        final List<User> users = List.of(
                new User("Munna", this.encoder.encode("Munna@123"), AuthProviders.LOCAL),
                new User("Sai Venkat", this.encoder.encode("SaiVenkat@123"), AuthProviders.LOCAL)
        );

        this.repo.saveAll(users);
    }
}
