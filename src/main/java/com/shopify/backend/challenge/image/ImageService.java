package com.shopify.backend.challenge.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.shopify.backend.challenge.tag.Tag;
import com.shopify.backend.challenge.tag.TagRepository;
import com.shopify.backend.challenge.tag.TagUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.shopify.backend.challenge.common.CloudinaryConfig.uploaderConfigMap;

@Service
@AllArgsConstructor
@Log4j2
public class ImageService {

    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final Cloudinary cloudinary;

    public List<Image> uploadImages(List<Part> parts) throws IOException {
        List<Image> images = new ArrayList<>();
        for (Part part : parts) {
            Map uploadResult = cloudinary.uploader().uploadLarge(part.getInputStream(), uploaderConfigMap);
            images.add(Image.builder()
                    .cloudinaryId(String.valueOf(uploadResult.get("public_id")))
                    .url(String.valueOf(uploadResult.get("secure_url")))
                    .tags(TagUtils.fromStringToList(String.valueOf(uploadResult.get("tags"))))
                    .build());
        }
        return imageRepository.saveAll(images);
    }

    public List<Image> findByTags(String[] tags) {
        List<Long> ids = tagRepository.findAllByValueIn(tags)
                .stream()
                .map(Tag::getImage)
                .map(Image::getId)
                .collect(Collectors.toList());
        return imageRepository.findAllByIdIn(ids);
    }

    public List<Image> findBySimilarImage(Part part) throws IOException {
        Map uploadResult = cloudinary.uploader().uploadLarge(part.getInputStream(), uploaderConfigMap);
        cloudinary.uploader().destroy(String.valueOf(uploadResult.get("public_id")), ObjectUtils.emptyMap());

        String tagString = String.valueOf(uploadResult.get("tags"));
        String[] parsedTags = TagUtils.fromStringToArray(tagString);

        return findByTags(parsedTags);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}
