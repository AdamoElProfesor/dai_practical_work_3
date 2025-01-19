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