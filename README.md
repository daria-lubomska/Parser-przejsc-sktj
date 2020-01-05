# Cave Club (SKTJ) members achievements
> The web application for managing cave, climbing and other activities achievements for members of Sopot Cave Club.

## Table of contents
* [Overview](#overview)
* [Features](#features)
* [Technologies](#technologies)
* [Status](#status)

## Overview
> The project is dedicated for members of Sopot Cave Club to manage cave, climbing and other activities achievements. Service will be integrated with Ghost CMS and will be available just for logged Club members. Initial data such as cave and climbing achievements comes from previously collected data (CSV format), which were parsed and saved using PostgreSQL DBMS (there are just sample data). The main features such as user and achievements management or live search, filtration of achievements are implemented as a REST endpoints.

## Features

### Caves, Users, Countries live search and achievements filtration.
> Live search functionality includes caves names, countries and users for user-friendly searching and add/edit form filling. Also users are able to filter all achievements by various parameters. Both functionalities are based on <a href="https://mvnrepository.com/artifact/net.kaczmarzyk/specification-arg-resolver" target="_blank">**Specification Arg Resolver**</a> library, which is API for filtering data with Spring MVC and Spring Data JPA.

### Achievements and Users CRUD
> Users has ability to add new achievement (regardless of whether these user is the author of achievement or not). Operation such as editing or deleting of achievements will be available just for achievement notification author and admin, while users management will be available just for admin. Users marked as a super admin cannot be removed od edited by any other user.

### Loggers
> Logs from application and server are logged to the console (level INFO) and to database (level WARN).

## Technologies
* Java SE 11
* Spring Boot 2.2.1
* Tomcat
* PostgreSQL
* Lombok
* OpenCSV 5.0
* jackson datatype hibernate5 2.10.1
* Maven
* Specification Arg Resolver 2.1.1
* SLF4J
* REST API

## Status
> In progress.
