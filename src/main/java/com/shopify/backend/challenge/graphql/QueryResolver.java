package com.shopify.backend.challenge.graphql;

import com.shopify.backend.challenge.image.Image;
import com.shopify.backend.challenge.image.ImageService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {

    private final ImageService service;

    public List<Image> imageFromTag(String[] tags) {
        return service.findByTags(tags);
    }

    public List<Image> imageFromSimilarImage(Part part, DataFetchingEnvironment env) throws IOException {
        return service.findBySimilarImage(env.getArgument("file"));
    }

    public List<Image> allImages() {
        return service.getAllImages();
    }
}
