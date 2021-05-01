package com.shopify.backend.challenge.common;

import com.shopify.backend.challenge.image.Image;
import com.shopify.backend.challenge.tag.Tag;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class CommonTestConstants {
    public static final String TEST_CLOUDINARY_ID_A = "test-cloudinary-id-a";
    public static final String TEST_CLOUDINARY_ID_B = "test-cloudinary-id-b";
    public static final String TEST_URL_A = "test-url-a";
    public static final String TEST_URL_B = "test-url-b";
    public static final String CUTE_TAG_VALUE = "cute";
    public static final String PET_TAG_VALUE = "pet";
    public static final String STATUS_KEY = "status";
    public static final String STATUS_OK = "ok";

    public static final Image TEST_IMAGE_A = Image.builder()
            .url(TEST_URL_A)
            .cloudinaryId(TEST_CLOUDINARY_ID_A)
            .build();

    public static final Image TEST_IMAGE_B = Image.builder()
            .url(TEST_URL_B)
            .cloudinaryId(TEST_CLOUDINARY_ID_B)
            .build();

    public static final List<Image> TEST_IMAGE_LIST = Arrays.asList(
            TEST_IMAGE_A,
            TEST_IMAGE_B
    );

    public static final Tag TEST_TAG_A = Tag.builder()
            .value(CUTE_TAG_VALUE)
            .image(TEST_IMAGE_A)
            .build();

    public static final Tag TEST_TAG_B = Tag.builder()
            .value(PET_TAG_VALUE)
            .image(TEST_IMAGE_B)
            .build();

    public static final List<Tag> TEST_TAG_LIST = Arrays.asList(
            TEST_TAG_A,
            TEST_TAG_B
    );
}
