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

import static com.shopify.backend.challenge.common.CollectionUtils.distinctByKey;

@Service
@AllArgsConstructor
@Log4j2
public class ImageService {

    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final Cloudinary cloudinary;

    public List<Image> uploadImages(List<Part> parts, String title) throws IOException {
        List<Image> images = new ArrayList<>();
        Map optionMap = ObjectUtils.asMap(
                "resource_type", "image",
                "categorization", "aws_rek_tagging",
                "auto_tagging", "0.5"
        );

        for (Part part : parts) {
            Map uploadResult = cloudinary.uploader().uploadLarge(part.getInputStream(), optionMap);
            images.add(Image.builder()
                    .cloudinaryId(uploadResult.get("public_id").toString())
                    .url(uploadResult.get("secure_url").toString())
                    .tags(TagUtils.fromString(uploadResult.get("tags").toString()))
                    .title(title)
                    .build());
        }

        return imageRepository.saveAll(images);
    }

    public List<Image> findByTags(List<Tag> tags) {
        List<Image> images = new ArrayList<>();
        tags.forEach(t -> tagRepository.findDistinctTopByValue(t.getValue())
                .flatMap(imageRepository::findDistinctByTags)
                .ifPresent(images::add));
        return images;
    }

    public List<Image> findBySimilarImage(List<Part> parts) throws IOException {
        List<Image> images = new ArrayList<>();
        Map optionMap = ObjectUtils.asMap(
                "resource_type", "image",
                "categorization", "aws_rek_tagging",
                "auto_tagging", "0.5"
        );

        for (Part part : parts) {
            Map uploadResult = cloudinary.uploader().uploadLarge(part.getInputStream(), optionMap);
            List<Tag> tags = TagUtils.fromString(uploadResult.get("tags").toString());
            images.addAll(findByTags(tags));
            cloudinary.uploader().destroy(uploadResult.get("public_id").toString(), ObjectUtils.emptyMap());
        }
        return images.stream()
                .filter(distinctByKey(Image::getId))
                .collect(Collectors.toList());
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}
