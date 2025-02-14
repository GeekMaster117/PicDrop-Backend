package com.geekmaster117.springweb.services;

import com.geekmaster117.springweb.enums.AuthProviders;
import com.geekmaster117.springweb.exceptions.UserNotFoundException;
import com.geekmaster117.springweb.exceptions.UnsupportedOAuth2ProviderException;
import com.geekmaster117.springweb.models.Image;
import com.geekmaster117.springweb.models.ImageDTO;
import com.geekmaster117.springweb.models.User;
import com.geekmaster117.springweb.repositories.ImageRepository;
import com.geekmaster117.springweb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService
{
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private void sendUpdateToUser()
    {
        this.messagingTemplate.convertAndSend("receive", "Car List Updated");
    }

    private AuthProviders getAuthProvider(Authentication authentication)
    {
        if(authentication instanceof OAuth2AuthenticationToken token)
        {
            return switch(token.getAuthorizedClientRegistrationId())
            {
                case "google" -> AuthProviders.GOOGLE;
                default -> throw new UnsupportedOAuth2ProviderException(token.getAuthorizedClientRegistrationId());
            };
        }
        return AuthProviders.LOCAL;
    }

    private String getUsername(Authentication authentication)
    {
        if(authentication.getPrincipal() instanceof OAuth2User oAuth2User)
            return (String) oAuth2User.getAttribute("name");
        return authentication.getName();
    }

    public int addImage(MultipartFile imageFile, Authentication authentication)
    {
        String username = this.getUsername(authentication);
        AuthProviders authProvider = this.getAuthProvider(authentication);

        User user = this.userRepository.findByUsernameAndAuthProvider(username, authProvider)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found for username: " + username
                                + " and auth provider: " + authProvider.name())
                );

        String contentType = imageFile.getContentType();
        if(contentType == null || !contentType.startsWith("image/"))
            return 2;

        byte[] imageData;
        try
        {
            imageData = imageFile.getBytes();
        }
        catch(IOException e)
        {
            return 3;
        }

        Image image = new Image();
        image.setProfilePhotoData(imageData);
        image.setContentType(contentType);
        image.setUser(user);

        this.imageRepository.save(image);

        this.sendUpdateToUser();

        return 1;
    }

    public Optional<List<ImageDTO>> getImageInfo(Authentication authentication)
    {
        String username = this.getUsername(authentication);
        AuthProviders authProvider = this.getAuthProvider(authentication);

        long userId = this.userRepository.findByUsernameAndAuthProvider(username, authProvider)
                .map(User::getId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found for username: " + username
                        + " and auth provider: " + authProvider.name())
                );

        List<Image> images = this.imageRepository.findByUser_Id(userId);
        if(images.isEmpty())
            return Optional.empty();

        List<ImageDTO> imageDTOs = images.stream()
                .map(Image::toDTO)
                .toList();

        return Optional.of(imageDTOs);
    }

    public Optional<byte[]> getImageById(int id, Authentication authentication)
    {
        String username = this.getUsername(authentication);
        AuthProviders authProvider = this.getAuthProvider(authentication);

        long userId = this.userRepository.findByUsernameAndAuthProvider(username, authProvider)
                .map(User::getId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found for username: " + username
                                + " and auth provider: " + authProvider.name())
                );

        return this.imageRepository.findByUser_IdAndId(userId, id).map(Image::getProfilePhotoData);
    }

    public boolean deleteImageById(int id, Authentication authentication)
    {
        String username = this.getUsername(authentication);
        AuthProviders authProvider = this.getAuthProvider(authentication);

        long userId = this.userRepository.findByUsernameAndAuthProvider(username, authProvider)
                .map(User::getId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found for username: " + username
                                + " and auth provider: " + authProvider.name())
                );

        if(!this.imageRepository.existsByUser_IdAndId(userId, id))
            return false;

        this.imageRepository.deleteById(id);

        this.sendUpdateToUser();

        return true;
    }

    public int updateImageById(MultipartFile imageFile, int id, Authentication authentication)
    {
        String username = this.getUsername(authentication);
        AuthProviders authProvider = this.getAuthProvider(authentication);

        long userId = this.userRepository.findByUsernameAndAuthProvider(username, authProvider)
                .map(User::getId)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found for username: " + username
                                + " and auth provider: " + authProvider.name())
                );

        String contentType = imageFile.getContentType();
        if(contentType == null || !contentType.startsWith("image/"))
            return 2;

        byte[] imageData;
        try
        {
            imageData = imageFile.getBytes();
        }
        catch(IOException e)
        {
            return 3;
        }

        Optional<Image> carOptional = this.imageRepository.findByUser_IdAndId(userId, id);
        if(carOptional.isEmpty())
            return 4;

        Image car = carOptional.get();
        car.setProfilePhotoData(imageData);

        this.imageRepository.save(car);

        this.sendUpdateToUser();

        return 1;
    }
}
