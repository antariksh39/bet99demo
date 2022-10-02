# Welcome to BET99 TODO Application Guide
Author: Antariksh Gosain  
Date: October 01, 2022

Hello, This quick guide will walk you throught the steps for setting up  **Bet99 TODO App**. This is a simple REST API Application based on Java Springboot framework using Java 17 and Maven.

This Guide has Three major sections  
1 - Getting Server Compiled and Running  
2- Getting Client compiled and Running  
3 - Application Walkthrough of all services  

## Important Information

This is the [Github repo](https://github.com/antariksh39/bet99demo) for the project.
You will need to have the [JDK 17](https://www.techspot.com/downloads/7440-java-se-17.html) installed.  
You can choose your preferred IDE, this guide will be using [Intelij](https://www.jetbrains.com/idea/download/#section=windows).  
We will be using [Redis](https://redis.io/download/) client for in-memory Database.  
And for request/response we will be using command line tool name curl.
(Alternatively you can also use [Postman](https://www.postman.com/downloads/))  


## SECTION ONE - Getting Server Compiled and Running

1. Download and install you Preferred IDE
2. Make sure you Java is installed and [Environment Variable are set](https://www.baeldung.com/java-home-on-windows-7-8-10-mac-os-x-linux)
3. Clone the Project from its [Github Repository](https://github.com/antariksh39/bet99demo)
4. Download Enable the [Lombok](https://projectlombok.org/) plugin for your IDE
5. Install and open the Redis Server Client.
6. Update the Dependancies by Right clicking on **pom.xml** file and using **Maven > Download Sources**.
7. Build the Project.
8. Right click and run **DemoApplication** and the Springboot applicaiton will be started in a Tomcat Server.


## SECTION TWO - Getting Client compiled and Running  

1. As client, we will be using the command line Curl Command
2. The Command Line Interface comes along with every Operation System. In case the curl command is not available, you can download the curl package using [this Guide](https://help.ubidots.com/en/articles/2165289-learn-how-to-install-run-curl-on-windows-macosx-linux).
3. Once the Springboot application is successfully launched, open up the Command Line Interface and run the following command.  
 `curl -v localhost:8080/bet99/todo` 
 4. This will give the list of all Tasks present in the Redis DB at the moment

 

## SECTION THREE - Application Walkthrough of all services  
There are a total of Five (05) Endpoints in the TODO Applicaiton.  
Here are the details.
|                |Endpoint                          |HTTP Method|
|----------------|-------------------------------|-----------------------------|
|Get List of Todos|`{baseUrl}/bet99/todo` |GET |
|Get TODO by id|`{baseUrl}/bet99/todo/{id}` |GET|
|Save a new TODO|`{baseUrl}/bet99/todo` |POST|
|Update an exising TODO|`{baseUrl}/bet99/todo` |PUT|
|Delete a TODO by id|`{baseUrl}/bet99/todo/{id}` |DELETE|
    
{baseUrl} = http://127.0.0.1:8080 (Check port for confirmation)  
{id} = Integer like 1, 5, 800 etc.
    
## GET 
Returns status OK (200) if the Todo(s) are found are not. The message in the reponse also describes the Number of Todo(s) in the List.  
Command

    curl -X GET localhost:8080/bet99/todo

Response

    {
    "statusCode":200,
    "message":"OK - Total todo(s) found: 3",
    "data":[
    {"taskId":3,"userId":"BET99_magnus","description":"fix printer","dueDate":"2022-11-16 12:00:40","state":"TODO"},
    {"taskId":2,"userId":"BET99_anta","description":"fix printer","dueDate":"2022-11-16 12:00:40","state":"TODO"},
    {"taskId":1,"userId":"BET99_magnus","description":"update windows on all systems","dueDate":"2022-11-15 12:00:40","state":"IN_PROGRESS"}]
    }


## POST 
Returns CREATED (201) if the Todo is created.  
taskId field is not required as it is autoincremented by the System.   

This is an example of the Payload required for the POST request

> {
"userId":"BET99_eddie",
"description":"assign Jira Tickets for next Sprint",
"dueDate":"2022-11-01 11:29:59",
"state":"TODO"
}

Command For Ubuntu Terminal

    curl --location --request POST 'http://127.0.0.1:8080/bet99/todo' --header 'Content-Type: application/json' --data-raw '{"userId":"BET99_eddie","description":"assign Jira Tickets for next Sprint","dueDate":"2022-11-01 11:29:59","state":"TODO"}'
    
In case the userId does not start with "BET99" the service will give an authenticaiton error.  Else the service will return UNAUTHORIZED (401)
Also, the userId and description are Mandatory fields and dueDate should be in proper format. Else the Service will return BAD_REQUEST (400)  
Last but not the least, state will be set to TODO by default

## PUT

Returns ACCEPTED (202) if the Todo is updated.  
taskId field is required as it is used to determine which TODO to update.   

This is an example of the Payload required for the PUT request

>{
"taskId":3,
"userId":"BET99_eddie",
"description":"update CEO about latest tech updates",
"dueDate":"2022-12-15 13:23:11",
"state":"TODO"
}

Command For Ubuntu Terminal

    curl --location --request PUT 'http://127.0.0.1:8080/bet99/todo' --header 'Content-Type: application/json' --data-raw '{"taskId":3,    "userId":"BET99_eddie","description":"update skype","dueDate":"2022-03-15 13:23:11","state":"TODO"}'
    
Please note that userId and description are Mandatory fields and dueDate should be in proper format. Else the Service will return BAD_REQUEST (400)    
Also, if the userId does not start with "BET99" the service will give an authenticaiton error.  Else the service will return UNAUTHORIZED (401)

## DELETE
Returns OK (200) if the Todo is delete.  
taskId field is required in the URL endpoint as it is used to determine which TODO to delete.   

Command For Ubuntu Terminal

    curl --location --request DELETE 'http://127.0.0.1:8080/bet99/todo/2'
    
The Delete Service will return NOT_FOUND (404) if the Todo based on given ID is not found.
