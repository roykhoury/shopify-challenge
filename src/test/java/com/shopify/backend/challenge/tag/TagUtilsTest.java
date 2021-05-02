package com.shopify.backend.challenge.tag;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagUtilsTest {

    @Test
    void validStringGiven_fromString_returnTagList() {
        String tags = "[tag1, tag2, tag3]";
        List<Tag> tagList = TagUtils.fromStringToList(tags);

        assertEquals(3, tagList.size());
        assertEquals("tag1", tagList.get(0).getValue());
        assertEquals("tag2", tagList.get(1).getValue());
        assertEquals("tag3", tagList.get(2).getValue());
    }

    @Test
    void validStringGiven_fromString_returnTagArray() {
        String tags = "[tag1, tag2, tag3]";
        String[] tagArray = TagUtils.fromStringToArray(tags);

        assertEquals(3, tagArray.length);
        assertEquals("tag1", tagArray[0]);
        assertEquals("tag2", tagArray[1]);
        assertEquals("tag3", tagArray[2]);
    }
}
