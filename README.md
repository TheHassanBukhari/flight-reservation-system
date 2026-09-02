# Flight Reservation System

This repository contains my first semester Java project, a Flight Reservation System. It's a console based application that allows users to view available flights, book tickets, cancel bookings, and manage flights through an admin menu. This project demonstrates fundamental Java programming concepts, including object oriented programming, arrays, file handling, and user input management. It's also my first ever contribution to GitHub.

**Project Type:** University Project (Solo) <br>
**Course:** Programming Fundamentals, 1st Semester, COMSATS University Islamabad

**Portfolio:** [hassanbukhari.is-a.dev](https://hassanbukhari.is-a.dev/) <br>
**LinkedIn:** [Syed Hassan Ali Bukhari](https://www.linkedin.com/in/syedhassanalibukhari/)

## Features

- View all available flights with details (ID, source, destination, price, seats left)
- Book a ticket and generate a ticket file
- Cancel a booked ticket
- Admin menu to add or remove flights
- Persistent storage of flights and bookings using text files (`flights.txt` and `bookings.txt`)
- Seat layout display (`O` = available, `X` = booked)

## How to Run

```bash
git clone https://github.com/TheHassanBukhari/flight-reservation-system.git
javac Project.java
java Project
```

> Make sure the `Data` folder exists, or the program will create it automatically when loading flights or bookings.

## Learning Outcomes

- Java syntax, loops, and conditional statements
- Object oriented programming using arrays and multi-dimensional arrays
- File handling for persistent storage
- Building a console based application with a user-friendly menu
- Basic admin functionality and simple CRUD operations

## Admin Access

Admin can add or remove flights using a password-protected menu. Default password: `admin123`

## Additional Resources

[Full Project Explanation](https://thehassanbukhari.github.io/flight-reservation-system/)

## Author

[Syed Hassan Ali Bukhari](https://hassanbukhari.is-a.dev/)

## License

This project is licensed under the [MIT License](./LICENSE).
