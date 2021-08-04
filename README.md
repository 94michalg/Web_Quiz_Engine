# Web_Quiz_Engine
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Features](#features)
* [How to use](#how-to-use)
* [Inspiration](#inspiration)
## General info
Server-side quiz engine with user database, that uses REST API to create, solve, browse and delete quizzes. Project has been built based on Spring Boot, all information are stored in H2 database, passwords are encrypted using BCrypt and Hibernate was used as a data access layer. All information are send using JSON format.
## Technologies
* Gradle
* Spring Boot
* H2 Database
* BCrypt (Spring Security)
* Hibernate
## Features
- Create new account
- Add new quiz to database
- Browse and solve the quizzes
- Check your successful completions
- Delete quizzes that you've created
## How to use
First of all, you have to create new account. To do that just send a JSON with email and password (at least 5 characters) via POST request to `/api/register`.

Every quiz has ID, title, text, at least 2 choices and correct answers.

You can browse the quizzes without creating an account. To do that just send GET request to `/api/quizzes` or `/api/quizzes/ID`. The API supports pagination and sorting (10 quizzes on 1 page), so you you can browse the pages with page parameter e.g. `/api/quizzes?page=1`.

To create, solve or delete quizzes you have to be authorized via HTTP Basic Auth, by sending your email and password with each request. To create new quiz send JSON with all data (title, text, options and correct answers) via POST request to `/api/quizzes`. 
![POST example](/images/post.jpg)

There might be only one correct answer, multiple correct answers or empty array (means all answers are wrong).

To solve the quiz send a POST request to `api/quizzes/ID/solve` with correct answers. 

To see all your correct completions you can send GET request to `api/quizzes/completed`.

You can also delete quiz by sending DELETE request to `api/quizzes/ID`. Notice that you can only delete quizzes, that you have created.

## Inspiration
Project has been made in 
