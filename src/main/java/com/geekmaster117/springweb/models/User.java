package com.geekmaster117.springweb.models;

import com.geekmaster117.springweb.enums.AuthProviders;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Entity
@Data
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "auth_provider"})
})
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "auth_provider", nullable = false)
    private AuthProviders authProvider;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Image> images;

    public User(String username, String password, AuthProviders authProvider)
    {
        this.username = username;
        this.password = password;
        this.authProvider = authProvider;
    }
}
