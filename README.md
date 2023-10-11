# **RideSharingSystem**

## Introduction
This ride-sharing application is designed to connect riders and drivers for on-demand transportation services. It allows users to create ride requests, track their rides in real-time, and manage their accounts. This README provides an overview of the application, its features, and how to set it up and run it.

## Table of Contents

- Features
- Technologies
- Installation
- Configuration
- API Endpoints

## Features
1. User Management:
- Users can register and log in with their credentials.
- User roles for riders and drivers.
2. Ride Requests:
- Users can create, accept and cancel ride requests.
- Option to select a preferred driver (if available).
- Real-time tracking of ride status and location updates.
3. Real-Time Location Updates:
- Drivers can update their location in real-time.
- Riders can track their assigned driver's location.


## Technologies
- Java 11
- Spring Boot 2.x
- Spring Security with JWT
- Spring Data JPA
- WebSocket (for real-time updates)
- MySQL 8.x
- Maven for project management
- Tomcat 9.x


## Installation
1. Clone the Repository:
``` bash
$ git clone https://github.com/yourusername/ride-sharing-app.git
$ cd ride-sharing-app
```
2. Build use maven:
``` bash
$ mvn clean package
```
3. Configuration:

#### Development environment

 Change value in <> to correct values in src/main/resources/application-properties

- mysql.datasource.url=jdbc:mysql://<mysql_server>:<mysql_server_port>/<db_name>?serverTimezone=UTC&uselegecyDatetimecode=false
- mysql.datasource.username=<db_username>
- mysql.datasource.password=<db_password>


Change value in <> to correct values in src/main/resources/application.properties

IMPORTANT! application.properties contains common properties for all available environments

- spring.mail.host=<mail_server_host>
- spring.mail.port=<mail_server_port>
- spring.mail.username=<from_email_address>
- spring.mail.password=<from_email_address_password>
- face.detection.url=<face_detection_service_url>

#### Configure Spring Security settings for authentication and authorization.


## API Endpoints
- Provide a detailed list of available API endpoints, request and response formats, and authentication requirements.
## Recommended modules
All dependencies are included in pom.xml
In case of using additional libraries (non-public), maven installation instructions will be provided


Run as web application using apache tomcat web <br/>



