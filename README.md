# Tours and Travel Management System

A Java-based Tours and Travel Management System that helps manage customer details, bookings, packages, tickets, and payments.  
The project integrates with a MySQL database for storing travel and customer-related data.

---

## 📂 Project Structure
toursandtravel/
│── src/ # Java source code
│ ├── Main.java # Entry point of the program
│ ├── CustomerDetails.java
│ ├── LoginSystem.java
│ ├── PackageDetails.java
│ ├── Payment.java
│ ├── Ticket.java
│ ├── database/ # Database schema
│ │ └── toursandtravel.sql
│ └── dsa/ # Custom Data Structures
│ ├── CustomQueue.java
│ └── LinkedList.java
│
│── bin/ # Compiled .class files
│── .vscode/ # VS Code settings
│── Recycling Bin/ # Old/unused files

yaml
Copy code

---

## ⚙️ Features
- ✅ Customer Registration & Login System  
- ✅ Manage Travel Packages (add, update, view)  
- ✅ Ticket Booking & Payment Handling  
- ✅ MySQL Database Integration  
- ✅ Custom Data Structures (`LinkedList`, `CustomQueue`)  

---

## 🛠️ Technologies Used
- **Java** (Core Java, OOP, Collections)  
- **MySQL** (Database for storing travel records)  
- **JDBC** (Java Database Connectivity)  

---

## 📦 Database Setup
1. Install MySQL and create a database:
   ```sql
   CREATE DATABASE toursandtravel;
Import the schema:

sql
Copy code
SOURCE src/database/toursandtravel.sql;
Update your database credentials in the code (if needed).

▶️ How to Run
Compile the project:

bash
Copy code
javac -d bin src/*.java src/dsa/*.java src/database/*.java
Run the program:

bash
Copy code
java -cp bin;lib/mysql-connector-j-8.0.xx.jar Main
⚠️ Make sure to add the MySQL Connector/J JAR file in your lib/ folder and include it in your classpath.

📌 Future Enhancements
Web-based frontend (Spring Boot / React)

Online booking with payment gateway

Admin panel for managing packages and customers

👨‍💻 Author
Developed by PAL MIYANI
GitHub: palmiyani
