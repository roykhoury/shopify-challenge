package com.shopify.backend.challenge.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.shopify.backend.challenge.image.Image;
import com.shopify.backend.challenge.tag.Tag;
import com.shopify.backend.challenge.image.ImageService;
import com.shopify.backend.challenge.tag.TagUtils;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {

    private final ImageService service;

    public List<Image> imageFromTag(String[] tagStrings) {
        List<Tag> tags = TagUtils.fromString(Arrays.toString(tagStrings));
        return service.findByTags(tags);
    }

    public List<Image> imageFromSimilarImage(List<Part> parts, DataFetchingEnvironment env) throws IOException {
        return service.findBySimilarImage(env.getArgument("files"));
    }

    public List<Image> allImages() {
        return service.getAllImages();
    }
}
