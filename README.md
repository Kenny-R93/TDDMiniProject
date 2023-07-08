# TDDMiniProject
Order Management System
This is a simple Order Management System developed using Spring Boot and H2 Database. 
It follows the principles of Test-Driven Development (TDD) and offers basic functionalities related to order handling, including (CRUD Operations) creating, reading, updating, and deleting orders.

Setup and Installation
1) Setup the development environment by including web, hpa, and h2 dependencies.
2) Create the Order entity that includes Id, customerName, orderDate, shippingAddress, and total.
3) Create the test-driven development test cases to test all of CRUD operations such as create, read, update, and delete.

Running the Application
Once the application has been successfully built, you can run it using the java -jar command:
java -jar build/libs/order-management-system-0.0.1-SNAPSHOT.jar

The application will start and you will be able to interact with it at http://localhost:8080.

REST API Endpoints
The application provides the following REST API endpoints:

POST /api/orders: Create a new order
GET /api/orders/{id}: Fetch the details of an order
PUT /api/orders/{id}: Update an existing order
DELETE /api/orders/{id}: Delete an order
