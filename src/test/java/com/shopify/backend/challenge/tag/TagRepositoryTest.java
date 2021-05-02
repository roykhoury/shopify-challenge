package com.shopify.backend.challenge.tag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.shopify.backend.challenge.common.CommonTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void init() {
        tagRepository.save(TEST_TAG_A);
    }

    @Test
    void validQueryParams_findAllByValueIn_returnTagList() {
        String[] tags = { CUTE_TAG_VALUE };
        List<Tag> result = tagRepository.findAllByValueIn(tags);

        assertEquals(1, result.size());
        assertEquals(CUTE_TAG_VALUE, result.get(0).getValue());

        assertNotNull(result.get(0).getImage());
        assertEquals(TEST_URL_A, result.get(0).getImage().getUrl());
        assertEquals(TEST_CLOUDINARY_ID_A, result.get(0).getImage().getCloudinaryId());
    }
}
