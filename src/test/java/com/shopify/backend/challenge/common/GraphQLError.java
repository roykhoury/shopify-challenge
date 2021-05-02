package com.shopify.backend.challenge.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GraphQLError {
    private String message;
    private List locations;
    private Object extensions;
}

