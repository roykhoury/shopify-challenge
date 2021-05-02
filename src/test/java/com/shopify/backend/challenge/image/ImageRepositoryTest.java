package com.shopify.backend.challenge.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static com.shopify.backend.challenge.common.CommonTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @BeforeEach
    void init() {
        imageRepository.save(TEST_IMAGE_A);
    }

    @Test
    void validQueryParams_findAllByIdIn_returnImageList() {
        List<Image> result = imageRepository.findAllByIdIn(Arrays.asList(1L, 2L, 3L));

        assertEquals(1, result.size());
        assertEquals(TEST_URL_A, result.get(0).getUrl());
        assertEquals(TEST_CLOUDINARY_ID_A, result.get(0).getCloudinaryId());
    }
}
