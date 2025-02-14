package com.geekmaster117.springweb.repositories;

import com.geekmaster117.springweb.enums.AuthProviders;
import com.geekmaster117.springweb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>
{
    boolean existsByUsernameAndAuthProvider(String username, AuthProviders authProvider);

    Optional<User> findByUsernameAndAuthProvider(String username, AuthProviders authProvider);
}
