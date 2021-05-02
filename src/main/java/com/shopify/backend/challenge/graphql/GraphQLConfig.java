package com.shopify.backend.challenge.graphql;

import graphql.kickstart.execution.error.GraphQLErrorHandler;
import graphql.kickstart.servlet.apollo.ApolloScalars;
import graphql.schema.GraphQLScalarType;
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
