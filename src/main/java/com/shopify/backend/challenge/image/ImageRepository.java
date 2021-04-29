package com.shopify.backend.challenge.image;

import com.shopify.backend.challenge.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findDistinctByTags(Tag tags);
}
