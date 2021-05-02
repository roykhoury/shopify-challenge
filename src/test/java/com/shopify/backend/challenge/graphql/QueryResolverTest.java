package com.shopify.backend.challenge.graphql;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.shopify.backend.challenge.common.GraphQLError;
import com.shopify.backend.challenge.image.Image;
import com.shopify.backend.challenge.image.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static com.shopify.backend.challenge.common.CommonTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QueryResolverTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    ImageService imageService;

    @Test
    void imageFromTag() throws IOException {
        when(imageService.findByTags(TEST_TAG_ARRAY)).thenReturn(TEST_IMAGE_LIST);

        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/image-from-tag.graphql");

        assertThat(response.isOk()).isTrue();
        assertThat(response.getList("$.data.imageFromTag", Image.class)).contains(TEST_IMAGE_A);
    }

    @Test
    // Passing file through graphql query is not possible for the moment, so
    // We will simply test the call, make sure it's reachable, and that we receive the correct error message
    // This is enough, since we have already tested the functionality of this use case, and now we test that it is reachable
    void imageFromSimilarImage_exception () throws IOException {
        when(imageService.findBySimilarImage(any())).thenReturn(TEST_IMAGE_LIST);

        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/image-from-similar-image.graphql");

        assertThat(response.isOk()).isTrue();
        assertThat(response.getList("$.errors", GraphQLError.class)).hasSize(1);

        GraphQLError error = response.getList("$.errors", GraphQLError.class).get(0);
        assertThat(error).hasFieldOrProperty("message");
        assertThat(error.getMessage()).contains("Validation error of type WrongType");
        assertThat(error.getExtensions()).hasFieldOrPropertyWithValue("classification", "ValidationError");
    }

    @Test
    void allImages() throws IOException {
        when(imageService.getAllImages()).thenReturn(TEST_IMAGE_LIST);

        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/all-images.graphql");

        assertThat(response.isOk()).isTrue();
        assertThat(response.getList("$.data.allImages", Image.class)).contains(TEST_IMAGE_A);
    }
}
