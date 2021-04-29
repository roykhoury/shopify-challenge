package com.shopify.backend.challenge.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.shopify.backend.challenge.image.Image;
import com.shopify.backend.challenge.image.ImageService;
import graphql.schema.DataFetchingEnvironment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class MutationResolver implements GraphQLMutationResolver {

    private ImageService service;

    // List<Part> parts must still be included, for the sake of signature, however is never populated
    public List<Image> createImage(List<Part> parts, String title, DataFetchingEnvironment env) throws IOException {
        return service.uploadImages(env.getArgument("files"), title);
    }
}
