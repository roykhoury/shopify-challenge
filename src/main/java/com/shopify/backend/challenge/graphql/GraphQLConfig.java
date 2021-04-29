package com.shopify.backend.challenge.graphql;

import graphql.schema.GraphQLScalarType;
import graphql.servlet.core.ApolloScalars;
import graphql.servlet.core.GraphQLErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfig {

    @Bean
    public GraphQLErrorHandler errorHandler() {
        return new GraphQLAPIErrorHandler();
    }

    @Bean
    public GraphQLScalarType uploadScalarDefine() {
        return ApolloScalars.Upload;
    }

}
