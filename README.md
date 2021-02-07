# Rest Sample Project

This project is just a simple rest example and a playground
to work with new technologies.

Used technologies:
- Gradle Multi-Project with Kotlin DSL
    - Using a shared data model in project `:model`
    - A Web Service with CRUD operations in project `:service`
    - A Web Client accessing the webservice in project `:client`
    - Shared build scripts for the applications using 
      same dependency versions for logging and data serialization
- Java records in data model (provided as preview function in JDK 15)
- Javalin for implementing RESTful services
- OpenFeign client to easily access the RESTful service


## Testing
Can be done easily with [HTTPie](https://httpie.io/).
```shell
http localhost:7000/podcasts
```