Car Rental API - Docker Compose Guide
=====================================

Prerequisites
-------------

1.  **Docker**: Make sure Docker is installed on your machine.

    -   Install Docker

2.  **Docker Compose**: Ensure Docker Compose is installed.

    -   Install Docker Compose

3.  Clone the repository:

    ```
    git clone https://github.com/AdamoElProfesor/dai_practical_work_3.git
    cd dai_practical_work_3
    ```

4. **Maven Wrapper**: Use the Maven Wrapper to build the application.

    -   If you haven't already, ensure the Maven Wrapper is included in the repository.

    -   Build the application JAR file:

    ```
    cd ./api/car_rental
    ./mvnw clean package
    ```

    For Windows:

    ```
   cd ./api/car_rental
    mvnw.cmd clean package
    ```

* * * * *

* * * * *

Using Docker Compose
--------------------

### 1\. Update the `.env` File in /api

Before running the application, update the `.env` file in the **API directory** to include your domain name:

```
TRAEFIK_FULLY_QUALIFIED_DOMAIN_NAME=your-domain.duckdns.org
```

* * * * *

### 2\. Start Traefik First

Run the following command to start the Traefik service:

```
cd ./traefik
docker-compose up --build traefik
```

-   This ensures that the reverse proxy is running and ready to route traffic to your API.

* * * * *

### 3\. Start the API

Once Traefik is up, start the API service:

```
cd ./traefik
docker-compose up --build api
```

* * * * *

### 4\. Access the Application

-   **API Endpoint**: The API will be available at:

    -   `http://your-domain.duckdns.org/cars` (if Traefik is configured correctly).

* * * * *

### 5\. Stop the Application

To stop the application and remove containers:

```
docker-compose down
```

* * * * *

### Troubleshooting

-   Ensure the `.env` file is correctly updated with your domain.

-   Start Traefik before the API to allow proper routing.

-   Check the logs if something doesn't work:

    ```
    docker-compose logs traefik
    docker-compose logs api
    ```