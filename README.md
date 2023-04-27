# File Server
This is a simple file server which allows user to add, delete and grab 
files anywhere from the local network. 
The files are stored separately for each user and password protected.

## Running the application

### Pre-Requisites

In order to run the file server locally, you will need the following:
- Docker

### Steps
- Clone the repository
- Go to `/frontend/.env` and replace `ENTER_YOUR_IP_HERE` with the IP address of the system the server is to run on.
- Run `start.bat/start.sh`

The file server should start with a default user with email `admin@mail.com` and password `admin`.

The user interface should be accessible on `localhost:3000`

# User Interface
## Landing page
![alt Landing page](./docs/Landing%20page.jpg)
The first page the user sees is the landing page which the user can login through by providing the aforementioned credenetials.

## Files page
![alt Files page](./docs/Files%20page.jpg)
On the initial login, the user is presented with an empty Files page.

From here, the user can upload, download and delete files to/from the server.

*Note: It is advised to create a new user to upload files to instead of using the admin user.*

### Creating a new user
In order to create a new user, click on the user/profile icon right side of the navigation bar.

![alt User icon](./docs/profileIcon.svg)

A user registration dialog should open.

![alt User registration dialog](./docs/User%20registration%20dialog.jpg)

*Note: Only a user with `Admin` role can create users so keep that in mind while selecting the role.*

Fill up the fields and click on `Register`.

Once the user is registered, logout of the admin account and login with your new user and start uploading your files.

### Uploading files
In order to upload files, click on the `Uplaod` button. A file selection window should open. Select the file and click `Open`. Your file should be uplaoded.

*Note: Currently you can only upload one file at a time. I apologize for the inconvenience.*

[//]: # (## Endpoints)

[//]: # ()
[//]: # (Before jumping into the endpoints, all the endpoints which are not user )

[//]: # (related, use JWT tokens for authorization.)

[//]: # ()
[//]: # (### Authentication endpoints &#40;/auth&#41;)

[//]: # ()
[//]: # (#### POST */register*  )

[//]: # ()
[//]: # (This endpoint is used to register a new user.  )

[//]: # (This endpoint requires the following object in the request body:  )

[//]: # (````)

[//]: # ({)

[//]: # (    "name": USER_NAME,)

[//]: # (    "email": USER_EMAIL,)

[//]: # (    "password": PASSWORD,)

[//]: # (    "role": ROLE_OF_USER,)

[//]: # (    "requester": UUID_OF_REQUESTER)

[//]: # (})

[//]: # (````)

[//]: # ()
[//]: # (**ROLE_OF_USER** can have two values:)

[//]: # (- ADMIN)

[//]: # (- USER)

[//]: # ()
[//]: # (The response of the request looks as following:)

[//]: # (````)

[//]: # ({)

[//]: # (    "userUuid": UUID,)

[//]: # (    "token": JWT_TOKEN)

[//]: # (})

[//]: # (````)

[//]: # ()
[//]: # (#### POST */authentication*  )

[//]: # ()
[//]: # (This endpoint is used to authenticate a user.  )

[//]: # (This endpoint requires the following object in the request body:)

[//]: # (````)

[//]: # ({)

[//]: # (    "email": USER_EMAIL,)

[//]: # (    "password": PASSWORD)

[//]: # (})

[//]: # (````)

[//]: # ()
[//]: # (The response of the request looks as following:)

[//]: # (````)

[//]: # ({)

[//]: # (    "userUuid": UUID,)

[//]: # (    "token": JWT_TOKEN)

[//]: # (})

[//]: # (````)

[//]: # ()
[//]: # (#### PUT)

[//]: # ()
[//]: # (This endpoint is used to update the password of a user.  )

[//]: # (This endpoint requires the following object in the request body:)

[//]: # (````)

[//]: # ({)

[//]: # (    "email": USER_EMAIL,)

[//]: # (    "oldPassword": OLD_PASSWORD,)

[//]: # (    "newPassword": NEW_PASSWORD)

[//]: # (})

[//]: # (````)

[//]: # ()
[//]: # (The response of the request looks as following:)

[//]: # (````)

[//]: # ({)

[//]: # (    "userUuid": UUID,)

[//]: # (    "token": JWT_TOKEN)

[//]: # (})

[//]: # (````)

[//]: # ()
[//]: # (#### DELETE)

[//]: # ()
[//]: # (This endpoint is used to delete of a user.  )

[//]: # (This endpoint requires the following object in the request body:)

[//]: # (````)

[//]: # ({)

[//]: # (    "email": USER_EMAIL,)

[//]: # (    "password": PASSWORD)

[//]: # (})

[//]: # (````)

[//]: # ()
[//]: # (### File endpoints &#40;/filez&#41;)

[//]: # ()
[//]: # (#### GET */{uuid}*)

[//]: # ()
[//]: # (This endpoint is used to retrieve all the files belonging to the user.  )

[//]: # (This endpoint requires the user's UUID for whom the files need to be )

[//]: # (retrieved.)

[//]: # ()
[//]: # (The response of the request looks as following:)

[//]: # (````)

[//]: # ([)

[//]: # (    {)

[//]: # (        "id": FILE_UUID,)

[//]: # (        "name": FILE_NAME,)

[//]: # (        "contentType": TYPE_OF_MEDIA,)

[//]: # (        "path": PATH_WHERE_THE_FILE_IS_STORED,)

[//]: # (        "numberOfDownloads": NUMBER_OF_DOWNLOADS)

[//]: # (    },)

[//]: # (    ...)

[//]: # (])

[//]: # (````)

[//]: # ()
[//]: # (#### GET */download/{uuid}*)

[//]: # ()
[//]: # (This endpoint is used to download a file.  )

[//]: # (This endpoint requires the file's UUID to be downloaded.)

[//]: # ()
[//]: # (The response of the request is a Resource.)

[//]: # ()
[//]: # (#### POST)

[//]: # ()
[//]: # (This endpoint is used to upload a file.  )

[//]: # (This endpoint requires the user's UUID who is uploading the file and the file )

[//]: # (as request parameters.)

[//]: # ()
[//]: # (#### DELETE */{uuid}*)

[//]: # ()
[//]: # (This endpoint is used to delete a file.  )

[//]: # (This endpoint requires the file's UUID to be deleted.)
