package com.geekmaster117.springweb.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Image
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(nullable = false)
    private byte[] profilePhotoData;

    @Column(nullable = false)
    private String contentType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ImageDTO toDTO()
    {
        return new ImageDTO(this.id, this.contentType);
    }
}
