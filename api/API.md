# Car rental API

The car rental API allows to manage cars and clients (users). It uses the HTTP protocol and the JSON format

the API is based on the CRUD pattern. It has the following operations:

- Create a new user
- Delete a user by its ID
- Get all cars
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

## Delete a user

- `DELETE /users/{id}`

Delete a user by its ID.

#### Request

The request path must contain the ID of the user.

#### Response

The response body is empty.

#### Status codes

- `204` (No Content) - The user has been successfully deleted
- `404` (Not Found) - The user does not exist


### Get all cars 

- `GET /cars/`

Get all cars.

#### Request 

Empty

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

### Get all cars 

- `GET /cars/`

Get all cars.

#### Request 

Empty

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
- `304` (Not modified) - The cars are still the same


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
- `304` (Not modified) - The car is still the same
- `404` (Not Found) - The car does not exist

### Rent a car

- `PUT /cars/{carId}/rent`

User rents a car 

#### Request

The request path must contain the ID of the car.

#### Response

The response body contains a JSON object with the following properties:
- `id`
- `message`
- `userRenting`
- `brand`
- `model`

The `userRenting` field is updated

#### Status codes

- `200` (OK) - The car has been successfully updated
- `401` (Unauthorized) - User has not logged in
- `404` (Not Found) - The car does not exist
- `409` (Conflict) - The is already rented
- `412` (Precondition Failed)


### Return a car

- `PUT /cars/{carId}/return`

User returns a car

#### Request

The request path must contain the ID of the car.

#### Response

The response body contains a JSON object with the following properties:
- `id`
- `message`
- `userRenting`
- `brand`
- `model`

The `userRenting` field is updated

#### Status codes

- `200` (OK) - The car has been successfully updated
- `401` (Unauthorized) - User has not logged in
- `403` (Forbidden) - The user is not renting the car
- `404` (Not Found) - The car does not exist
- `412` (Precondition Failed)

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

## Examples

### **Create a New User**

**Endpoint**: `POST /users`

#### Curl Example:

```
curl -X POST https://api.car-rentals.duckdns.org/users --insecure\
  -H "Content-Type: application/json"\
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "password": "securepassword"
  }'
```

**Response**:

```
{
  "id": "123",
  "firstName": "John",
  "lastName": "Doe"
}
```

* * * * *

### **Delete a User**

**Endpoint**: `DELETE /users/{id}`

#### Curl Example:

```
curl -X DELETE https://api.car-rentals.duckdns.org/users/123 --insecure
```

**Response**: *(No Content)*

* * * * *

### **Get All Cars**

**Endpoint**: `GET /cars`

#### Curl Example:

```
curl -X GET https://api.car-rentals.duckdns.org/cars --insecure
```

**Response**:

```
[
  {
    "id": "1",
    "brand": "Toyota",
    "model": "Corolla",
    "engine": "Hybrid",
    "power": 120,
    "userRenting": null
  },
  {
    "id": "2",
    "brand": "Honda",
    "model": "Civic",
    "engine": "Gasoline",
    "power": 158,
    "userRenting": null
  }
  ...
]
```

* * * * *

### **Get One Car**

**Endpoint**: `GET /cars/{carId}`

#### Curl Example:

```
curl -X GET https://api.car-rentals.duckdns.org/cars/1 --insecure
```

**Response**:

```
{
  "id": "1",
  "brand": "Toyota",
  "model": "Corolla",
  "engine": "Hybrid",
  "power": 120,
  "userRenting": null
}
```

* * * * *

### **Rent a Car**

**Endpoint**: `PUT /cars/{carId}/rent`

#### Curl Example:

```
curl -X PUT https://api.car-rentals.duckdns.org/cars/1/rent --insecure
```

**Response**:

```
{
  "id": "1",
  "message": "Car successfully rented.",
  "userRenting": "123",
  "brand": "Toyota",
  "model": "Corolla"
}
```

* * * * *

### **Return a Car**

**Endpoint**: `PUT /cars/{carId}/return`

#### Curl Example:

```
curl -X PUT https://api.car-rentals.duckdns.org/cars/1/return --insecure
```

**Response**:

```
{
  "id": "1",
  "message": "Car successfully returned.",
  "userRenting": null,
  "brand": "Toyota",
  "model": "Corolla"
}
```

* * * * *

### **Login**

**Endpoint**: `POST /login`

#### Curl Example:

```
curl -X POST https://api.car-rentals.duckdns.org/login --insecure\
  -H "Content-Type: application/json"\
  -d '{
    "email": "john.doe@example.com",
    "password": "securepassword"
  }'
```

**Response**: *(No Content)*

* * * * *

### **Logout**

**Endpoint**: `POST /logout`

#### Curl Example:

```
curl -X POST https://api.car-rentals.duckdns.org/logout --insecure
```

**Response**: *(No Content)*

* * * * *