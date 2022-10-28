## ITBC-Logger
The final product for IT Bootcamp. Implementation of simple logger Rest API with Springboot, JPA, H2 database.
### Technologies used on the project:

- Java version 19
- PostgreSQL - pgAdmin 4
- Spring Boot version 2.7.5

### User
- The user can register
  username, email, password
- The user can retrieve his "key"
  Using username, email, and password
- The user can enter the log
  message
  log type (ERROR, WARNING,INFO)
  created date
- User can search logs

### Admin
- There is an admin account (or admin key)
- Admin can see all users
- Admin can see the number of logs for each user
- The admin can change the user's password at his request

### Endpoints

1. Register
- HTTP Method: 'POST'
- Enpoint URL: '/api/clients/register'
- Request body:
  ```json
  {
    "username":"string",
    "password":"string",
    "email":"string",
    "logType":"admin"
  }
  ```
- Responses:
    - 201 - Registered
    - 400 - Bad Request
        - email must be valid
        - username at least 3 characters
        - password at least 8 characters and one letter and one number and one special
    - 409 - Conflict
        - username already exist
        - email already exist

2. Login
- HTTP Method: 'POST'
- Endpoint URL: '/api/clients/login'
- Request body:
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
  Responses:
    - 200 - OK
    ```json
    {
        "token":"string"
     }
    ```
    - 400 - Bad Request
      -username or password incorect

3. Create log
- HTTP Method: 'POST'
- Endpoint URL: '/api/logs/create'
- Request body:
  ```json
  {
    "message":"string",
    "logType": 0
  }
  ```
- Request headers:
    - 'Authorization' - token
- Responses:
    - 201 - Created
    - 400 - Bad Request
      -Incorrect logType
    - 401 - Unauthorization
      -Incorrect token
    - 413 - Payload too large
      -Message should be less than 1024 character
4. Search Log
- HTTP Method: 'GET'
- Endpoint URL: '/api/logs/search'
- Request params:
    - 'dateFrom'
    - 'dateTo'
    - 'message'
    - 'logType'
- Request headers:
    - 'Authorization' - token
- Responses:
    - 200 - OK
      ```json 
      {
        "message":"string",
        "logType":0,
        "dateFrom":"date",
        "dateTo":"date"
      }
      ```
    - 400 - Bad request
        - Invalid dates
        - Invalid logType
    - 401 - Unauthorized
        - Incorrect token
5. Get all clients
- HTTP Method: 'GET'
- Endpoint URL: '/api/clients'
- Request headers:
    - 'Authorization' - token (Admin token)
- Responses:
    - 200 - OK
      ```json
      [
        {
          "id":"uuid",
          "username":"string",
          "email":"string",
          "logCount":0
        }
      ]
      ```
- 401 - Unauthorized
    - Incorrect token
- 403 - Frobidden
    - Correct token, but not admin

6. Change client password
- HTTP Method: 'PATCH'
- Endpoint URL: '/api/clients/{id}/reset-password'
- Request body:
  ```json
    {
      "password":"string"
    }
  ```
- Request headers:
  -'Authorization' - token (Admin token or exact user token)
- Responses:
    - 204 - No content
    - 401 - Unauthorized
        - Correct token, but not admin
    - 403 - Forbidden
        - Incorrect token

7. Delete user
    - HTTP Method: 'DELETE'
    - Endpoint URL: '/api/clients/{id}/delete-client'
    - Request headers:
        - 'Authorization' - token (Admin token)
    - Responses:
        - 200 -OK
        - 204 - No content
        - 401 - Unauthorized
            - Correct token, but not admin
            - 403 - Forbidden
            - Incorrect token

8. Delete log
    - HTTP Method: 'DELETE'
    - Endpoint URL: '/api/logs/{id}/delete-log'
    - Request headers:
        - 'Authorization' - token (Admin token)
    - Responses:
        - 200 -OK
        - 204 - No content
        - 401 - Unauthorized
            - Correct token, but not admin
        - 403 - Forbidden
            - Incorrect token

## Installation and setup

- Clients (clientid, email, password, username, client_type)
- Logs (id, message, date_of_log, log_type, clientid)
  Setting up application.properties is really important for normal functioning of the base. To set up, data base name, 
  username and password in the application.properties need to match with SQL server autentication in the base
- Relational database used: PostgreSQL

### SQL Queries

```
CREATE TABLE client (
	clientid uuid PRIMARY KEY,
	email varchar(255),
	password varchar(255),
	username varchar(255),
	client_type integer
);

CREATE TABLE log (
	id uuid PRIMARY KEY,
	message varchar(1024),
	date_of_log date,
	log_type integer,
	FOREIGN KEY (client) REFERENCES client(clientid)
);
```