package com.shopify.backend.challenge.common;

import com.cloudinary.utils.ObjectUtils;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class CloudinaryUtils {

    public static Map uploaderConfig = ObjectUtils.asMap(
            "resource_type", "image",
            "categorization", "aws_rek_tagging",
            "auto_tagging", "0.5"
    );
}
