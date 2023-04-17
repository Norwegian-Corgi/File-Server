# File Server Backend
This is a simple file server which allows user to add, delete and grab 
files anywhere from the local network. 
The files are stored separately for each user and password protected.

## Running the application

### Pre-Requisites

In order to run the file server locally, you will need the following:
- Docker
- Postman (for accessing the API)

Steps:
- Clone the repository
- Build the project
- Run ``start.bat/start.sh``

The file server should start with a default user with email `test@email.com` and password `703f9503bb8b4ddae48725cdc73857e44e56677a7a500d6037028a2d6ca0859c`

## Endpoints

Before jumping into the endpoints, all the endpoints which are not user 
related, use JWT tokens for authorization.

### Authentication endpoints (/auth)

#### POST */register*  

This endpoint is used to register a new user.  
This endpoint requires the following object in the request body:  
````
{
    "name": USER_NAME,
    "email": USER_EMAIL,
    "password": PASSWORD,
    "role": ROLE_OF_USER,
    "requester": UUID_OF_REQUESTER
}
````

**ROLE_OF_USER** can have two values:
- ADMIN
- USER

The response of the request looks as following:
````
{
    "userUuid": UUID,
    "token": JWT_TOKEN
}
````

#### POST */authentication*  

This endpoint is used to authenticate a user.  
This endpoint requires the following object in the request body:
````
{
    "email": USER_EMAIL,
    "password": PASSWORD
}
````

The response of the request looks as following:
````
{
    "userUuid": UUID,
    "token": JWT_TOKEN
}
````

#### PUT

This endpoint is used to update the password of a user.  
This endpoint requires the following object in the request body:
````
{
    "email": USER_EMAIL,
    "oldPassword": OLD_PASSWORD,
    "newPassword": NEW_PASSWORD
}
````

The response of the request looks as following:
````
{
    "userUuid": UUID,
    "token": JWT_TOKEN
}
````

#### DELETE

This endpoint is used to delete of a user.  
This endpoint requires the following object in the request body:
````
{
    "email": USER_EMAIL,
    "password": PASSWORD
}
````

### File endpoints (/filez)

#### GET */{uuid}*

This endpoint is used to retrieve all the files belonging to the user.  
This endpoint requires the user's UUID for whom the files need to be 
retrieved.

The response of the request looks as following:
````
[
    {
        "id": FILE_UUID,
        "name": FILE_NAME,
        "contentType": TYPE_OF_MEDIA,
        "path": PATH_WHERE_THE_FILE_IS_STORED,
        "numberOfDownloads": NUMBER_OF_DOWNLOADS
    },
    ...
]
````

#### GET */download/{uuid}*

This endpoint is used to download a file.  
This endpoint requires the file's UUID to be downloaded.

The response of the request is a Resource.

#### POST

This endpoint is used to upload a file.  
This endpoint requires the user's UUID who is uploading the file and the file 
as request parameters.

#### DELETE */{uuid}*

This endpoint is used to delete a file.  
This endpoint requires the file's UUID to be deleted.
