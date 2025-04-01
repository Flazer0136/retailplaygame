# 🎮 🕹️Retail Play & Game(RPG): Online Store for Used Console Games 🖥️
 Retail Play & Game (RPG) is an online store for used console games designed to provide users with easy access to cheap games. The platform enables users to buy various console games such as Playstation, Xbox, and Nintendo, giving them the opportunity to enjoy these games without the financial burden of buying full-price versions. The system streamlines the buying process, allowing for quick browsing, easy payments, creating a seamless experience for gamers.

## Authors 
- zhoda-lii
- Flazer0136
- AronLimos
- lancemiranoo

## Installation & Setup

**Pre-requisites:** MySQL Database & Workbench

### 1. Prepare MySQL Database Schema

Prepare the MySQL database by running the application first and then overriding the database schema.

- #### 1.1 Navigate the project directory and update the `user`, `password` and `port` in application.properties:

- #### 1.2 Run the app to initialize the database in MySQL.

- #### 1.3 In MySQL Workbench, start a connection with that user and run the `schema.sql` from sqlscripts folder in the project directory.

- #### 1.4 In MySQL Workbench, run the `seed_data_schema.sql` from sqlscripts folder in the project directory.

- #### 1.5 The MySQL database schema is now prepared. 




### 2. Prepare User Roles

#### 2.1 Customer
- Create a new user through the register page in the app, and login through it.

#### 2.2 Owner
- Create a new user through the register page in the app (assume userID = 15).
- In MySQL workbench, insert `ROLE_OWNER` in the authorities table using the same userID.
```
authorityID | userID | authority
12          | 15     | ROLE_CUSTOMER
13          | 15     | ROLE_OWNER     <-- INSERT THIS ROW
```
- Login through the app using the registered credentials.

#### 2.3 Admin
- Create a new user through the register page in the app (assume userID = 16).
- In MySQL workbench, insert `ROLE_ADMIN` in the authorities table using the same userID.
```
authorityID | userID | authority
14          | 16     | ROLE_CUSTOMER
15          | 16     | ROLE_ADMIN     <-- INSERT THIS ROW
```
- Login through the app using the registered credentials.


### 3. The application setup is now complete. 
