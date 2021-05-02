# Shopify-challenge
This is an image repository with a focus on SEARCH features. <br>
- Searching by text (tags, such as tree, mountain, snow)
- Searching via similar Image (Upload an image, and get similar results)

Other side features include
- Viewing all images in the database
- Adding a new image to the database (1 or many)

## Tech stack & design diagram
![Tech Stack](tech-stack.jpg)

##Prerequisits
- jdk-12.0.1
- docker

## Instructions
First clone the repository in a chosen local directory.

` git clone https://github.com/roykhoury/shopify-challenge `

From the project root, run the following command, to install dependencies, etc.

` mvn clean package `
     
From the project root, Run the command to start the service

` docker compose up `

Finally the service should be up and running, and graphQL queries should be accessible through
> localhost:8080/apis/graphql/

You can execute graphQL queries through tools such as Postman, with GraphQL queries for example:

```
{
    allImages {
        url
        tags {
            value
        }
    }
}
```
