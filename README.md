Car Rental API
==================================

Authors
-------

-   Pittet Axel

-   Gruber Adam

Introduction
----

<p>The Car Rental API is a web application that simulates a car rental scenario. It provides a fully functional backend for managing a fleet of cars and user accounts, allowing clients to perform actions like browsing available cars, renting vehicles, and returning them. 

The API is designed for developers to integrate into projects or simulate car rental workflows in a controlled environment. This project demonstrates key concepts such as CRUD operations, and modern deployment techniques using Docker and Traefik.</p>




## Summary

| Section                                                                         | Description                                                                     |
|---------------------------------------------------------------------------------| ------------------------------------------------------------------------------- |
| [Run the Application](#run-the-application)                                     | Instructions to set up and run the Car Rental API using Docker Compose.         |
| [Azure Virtual Machine Setup](#setting-up-a-virtual-machine-on-microsoft-azure) | Guide to setting up a virtual machine on Microsoft Azure.                       |
| [API Documentation](api/API.md)                                                 | Complete API details including endpoints, request/response formats, and examples. |
| [Publish docker image](#publish-your-applications-with-docker)                  | Publish and Run Your Docker Applications with GitHub Container Registry|


Run the Application
=====================================

Prerequisites
-------------

1.  Docker: Make sure Docker is installed on your machine.

    -   Install Docker
2.  Docker Compose: Ensure Docker Compose is installed.

    -   Install Docker Compose
3.  Clone the repository:

    ```
    git clone https://github.com/AdamoElProfesor/dai_practical_work_3.git
    cd dai_practical_work_3

    ```

4.  Maven Wrapper: Use the Maven Wrapper to build the application.

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

### 1\. Update the `.env` File in /api

Before running the application, update the `.env` file in the API directory to include your domain name:

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

-   API Endpoint: The API will be available at:

    -   `http://your-domain.duckdns.org/cars` (if Traefik is configured correctly).

* * * * *

### 5\. Stop the Application

To stop the application and remove containers:

```
docker-compose down

```

* * * * *

### Troubleshooting

-   Ensure the `.env` file is correctly updated with your domain.

-   Start Traefik before the API to allow proper routing.

-   Check the logs if something doesn't work:

    ```
    docker-compose logs traefik
    docker-compose logs api
    ```

Setting Up a Virtual Machine on Microsoft Azure
===============================================

This guide will walk you through setting up a virtual machine on Microsoft Azure. Follow the steps below to acquire and configure your VM for use.

* * * * *

Prerequisites
-------------

1.  **Azure for Students Account**: Use your HES-SO email address to apply for free Azure credits.

2.  **SSH Key Pair**: Ensure you have an SSH key pair ready for authentication. You can use your existing key or generate a new one with `ssh-keygen`.

* * * * *

Step 1: Access Microsoft Azure
------------------------------

1.  Log in to the Azure portal at <https://portal.azure.com> using your HES-SO email address (`<first name>.<last name>@hes-so.ch`).

2.  Use the password associated with your HES-SO account (e.g., GAPS, Cyberlearn credentials).

* * * * *

Step 2: Apply for the Azure for Students Offer
----------------------------------------------

1.  Navigate to [Azure for Students](https://azure.microsoft.com/en-us/free/students/).

2.  Log in with your HES-SO email if prompted.

3.  Complete the form with your details to activate free credits.

* * * * *

Step 3: Create a Virtual Machine
--------------------------------

1.  Log in to the Azure portal.

2.  From the dashboard, select **Create a resource** > **Virtual Machine**.

3.  Configure the virtual machine with the following settings:

### **Project Details**

-   **Subscription**: Azure for Students

-   **Resource Group**: Create a new group named `heig-vd-dai-course`

### **Instance Details**

-   **Name**: `heig-vd-dai-course-vm`

-   **Region**: (Europe) West Europe

-   **Availability Options**: No infrastructure redundancy required

-   **Security Type**: Trusted launch virtual machines

-   **Image**: Ubuntu Server 24.04 LTS - x64 Gen2

-   **Size**: Standard_B1s (click "See all sizes" if needed)

### **Administrator Account**

-   **Authentication Type**: SSH Public Key

-   **Username**: `ubuntu`

-   **SSH Public Key Source**: Use existing public key

-   **SSH Public Key**: Paste your public key

### **Inbound Port Rules**

-   **Public Inbound Ports**: Allow selected ports

-   **Selected Ports**: HTTP (80), HTTPS (443), SSH (22)

1.  Click **Review + Create**, then **Create** to deploy the virtual machine.

2.  Wait for the deployment to complete and click **Go to resource**.

3.  Note the public IP address of the virtual machine for SSH access.

* * * * *

Step 4: Access the Virtual Machine with SSH
-------------------------------------------

1.  Use the public IP address of the VM to connect:

    ```
    ssh ubuntu@<vm_public_ip>
    ```

2.  Confirm the fingerprint when prompted:

    ```
    The authenticity of host '<vm_public_ip>' can't be established.
    Are you sure you want to continue connecting (yes/no/[fingerprint])? yes
    ```

3.  To validate the fingerprint, you can:

    ```
    find /etc/ssh -name '*.pub' -exec ssh-keygen -l -f {} \;
    ```

    Alternatively, use the Azure CLI:

    ```
    az vm run-command invoke\
      --resource-group <resource_group>\
      --name <vm_name>\
      --command-id RunShellScript\
      --scripts "find /etc/ssh -name '*.pub' -exec ssh-keygen -l -f {} \;"
    ```

* * * * *

Step 5: Update and Secure the Virtual Machine
---------------------------------------------

1.  Update packages:

    ```
    sudo apt update
    sudo apt upgrade
    ```

2.  Reboot the VM:

    ```
    sudo reboot
    ```

**Security Measures:**

-   SSH access is restricted to your key pair.

-   Only essential ports (22, 80, 443) are open.

-   Packages are kept up to date.

For advanced security, refer to [How to Secure a Linux Server](https://github.com/imthenachoman/How-To-Secure-A-Linux-Server).

* * * * *

Step 6: Install Docker and Docker Compose
-----------------------------------------

1.  Install Docker and Docker Compose by following this guide.

2.  Verify the installation:

    ```
    docker --version
    docker-compose --version
    ```

* * * * *

Congratulations!
----------------

Your virtual machine is now set up and ready for use. You have:

-   Acquired a virtual machine with Azure for Students credits.

-   Configured SSH access and updated the system.

-   Installed Docker and Docker Compose for further development.

Continue with the course materials to deploy applications on your VM.


Publish and Run Your Docker Applications with GitHub Container Registry
=======================================================================

This guide explains how to publish your Docker images to GitHub Container Registry and how to use them with Docker and Docker Compose.

* * * * *

Publish Your Applications with Docker
-------------------------------------

### Why Use GitHub Container Registry?

Publishing Docker images to GitHub Container Registry allows you to share your images with others securely. Follow these steps to publish your own images.

* * * * *

### 1\. Create a Personal Access Token

To authenticate with GitHub Container Registry, you need a Personal Access Token (PAT):

1.  Go to your GitHub account settings:

    -   Navigate to **Settings** > **Developer settings** > **Personal access tokens** > **Tokens (classic)**.

2.  Generate a new token with the required permissions by following [GitHub's official guide](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-container-registry).

3.  Copy and save the token securely. You will use it instead of your GitHub password for authentication.

* * * * *

### 2\. Log In to GitHub Container Registry

Authenticate with GitHub Container Registry by running the following command, replacing `<username>` with your GitHub username:

```
docker login ghcr.io -u <username>
```

When prompted for a password, paste the Personal Access Token you created earlier.

**Expected Output:**

```
Login Succeeded
```

* * * * *

### 3\. Tag Your Docker Image

Docker images must be correctly tagged to upload them to GitHub Container Registry. Use the following format:

```
ghcr.io/<username>/<image>:<tag>
```

For example, to tag the image `java-ios-docker` for your username, run:

```
docker tag java-ios-docker ghcr.io/<username>/java-ios-docker:latest
```

**Verify the Tagging:** List all Docker images to confirm the tagging:

```
docker images
```

**Sample Output:**

```
REPOSITORY                           TAG       IMAGE ID       CREATED         SIZE
java-ios-docker                      latest    8214c1a1c97c   3 minutes ago   282MB
ghcr.io/<username>/java-ios-docker   latest    8214c1a1c97c   3 minutes ago   282MB
```

You can optionally remove the local `java-ios-docker` image:

```
docker rmi java-ios-docker
```

* * * * *

### 4\. Push the Image to GitHub Container Registry

Publish the image to GitHub Container Registry:

```
docker push ghcr.io/<username>/java-ios-docker
```

**Expected Output:**

```
The push refers to repository [ghcr.io/<username>/java-ios-docker]
130abe5d3a5e: Pushed
...
latest: digest: sha256:d0d83a97c4522ddbeb8968e9d509fdebecf0450ca1651c13c14ca774f01e8675 size: 1784
```

* * * * *

### 5\. Verify the Published Image

Go to the GitHub Container Registry page to confirm the image has been successfully uploaded. Replace `<username>` with your GitHub username:

```
https://github.com/<username>?tab=packages
```

The image is private by default. You can change its visibility in the image settings if needed.

* * * * *

### Congratulations!

You have successfully published your Docker image to GitHub Container Registry. To pull the image, use:

```
docker pull ghcr.io/<username>/java-ios-docker:latest
```

* * * * *

Run Your Applications with Docker and Docker Compose
----------------------------------------------------

### 1\. Pull the Image from GitHub Container Registry

Pull the image using the following command, replacing `<username>` with your GitHub username:

```
docker pull ghcr.io/<username>/java-ios-docker
```

**Sample Output:**

```
Using default tag: latest
latest: Pulling from <username>/java-ios-docker
...
Status: Downloaded newer image for ghcr.io/<username>/java-ios-docker:latest
```

* * * * *

### 2\. Run the Image with Docker

Run the image with the following commands, replacing `<username>` with your GitHub username:

#### Write a 100-byte File:

```
docker run --rm -v "$(pwd):/data" ghcr.io/<username>/java-ios-docker\
  --implementation BUFFERED_BINARY\
  /data/100-bytes.bin\
  write\
  --size 100
```

#### Read the 100-byte File:

```
docker run --rm -v "$(pwd):/data" ghcr.io/<username>/java-ios-docker\
  --implementation BUFFERED_BINARY\
  /data/100-bytes.bin\
  read
```

The results will match those obtained when running the image locally, but now the image is pulled from GitHub Container Registry.

* * * * *

### 3\. Run the Image with Docker Compose

Create a Docker Compose file to manage the image. The file will define two services:

1.  `**writer**`: Writes a `100-bytes.bin` file to the `/data` volume.

2.  `**reader**`: Reads the `100-bytes.bin` file from the `/data` volume.

Take your time to create the Docker Compose file, understanding each instruction and its purpose. Once ready, run the following commands:

#### Write the File:

```
docker compose up writer
```

#### Read the File:

```
docker compose up reader
```

* * * * *

### Congratulations!

You have successfully published, pulled, and run your Docker applications using GitHub Container Registry. This demonstrates how to manage, share, and use Docker images effectively in your projects.