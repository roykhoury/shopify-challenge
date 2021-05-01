package com.shopify.backend.challenge.common;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static com.shopify.backend.challenge.common.CommonTestConstants.STATUS_KEY;
import static com.shopify.backend.challenge.common.CommonTestConstants.STATUS_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = CloudinaryConfig.class)
class CloudinaryConfigTest {

    @Autowired
    private Cloudinary cloudinary;

    @Test
    void validConfig_pingCloudinary_returnOk() throws Exception {
        Map response = cloudinary.api().ping(ObjectUtils.emptyMap());
        String status = String.valueOf(response.get(STATUS_KEY));
        assertEquals(STATUS_OK, status);
    }
}
