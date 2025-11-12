# 🌟 JDBC Shop Console – Java Mini Project  
> 🧠 A full-featured **Java JDBC console application** demonstrating CRUD, Transaction Management, and Reporting with MySQL.

---

## 🏗️ Overview  
This console-based project simulates a small **sales management system** using JDBC.  
It contains 5 related tables — `categories`, `customers`, `products`, `orders`, `order_items` — to help you **master JDBC fundamentals**: CRUD operations, JOIN queries, transactions, and reporting.

---

## ✨ Features
| Module | Description |
|:-------|:-------------|
| 🧱 **Category / Product / Customer** | Full CRUD with JDBC |
| 🧾 **Order Management** | Transaction-based order creation (commit/rollback) |
| 💰 **Reports** | Revenue by product & customer using `JOIN + GROUP BY` |
| 🔒 **TransactionManager** | Centralized transaction handling with `ThreadLocal<Connection>` |
| ⚙️ **DAO Layer** | Clean, reusable Data Access Objects |
| 🖥️ **Console UI** | Text-based menu system for easy navigation |

---

## 🧩 Database Schema (MySQL)
```sql
CREATE TABLE categories (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

CREATE TABLE customers (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL
);

CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  quantity INT NOT NULL,
  category_id INT NOT NULL,
  FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  customer_id INT NOT NULL,
  order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  total DECIMAL(12,2) DEFAULT 0,
  FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE order_items (
  id INT AUTO_INCREMENT PRIMARY KEY,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (order_id) REFERENCES orders(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);
```

---

## ⚙️ Project Structure
```
jdbc-shop-console/
 ├── src/main/java/com/demo/jdbc/
 │   ├── dao/
 │   ├── dao/impl/
 │   ├── model/
 │   ├── service/
 │   ├── service/impl/
 │   ├── util/
 │   └── App.java
 ├── pom.xml
 ├── .gitignore
 └── README.md
```

---

## 🧠 Key Concepts
- JDBC API (Connection, PreparedStatement, ResultSet)
- SQL JOINs, GROUP BY, Aggregate Functions
- Manual Transaction Control (`commit`, `rollback`)
- Clean 3-layer Architecture (DAO → Service → Console)
- Reusable `TransactionManager` for consistent DB handling

---

## 🚀 Getting Started

### 1️⃣ Clone the Project
```bash
git clone https://github.com/be-better-be-stronger/jdbc-shop-console.git
cd jdbc-shop-console
```

### 2️⃣ Setup Database
- Create schema `jdbc_shop` in MySQL  
- Execute the SQL above to generate tables  
- Configure your credentials in `DB.java`:
  ```java
  private static final String URL = "jdbc:mysql://localhost:3306/jdbc_shop";
  private static final String USER = "root";
  private static final String PASSWORD = "your_password";
  ```

### 3️⃣ Run the App
```bash
mvn clean compile exec:java -Dexec.mainClass="com.demo.jdbc.App"
```

---

## 🧮 Console Menu Preview
```
===== MAIN MENU =====
1. Manage Categories
2. Manage Customers
3. Manage Products
4. Manage Orders
5. Reports
0. Exit

-- Manage Categories --
1. View all
2. Create new
3. Update
4. Delete
0. Return

-- Manage Customers --
1. View all
2. Create new
3. Update
4. Delete
0. Return

-- Manage Products --
1. View all
2. Create new
3. Update
4. Delete
5. Group by category
0. Return

-- Manage Orders --
1. Create new order
2. Order details
0. Return

-- Reports --
1. Revenue by Product
2. Revenue by Customer
```

---

## 📊 Sample Output
```
===== REVENUE BY PRODUCT =====
Product Name                Quantity        Revenue
----------------------------------------------------
Sản phẩm A                      5        1,350,000.00
Sản phẩm B                      3          810,000.00
----------------------------------------------------

===== REVENUE BY CUSTOMER =====
Customer Name              Orders          Revenue
----------------------------------------------------
Nguyễn Văn A                    2        1,080,000.00
Trần Thị B                      1          540,000.00
----------------------------------------------------
```

---

## 🧰 Technologies Used
| Category | Tools |
|:----------|:------|
| Language | Java 21 |
| Database | MySQL 8.0 |
| Build Tool | Maven |
| IDE | Eclipse 2025-03 |
| Version Control | Git + GitHub |

---

## 🧑‍💻 Author
**Đặng Quốc Thanh - dangquocthanh.la@gmail.com**  
🎓 FPT Polytechnic Alumni  
💻 Java Web Fullstack Developer  
🌐 [GitHub Profile](https://github.com/be-better-be-stronger)

---

## 🪪 License
This project is licensed under the [MIT License](LICENSE).  
Feel free to fork, modify, and use it for learning or personal projects.

---

## 🌈 Future Enhancements
- [ ] Export reports to Excel / PDF
- [ ] Integrate Hibernate ORM
- [ ] Add JUnit tests
- [ ] Migrate to Spring Boot + Angular

---

## ❤️ Support
If this project helps you learn JDBC faster:  
⭐ **Give it a star** & share it to inspire other Java learners!

<p align="center">
  <img src="https://img.shields.io/github/stars/be-better-be-stronger/jdbc-shop-console?style=social" />
</p>
