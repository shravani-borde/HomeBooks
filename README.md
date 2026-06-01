# HomeBooks 📚

A full-stack book management and recommendation platform built using Spring Boot, MySQL, JWT Authentication, and React (frontend in progress).

HomeBooks allows users to discover books, manage personalized reading lists, like and rate books, and receive intelligent recommendations based on their interactions.

---

## Features

### Authentication & Security

* User Registration
* User Login
* BCrypt Password Encryption
* JWT Authentication
* Protected APIs using Spring Security
* Role-ready architecture

### Book Management

* Add Books
* Update Books
* Delete Books
* View All Books
* Search Books
* Pagination & Sorting
* Input Validation

### Personal Reading Lists

* Add Books to TBR (To Be Read)
* Remove Books from TBR
* View Personal TBR List

### Like System

* Like Books
* Unlike Books
* View Liked Books
* Timestamped User Interactions

### Rating System

* Rate Books (0-10 Scale)
* Update Existing Ratings
* View Ratings
* Average Rating Calculation
* Top Rated Books Analytics

### Recommendation-Ready Architecture

* User Interaction Tracking
* TBR Data Collection
* Like Interaction Collection
* Rating Collection
* Analytics Endpoints
* ML/DL Recommendation Integration Planned

---

## Technology Stack

### Backend

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Security
* JWT (JSON Web Tokens)
* Hibernate
* Maven

### Database

* MySQL

### Frontend (Upcoming)

* React.js
* Axios
* Bootstrap / Tailwind CSS

---

## Database Design

### Users

Stores registered user information.

### Books

Stores book catalog information.

### User TBR Books

Maintains personalized reading lists using Many-to-Many relationships.

### Likes

Stores user-book interaction history.

### Ratings

Stores user ratings and timestamps.

---

## API Highlights

### Authentication

#### Register User

POST /api/auth/register

#### Login

POST /api/auth/login

Returns JWT token for accessing protected resources.

---

### Books

#### Get All Books

GET /api/books

#### Get Book By ID

GET /api/books/{id}

#### Add Book

POST /api/books

#### Update Book

PUT /api/books/{id}

#### Delete Book

DELETE /api/books/{id}

---

### TBR

#### Add Book To TBR

POST /api/tbr/{bookId}

#### Remove Book From TBR

DELETE /api/tbr/{bookId}

#### View My TBR

GET /api/tbr

---

### Likes

#### Like Book

POST /api/likes/{bookId}

#### Unlike Book

DELETE /api/likes/{bookId}

#### View Liked Books

GET /api/likes

---

### Ratings

#### Rate Book

POST /api/ratings/{bookId}

#### Average Rating

GET /api/ratings/book/{bookId}/average

#### Top Rated Books

GET /api/ratings/top-rated

---

## Project Architecture

Controller Layer

* Handles HTTP Requests

Service Layer

* Contains Business Logic

Repository Layer

* Database Access

Entity Layer

* Database Models

DTO Layer

* API Response Models

Mapper Layer

* Entity ↔ DTO Conversion

Security Layer

* JWT Authentication
* Spring Security Configuration

---

## Learning Outcomes

This project demonstrates:

* REST API Development
* Spring Boot Application Architecture
* JWT Authentication & Authorization
* Spring Security
* Hibernate & JPA Relationships
* DTO Design Pattern
* Entity Mapping
* Pagination & Sorting
* Exception Handling
* Data Validation
* Analytics Queries
* Recommendation System Preparation

---

## Future Enhancements

* React Frontend
* Role-Based Authorization (ADMIN / USER)
* Recommendation Engine
* Machine Learning Integration
* Deep Learning-Based Recommendations
* Book Reviews & Comments
* Book Cover Uploads
* User Profiles
* Dashboard Analytics
* Swagger API Documentation

---

## Author

Shravani Borde

B.Tech Computer Science & Engineering

Passionate about Backend Development, Artificial Intelligence, and Building Intelligent Software Systems.
