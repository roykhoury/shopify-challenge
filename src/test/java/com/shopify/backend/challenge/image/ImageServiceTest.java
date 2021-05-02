package com.shopify.backend.challenge.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.shopify.backend.challenge.common.CloudinaryConfig;
import com.shopify.backend.challenge.tag.Tag;
import com.shopify.backend.challenge.tag.TagRepository;
import org.apache.http.impl.io.EmptyInputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.shopify.backend.challenge.common.CommonTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ImageServiceTest {

    @Mock
    ImageRepository imageRepository;

    @Mock
    TagRepository tagRepository;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    Part part;

    @Mock
    private Uploader uploader;

    @Spy
    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void validRequest_uploadImages_returnUploadedImages() throws IOException {
        // We don't want to connect to cloudinary at all, so stub and return empty results
        when(cloudinary.uploader()).thenReturn(uploader);
        when(part.getInputStream()).thenReturn(EmptyInputStream.nullInputStream());
        when(uploader.uploadLarge(part.getInputStream(), CloudinaryConfig.uploaderConfigMap)).thenReturn(ObjectUtils.emptyMap());
        when(imageRepository.saveAll(anyList())).thenReturn(Collections.singletonList(TEST_IMAGE_A));

        List<Part> parts = Collections.singletonList(part);
        List<Image> uploadResults = imageService.uploadImages(parts);

        verify(uploader, times(parts.size())).uploadLarge(part.getInputStream(), CloudinaryConfig.uploaderConfigMap);
        verify(imageRepository).saveAll(anyList());

        assertEquals(1, uploadResults.size());
        assertEquals(TEST_CLOUDINARY_ID_A, uploadResults.get(0).getCloudinaryId());
    }

    @Test
    void validRequest_findByTags_returnImagesList() {
        String[] tags = TEST_TAG_LIST.stream()
                .map(Tag::getValue)
                .toArray(String[]::new);

        List<Long> imageIds = TEST_TAG_LIST.stream()
                .map(Tag::getImage)
                .map(Image::getId)
                .collect(Collectors.toList());

        when(tagRepository.findAllByValueIn(tags)).thenReturn(TEST_TAG_LIST);
        when(imageRepository.findAllByIdIn(imageIds)).thenReturn(TEST_IMAGE_LIST);

        List<Image> images = imageService.findByTags(tags);

        assertEquals(TEST_IMAGE_LIST.size(), images.size());
        assertEquals(TEST_URL_A, images.get(0).getUrl());
    }

    @Test
    void validRequest_findBySimilarImage_returnImages() throws IOException {
        // We don't want to connect to cloudinary at all, so stub and return empty results
        when(cloudinary.uploader()).thenReturn(uploader);
        when(part.getInputStream()).thenReturn(EmptyInputStream.nullInputStream());
        when(uploader.uploadLarge(part.getInputStream(), CloudinaryConfig.uploaderConfigMap)).thenReturn(TEST_CLOUDINARY_RESPONSE);
        when(uploader.destroy(TEST_CLOUDINARY_ID_A, ObjectUtils.emptyMap())).thenReturn(ObjectUtils.emptyMap());

        imageService.findBySimilarImage(part);

        // Verification is sufficient in this case, because findByTags has already been tested
        verify(imageService).findByTags(TEST_TAG_ARRAY);
        verify(uploader).uploadLarge(part.getInputStream(), CloudinaryConfig.uploaderConfigMap);
        verify(uploader).destroy(TEST_CLOUDINARY_ID_A, ObjectUtils.emptyMap());
    }

    @Test
    void validRequest_allImages_returnImagesList() {
        Image image1 = Image.builder().url(TEST_URL_A).build();
        Image image2 = Image.builder().url(TEST_URL_B).build();

        when(imageRepository.findAll()).thenReturn(Arrays.asList(image1, image2));

        List<Image> images = imageService.getAllImages();

        assertEquals(2, images.size());
        assertEquals(TEST_URL_A, images.get(0).getUrl());
        assertEquals(TEST_URL_B, images.get(1).getUrl());
    }
}

