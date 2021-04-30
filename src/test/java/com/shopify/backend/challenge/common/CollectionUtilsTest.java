package com.shopify.backend.challenge.common;

import com.shopify.backend.challenge.image.Image;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.shopify.backend.challenge.common.CollectionUtils.distinctByKey;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CollectionUtilsTest {

    @Test
    void validImageList_testDistinctByTitle_returnUniqueList() {
        Image image1 = Image.builder().cloudinaryId("1").title("title").build();
        Image image2 = Image.builder().cloudinaryId("2").title("title").build();
        List<Image> imageList = Arrays.asList(image1, image2);

        assertEquals(2, imageList.size());

        imageList = imageList.stream()
            .filter(distinctByKey(Image::getTitle))
            .collect(Collectors.toList());

        assertEquals(1, imageList.size());
    }

    @Test
    void validImageList_testDistinctById_returnUniqueList() {
        Image image1 = Image.builder().cloudinaryId("1").title("title").build();
        Image image2 = Image.builder().cloudinaryId("2").title("title").build();
        List<Image> imageList = Arrays.asList(image1, image2);

        assertEquals(2, imageList.size());

        imageList = imageList.stream()
                .filter(distinctByKey(Image::getCloudinaryId))
                .collect(Collectors.toList());

        assertEquals(2, imageList.size());
    }
}
