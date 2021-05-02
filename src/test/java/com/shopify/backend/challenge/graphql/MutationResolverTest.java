package com.shopify.backend.challenge.graphql;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.shopify.backend.challenge.image.Image;
import com.shopify.backend.challenge.image.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static com.shopify.backend.challenge.common.CommonTestConstants.TEST_IMAGE_A;
import static com.shopify.backend.challenge.common.CommonTestConstants.TEST_IMAGE_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @MockBean
    private ImageService imageService;

    @Test
    void uploadImage_exception() throws IOException {
        when(imageService.uploadImages(any())).thenReturn(TEST_IMAGE_LIST);

        GraphQLResponse response = graphQLTestTemplate.postForResource("graphql/create-image.graphql");

        assertThat(response.isOk()).isTrue();
        assertThat(response.getList("$.data.createImage", Image.class).contains(TEST_IMAGE_A));
    }
}
