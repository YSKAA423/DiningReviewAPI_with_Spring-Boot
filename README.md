# 🍽️ Allergy-Friendly Dining Review API

---

Welcome to this Spring Boot REST API project! This project demonstrates how to build a fully functional REST API using **Spring Boot** and **Spring Data JPA**. It’s a great way to apply concepts learned in the [Codecademy "Building REST APIs with Spring Boot and Java"](https://www.codecademy.com/learn/paths/building-rest-apis-with-spring-boot-and-java) skill path, using technologies like **Java**, **Spring Boot**, and **H2 Database**.

---

## 🛠 Technologies Used

- Java 21
- Spring Boot
- H2 Database
- JPA (Hibernate)
- Spring Data JPA
- Lombok

---

## 🚀 Getting Started

### Clone and Run the App

```bash
git clone https://github.com/your-username/allergy-review-api.git
cd allergy-review-api
./mvnw spring-boot:run
```

### Access the H2 Console

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./DiningReviews`
- Username: `sa`
- Password: `password`

---

## 🧪 API Overview

### Users

- **Create User**
  - `POST /users`
  - Request Body:
    ```json
    {
      "userName": "john_doe",
      "city": "Dubai",
      "state": "DU",
      "zipCode": "12345",
      "isInterestedInDairyAllergies": true,
      "isInterestedInPeanutAllergies": false,
      "isInterestedInEggAllergies": true
    }
    ```

- **Get User by Display Name**
  - `GET /users/{display_name}`

- **Update User**
  - `PUT /users/{userName}`
  - Request Body: Partial or full user object

- **Delete User**
  - `DELETE /users`
  - Request Body: Full user object

### Restaurants

- **Create Restaurant**
  - `POST /restaurant`
  - Request Body:
    ```json
    {
      "name": "Allergy-Free Diner",
      "zipCode": "12345"
    }
    ```

- **Get Restaurant by ID**
  - `GET /restaurant/{restaurant_id}`

- **Get All Restaurants**
  - `GET /restaurant/allRestaurants`

- **Search Restaurants by Zip Code and Allergy**
  - `GET /restaurant/search?zipCode=12345&allergy=peanut`

### Dining Reviews

- **Create Dining Review**
  - `POST /dining_review`
  - Request Body:
    ```json
    {
      "userName": "john_doe",
      "restaurantId": 1,
      "peanutScore": 4,
      "eggScore": 5,
      "dairyScore": 3
    }
    ```

### Admin Actions

- **Get Reviews by Status**
  - `GET /admin/reviews?status=pending`

- **Update Review Status**
  - `PUT /admin/reviews/{review_id}`
  - Request Body:
    ```json
    {
      "acceptDiningReview": true
    }
    ```

---

## 🧪 Testing with cURL

### Create a New User

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
        "userName": "john_doe",
        "city": "Dubai",
        "state": "DU",
        "zipCode": "12345",
        "isInterestedInDairyAllergies": true,
        "isInterestedInPeanutAllergies": false,
        "isInterestedInEggAllergies": true
      }'
```

### Create a New Restaurant

```bash
curl -X POST http://localhost:8080/restaurant \
  -H "Content-Type: application/json" \
  -d '{
        "name": "Allergy-Free Diner",
        "zipCode": "12345"
      }'
```

### Submit a Dining Review

```bash
curl -X POST http://localhost:8080/dining_review \
  -H "Content-Type: application/json" \
  -d '{
        "userName": "john_doe",
        "restaurantId": 1,
        "peanutScore": 4,
        "eggScore": 5,
        "dairyScore": 3
      }'
```

### Admin: Get Pending Reviews

```bash
curl http://localhost:8080/admin/reviews?status=pending
```

### Admin: Accept a Review

```bash
curl -X PUT http://localhost:8080/admin/reviews/1 \
  -H "Content-Type: application/json" \
  -d '{
        "acceptDiningReview": true
      }'
```

---

## 📌 Notes

- Only reviews with status `ACCEPTED` influence a restaurant's allergy scores.
- Admins are responsible for moderating reviews and updating restaurant scores accordingly.
- Ensure that the H2 console is enabled and accessible for database inspection.

---

## 📄 License

This project is licensed under the Apache 2.0 License.

---
