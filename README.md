<div style="display: flex; align-items: center; justify-content: center;">
  <img src="https://www.linkpicture.com/q/favicon_27.png" alt="Icon" width="50" height="50">

</div>
  <h2 align="center">MAKS HR Management System</h2>

  <p align="center" >
MAKS created for commercial companies for managing their human resource processes from one platform
    <br/>
    <br/>

### About This Project

This is a human resources application that includes four distinct roles: admin, company manager, employee, and guest. Each role is equipped with its own dashboard, profile, and pages tailored to their specific responsibilities.

When a company manager registers, their registration is subject to approval by the admin. Once approved, the company manager gains access to the system and can subsequently add employees to the system.

The Employee profile page encompasses personal information, salary details, and shift schedules. The employee dashboard also provides information about public holidays and details about the company they are employed by.

Employees have the option to submit comments regarding their company, but these comments will only be published after receiving approval from the admin.

For company managers, there is a dedicated page with comprehensive information about their company's financial status, including income, expenses, and profit and loss data.

Guests who register with the application can access information and comments pertaining to all companies that are registered within the application.

### Usage
You will need to have Gradle and JDK 8 or higher. The recommended way to run the sample applications is with IDEs like IntelliJ IDEA or Eclipse. You will also need to run MongoDB, PostgreSQL and RabbitMQ in Docker container, so you must have Docker installed on your local machine.

### Scripts
- MongoDB
```
docker run -d -e MONGO_INITDB_ROOT_USERNAME=mongoadmin -e MONGO_INITDB_ROOT_PASSWORD=secret -p 27017:27017 mongo
```
- MongoDB Compass Download (GUI)
```
  https://www.mongodb.com/try/download/compass
```
- PostgreSQL
```
docker run -d --name some-postgres -e POSTGRES_PASSWORD=secret -e PGDATA=/var/lib/postgresql/data/pgdata -v /custom/mount:/var/lib/postgresql/data -p 5432:5432 postgres
```
- pgAdmin
```
https://www.pgadmin.org/download
```
- RabbitMQ
```
docker run -d name some-rabbit -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=secret -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```
### Architecture
- Each microservice has its own database as shown on the following picture.

![Cloud Architecture](/user-microservice/src/main/resources/images/cloud-architecture-2.png)

### Project Deployment
1. In the Gradle tool window, open the project's node, then the Tasks node, and double-click on "build," then double-click on "buildDependents."
2. A JAR file is created under the build directory.

#### A Basic Dockerfile
```
FROM amazoncorretto:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/application.jar"]
```

#### Build an image from a Dockerfile
```
docker build --build-arg JAR_FILE=config-server-git/build/libs/config-server-git-v.0.0.1.jar -t siinemvarol/config-server-git:v.1.0 .
```
#### The sample YAML file is as follows.
- Config Server - Deployment.yaml
```
kind: Deployment
metadata:
  name: deployment-config
spec:
  selector:
    matchLabels:
      app: deployment-pod-label-config
  template:
    metadata:
      labels:
        app: deployment-pod-label-config      
    spec:
      containers: 
      - name: config 
        image: siinemvarol/hrms-config-server-git:v.1.0
        resources:
          requests:
            cpu: "300m"
            memory: "1024Mi"
          limits:
            cpu: "500m"
            memory: "2048Mi"
        ports: 
        - containerPort: 8888
        env:
          - name: GIT_CONFIG_PASS
            value: secret
```
- Config Server - Service.yaml
```
kind: Service
metadata:
  name: clusterip-config-export-ip
spec:
  selector:
    app: deployment-pod-label-config
  type: ClusterIP
  ports:
    - name: configport
      port: 8888
      targetPort: 8888
```
