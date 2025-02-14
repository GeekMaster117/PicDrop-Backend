package com.geekmaster117.springweb.services;

import com.geekmaster117.springweb.enums.AuthProviders;
import com.geekmaster117.springweb.exceptions.UnsupportedOAuth2ProviderException;
import com.geekmaster117.springweb.models.User;
import com.geekmaster117.springweb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService
{
    @Autowired
    private UserRepository repository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException
    {
        OAuth2User oAuth2User = super.loadUser(request);

        String username = (String) oAuth2User.getAttribute("name");

        AuthProviders authProvider = switch (request.getClientRegistration().getRegistrationId())
        {
            case "google" -> AuthProviders.GOOGLE;
            default -> throw new UnsupportedOAuth2ProviderException(request.getClientRegistration().getRegistrationId());
        };

        if(!this.repository.existsByUsernameAndAuthProvider(username, authProvider))
        {
            User user = new User();
            user.setUsername(username);
            user.setPassword("");
            user.setAuthProvider(authProvider);
            this.repository.save(user);
        }

        return oAuth2User;
    }
}
