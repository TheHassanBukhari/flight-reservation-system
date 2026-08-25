# Flight Reservation System (1st Semester Java Project)
This repository contains my **first semester Java project**: a **Flight Reservation System**. It is a **console-based application** that allows users to **view available flights, book tickets, cancel bookings, and manage flights** (admin functionality). This project demonstrates fundamental Java programming concepts, including **object-oriented programming, arrays, file handling, and user input management**.

**Portfolio:** [hassanbukhari.is-a.dev](https://hassanbukhari.is-a.dev/)

---
## Features
* View all available flights with details (ID, source, destination, price, seats left)
* Book a ticket and generate a ticket file
* Cancel a booked ticket
* Admin menu to add or remove flights
* Persistent storage of flights and bookings using text files (`flights.txt` and `bookings.txt`)
* Seat layout display (`O` = available, `X` = booked)
---
## How to Run
1. **Clone the repository:**
```bash
git clone https://github.com/TheHassanBukhari/flight-reservation-system.git
```
2. **Compile all Java files:**
```bash
javac Project.java
```
3. **Run the program:**
```bash
java Project
```
> Make sure the `Data` folder exists, or the program will create it automatically when loading flights or bookings.
---
## Learning Outcomes
* Practiced **Java syntax, loops, and conditional statements**
* Applied **object-oriented programming** concepts using arrays and multi-dimensional arrays
* Learned **file handling** for persistent storage
* Gained experience in building a **console-based application** with a user-friendly menu
* Understood **basic admin functionalities** and simple CRUD operations
---
## Admin Access
* Admin can add or remove flights using a password-protected menu.
* Default password: `admin123`
---
## Additional Resources

For a **detailed explanation of the code, design, and implementation**, visit my documentation:
[Full Project Explanation](https://thehassanbukhari.github.io/flight-reservation-system/)

---
## Author
[Syed Hassan Ali Bukhari](https://hassanbukhari.is-a.dev/)

---
## License
This project is licensed under the **MIT License** – see the [LICENSE](LICENSE) file for details.

---
