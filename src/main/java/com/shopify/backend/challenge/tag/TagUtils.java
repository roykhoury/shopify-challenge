package com.shopify.backend.challenge.tag;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class TagUtils {

    /** Convert [1, 2, 3] string into list of tag objects with values 1, 2 and 3 */
    public static List<Tag> fromStringToList(String tagString) {
        return Stream.of(tagString.replaceAll("^.|.$", "").split(", ?"))
                .map(v -> Tag.builder().value(v).build())
                .collect(Collectors.toList());
    }

    public static String[] fromStringToArray(String tagString) {
        return tagString.replaceAll("^.|.$", "").split(", ?");
    }
}
