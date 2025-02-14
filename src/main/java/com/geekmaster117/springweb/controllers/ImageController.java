package com.geekmaster117.springweb.controllers;

import com.geekmaster117.springweb.models.ImageDTO;
import com.geekmaster117.springweb.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("image")
public class ImageController
{
    @Autowired
    private ImageService service;

    @GetMapping("info")
    public ResponseEntity<?> getImageInfo(Authentication authentication)
    {
        Optional<List<ImageDTO>> imagesInfo = this.service.getImageInfo(authentication);
        if(imagesInfo.isPresent())
            return new ResponseEntity<>(imagesInfo.get(), HttpStatus.OK);
        return new ResponseEntity<>("No images found", HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<?> getImageById(@RequestParam int id, Authentication authentication)
    {
        Optional<byte[]> imageData = this.service.getImageById(id, authentication);
        if(imageData.isPresent())
            return new ResponseEntity<>(imageData.get(), HttpStatus.OK);
        return new ResponseEntity<>("Image with id: " + id + " does not exists",
                HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addImage(@RequestPart MultipartFile image, Authentication authentication)
    {
        return switch(this.service.addImage(image, authentication))
        {
            case 1 -> new ResponseEntity<>("Image successfully uploaded", HttpStatus.OK);
            case 2 -> new ResponseEntity<>("Only image files are allowed",
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            case 3 -> new ResponseEntity<>("Uploaded image could not be processed",
                    HttpStatus.UNPROCESSABLE_ENTITY);
            default -> new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @DeleteMapping
    public ResponseEntity<String> deleteImageById(@RequestParam int id, Authentication authentication)
    {
        if(this.service.deleteImageById(id, authentication))
            return new ResponseEntity<>("Image with id: " + id + " deleted", HttpStatus.OK);
        return new ResponseEntity<>("No Image with id: " + id + " exists", HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<String> updateImageById(@RequestPart MultipartFile image, @RequestParam int id, Authentication authentication)
    {
        return switch(this.service.updateImageById(image, id, authentication))
        {
            case 1 -> new ResponseEntity<>("Image with id: " + id + " updated", HttpStatus.OK);
            case 2 -> new ResponseEntity<>("Only image files are allowed",
                    HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            case 3 -> new ResponseEntity<>("Uploaded image could not be processed",
                    HttpStatus.UNPROCESSABLE_ENTITY);
            case 4 -> new ResponseEntity<>("Image with id: " + id + " does not exists",
                    HttpStatus.NOT_FOUND);
            default -> new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }
}
