package com.geekmaster117.springweb.repositories;

import com.geekmaster117.springweb.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>
{
    List<Image> findByUser_Id(long userId);
    Optional<Image> findByUser_IdAndId(long userId, int id);
    boolean existsByUser_IdAndId(long userId, int id);
}
