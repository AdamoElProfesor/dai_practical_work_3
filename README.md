# Car rental API

The car rental API allows to manage cars and clients (users). It uses the HTTP protocol and the JSON format

the API is based on the CRUD pattern. It has the following operations:

- Create a new user
- Get one car by its ID
- Rent a car
- Return a car
- Delete a user by its ID
- Login
- Logout

## Endpoints

### Create a new user

- `POST /users`

Create a new user.
#### Request

The request body must contain a JSON object with the following properties:

- `firstName`
- `lastName`
- `email`
- `password`

### Response

The response body must contain a JSON object with the following properties:

- `id`
- `firstName`
- `lastName`

#### Status codes

- `201` (Created) - The user has been successfully created
- `400` (Bad Request) - The request body is invalid
- `409` (Conflict) - The user already exists


### Get one car 

- `GET /cars/{carId}`

Get one car by its ID.

#### Request 

The request path must contain the ID of the car.

#### Response

The response body contains a JSON object with the following properties:

- `id`
- `brand`
- `model`
- `engine`
- `power`
- `userRenting` (contains the userId, if no one is renting, it is null)


#### Status codes

- `200` (OK) - The car has been successfully retrieved
- `404` (Not Found) - The car does not exist

### Rent a car

- `PUT /cars/{carId}/rent`

User rents a car 

#### Request

The request path must contain the ID of the car.

#### Response

The response body is empty. The `userRenting` field is updated

#### Status codes

- `200` (OK) - The car has been successfully updated
- `401` (Unauthorized) - User has not logged in
- `404` (Not Found) - The car does not exist
- `409` (Conflict) - The is already rented


### Return a car

- `PUT /cars/{carId}/return`

User returns a car

#### Request

The request path must contain the ID of the car.

#### Response

The response body is empty. The `userRenting` field is updated

#### Status codes

- `200` (OK) - The car has been successfully updated
- `401` (Unauthorized) - User has not logged in
- `403` (Forbidden) - The user is not renting the car
- `404` (Not Found) - The car does not exist

### Delete a user

- `DELETE /users/{id}`

Delete a user by its ID.

#### Request

The request path must contain the ID of the user.

#### Response

The response body is empty.

#### Status codes

- `204` (No Content) - The user has been successfully deleted
- `404` (Not Found) - The user does not exist

### Login

- `POST /login`

Login a user.

#### Request

The request body must contain a JSON object with the following properties:

- `email`
- `password`

#### Response

The response body is empty. A `user` cookie is set with the ID of the user.

#### Status codes

- `204` (No Content) - The user has been successfully logged in
- `400` (Bad Request) - The request body is invalid
- `401` (Unauthorized) - The user does not exist or the password is incorrect

### Logout

- `POST /logout`

Logout a user.

#### Request

The request body is empty.

#### Response

The response body is empty. The `user` cookie is removed.

#### Status codes

- `204` (No Content) - The user has been successfully logged out
