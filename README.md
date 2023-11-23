# Welcome to Accommodation Booking Service project!

* ### Do you have an **accommodation** booking business? 


* #### But are all processes carried out in a __bullet-proof way of manual recording and administration__?

* #### Is all data stored in dusty shelves with paper folders?

* #### Do you want to expand to the modern majority?



## If at least one option affected your problem, then **come to us**!

---

Our team, QuackCraft, offers you a solution to all your problems. \
We designed and implemented all the **necessary business processes** that will certainly help you in your development!

---


# Description of the project

## in this project we used the following technologies:

<details>
  <summary><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZqRFNAis0vxGXeQDFA2thujnilvYO8eqTKDX5QgJ5APGtLTNQu0-d6rTkb8oSWOdyRyY&usqp=CAU" width="30"/> Java</summary>
  
`In this project, we used Java as the main programming language.`
</details>

<details>
  <summary><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwsq-7f5BWyog4cdeT1sQaYLVzhJ0o37Up8TjHvVU08WUgfyyMMRMHTVwJ5XReSjyhZa0&usqp=CAU" width="30"/> Spring Boot</summary>
 
`A powerful framework for building Java-based applications.`
</details>

<details>
  <summary><img src="https://www.baeldung.com/wp-content/uploads/2021/02/lsd-module-icon-1.png" width="30"/> Spring Data JPA</summary>
  
`Simplifies data access and persistence with JPA (Java Persistence API).`
</details>

<details>
  <summary><img src="https://www.javacodegeeks.com/wp-content/uploads/2014/07/spring-security-project.png" width="30"/> Spring Security</summary>
  
`Enables robust and secure authentication and authorization mechanisms.`
</details>

<details>
  <summary><img src="https://oddblogger.com/wp-content/uploads/2021/03/swagger-logo-2.png" width="30"/> Swager</summary>
  
 `Provides API documentation.`
</details>

<details>
  <summary><img src="https://www.freepnglogos.com/uploads/logo-mysql-png/logo-mysql-mysql-logo-png-images-are-download-crazypng-21.png" width="30"/> MySQL </summary>
  
` Utilization of a relational database to store information about: `

` * 🏨 accommodations;  ` \
` * 🦲 users; ` \
` * addresses; ` \
` * bookings; ` \
` * payments. `

</details>

<details>
  <summary><img src="https://repository-images.githubusercontent.com/444861690/5f8b5fb8-fb79-447b-8e52-2ecd52743e41" width="30"/> Telegram API</summary>
  
` Used to send notifications to administrators via Telegram. `
</details>

<details>
  <summary><img src="https://cdn-icons-png.flaticon.com/512/5968/5968312.png" width="30"/> Stripe API</summary>
  
`Integrated with Stripe for secure payment processing.`
</details>

<details>
  <summary><img src="https://philiaweb.com/uploads/image/google-maps.png" width="30"/> Google Maps API</summary>

`Used to display the location of accommodation.`
</details>

<details>
  <summary><img src="https://cdn-icons-png.flaticon.com/512/919/919853.png" width="30"/> Docker</summary>
  
`Used for containerization of the application and database.`
</details>

<details>
  <summary><img src="https://user-images.githubusercontent.com/1204509/79262490-b2012a80-7e91-11ea-82fa-e791f8b4d177.jpg" width="30"/> Lombok</summary>

`Reduces boilerplate code with annotations.`
</details>

<details>
  <summary><img src="https://1.bp.blogspot.com/-C5lGqSQuCic/WX39mN-OhdI/AAAAAAAAALU/qUZQdUPTvmInwGSKAYfcZ-QA_PXxhXCXwCLcBGAs/s1600/mapstruct.png" width="30"/> Mapstruct</summary>

`Simplifies object mapping between DTOs and entities.`
</details>

<details>
  <summary><img src="https://www.liquibase.org/wp-content/themes/liquibase/assets/img/cta-icon.svg" width="30" height="30"/> Liquibase </summary>

`Ensures the application database is updated along with the application code.`
</details> 


---

# Project architecture: 
\
![architecture](assets/architecture.png) 

---

# Database structure:
\
![scheme](assets/db_scheme.png)

---

# Project controllers with the following endpoints:


---

## **Authentication Controller:**

| **HTTP method** | **Endpoint**  | **Role** | **Function** |
|:----------------:|:--------------:|:--------:|:-------------|
| POST | /register | ALL | Allows users to register a new account. |
| POST | /login | ALL | Get JWT tokens for authentication. |

---

## **User Controller:** _Managing authentication and user registration_

| **HTTP method** | **Endpoint**          | **Role** | **Function**                                                        |
|:----------------:|:----------------------:|:--------:|:--------------------------------------------------------------------|
| PUT              | /users/{id}/role      |  ADMIN   | Enables admins to update their roles, providing role-based access.  |
| GET              | /users/me             |   ALL    | Retrieves the profile information for the currently logged-in user. |
| PUT/PATCH        | /users/me             |   ALL    | Allows users to update their profile information.                   |

---

## **Accommodation Controller:** _Managing accommodation inventory (CRUD for Accommodations)_

| **HTTP method** | **Endpoint**         | **Role** | **Function**                                         |
|:----------------:|:---------------------:|:--------:|:-----------------------------------------------------|
| POST             | /accommodations      |  ADMIN   | Permits the addition of new accommodations.          |
| GET              | /accommodations      |   ALL    | Provides a list of available accommodations.        |
| GET              | /accommodations/{id} |   ALL    | Retrieves detailed information about a specific accommodation. |
| PUT/PATCH        | /accommodations/{id} |  ADMIN   | Allows updates to accommodation details, including inventory management. |
| DELETE           | /accommodations/{id} |   ADMIN    | Enables the removal of accommodations.                |

---

## **Booking Controller**: _Managing users' bookings_.

| **HTTP method** | **Endpoint**         | **Role**   | **Function**                                          |
|:----------------:|:---------------------:|:----------:|:------------------------------------------------------|
| POST             | /bookings            | ALL        | Permits the creation of new accommodation bookings.   |
| GET              | /bookings/?user_id=...&status=... | ADMIN | Retrieves bookings based on user ID and their status. |
| GET              | /bookings/my         | ALL        | Retrieves user bookings.                              |
| GET              | /bookings/{id}       | ALL        | Provides information about a specific booking.        |
| PUT/PATCH        | /bookings/{id}       | ADMIN        | Allows admins to update  booking details.        |
| DELETE           | /bookings/{id}       | ADMIN        | Enables the cancellation of bookings.                 |

---

## **Payment Controller (Stripe)**: _Facilitates payments for bookings through the platform. Interacts with Stripe API. Use stripe-java library._

| **HTTP method** | **Endpoint**         | **Role**   | **Function**                                         |
|:----------------:|:---------------------:|:----------:|:-----------------------------------------------------|
| GET              | /payments/?user_id=...| ALL        | Retrieves payment information for users.             |
| POST             | /payments/            | ALL        | Initiates payment sessions for booking transactions. |
| GET              | /payments/success/    | ALL        | Handles successful payment processing through Stripe redirection. |
| GET              | /payments/cancel/     | ALL        | Manages payment cancellation and returns payment paused messages during Stripe redirection. |
 

---

# About notification:

\
`Notifications about:` \
`- new bookings created/canceled,` \
`- new created/released accommodations,` \
`- and successful payments` \
`Other services interact with it to send notifications to booking service administrators.` 

###### Used Telegram API, Telegram Chats, and Bots.

---

