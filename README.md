# Welcome to BET99 TODO Application Guide

Hello, This quick guide will walk you throught the steps for setting up  **Bet99 TODO App**. This is a simple REST API Application based on Java Springboot framework using Java 17 and Maven.

This Guide has Three major sections
1 - Getting Server Compiled and Running
2- Getting the Client compiled and Running
3 - Application Walkthrough of all services

## Important Information

This is the [Github repo](https://github.com/antariksh39/bet99demo) for the project.
You will need to have the [JDK 17](https://www.techspot.com/downloads/7440-java-se-17.html) installed.
You can choose your preferred IDE, this guide will be using [Intelij](https://www.jetbrains.com/idea/download/#section=windows).
We will be using [Redis](https://redis.io/download/) client for in-memory Database.
And for request/response we will be using command line tool name curl.
(Alternatively you can also use [Postman](https://www.postman.com/downloads/))


## SECTION ONE - Getting Server Compiled and Running






|                |Endpoint                          |HTTP Method|
|----------------|-------------------------------|-----------------------------|
|Get List of Todos|`/bet99/todo` |GET |
|Get TODO by id|`{baseUrl}/bet99/todo/{id}` |GET|
|Save a new TODO|`{baseUrl}/bet99/todo` |POST|
|Update an exising TODO|`{baseUrl}/bet99/todo` |PUT|
|Delete a TODO by id|`{baseUrl}/bet99/todo/{id}` |Delete|


