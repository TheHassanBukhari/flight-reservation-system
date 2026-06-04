import java.util.Scanner;
import java.io.*;

public class Project {

    static final int MAX_FLIGHTS = 100;
    static final int ROWS = 5; 
    static final int COLS = 4; 

    static String[] flightIDs = new String[MAX_FLIGHTS];
    static String[] sources = new String[MAX_FLIGHTS];
    static String[] dests = new String[MAX_FLIGHTS];
    static double[] prices = new double[MAX_FLIGHTS];
    static char[][][] seats = new char[MAX_FLIGHTS][ROWS][COLS];
    static int flightCount = 0;

    static Scanner scanner = new Scanner(System.in);

    // Platform-independent file paths
    static final String DATA_DIR = "Data" + File.separator;
    static final String FLIGHTS_FILE = DATA_DIR + "flights.txt";
    static final String BOOKINGS_FILE = DATA_DIR + "bookings.txt";

    public static void main(String[] args) {
        loadFlights();
        loadBookings();
        mainMenu();
    }

    static void loadFlights() {
        try {
            File file = new File(FLIGHTS_FILE);
            // Create directory if it doesn't exist
            File dataDir = new File(DATA_DIR);
            dataDir.mkdirs();
            
            if (!file.exists()) {
                System.out.println(FLIGHTS_FILE + " not found. No flights loaded.");
                return;
            }
            
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine() && flightCount < MAX_FLIGHTS) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length < 4)
                    continue;

                flightIDs[flightCount] = parts[0];
                sources[flightCount] = parts[1];
                dests[flightCount] = parts[2];
                prices[flightCount] = Double.parseDouble(parts[3]);

                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        seats[flightCount][i][j] = 'O';
                    }
                }
                flightCount++;
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(FLIGHTS_FILE + " not found. No flights loaded.");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing price in flights file.");
        }
    }

    static void loadBookings() {
        try {
            File file = new File(BOOKINGS_FILE);
            // Create directory if it doesn't exist
            File dataDir = new File(DATA_DIR);
            dataDir.mkdirs();
            
            if (!file.exists()) {
                System.out.println(BOOKINGS_FILE + " not found. No bookings loaded.");
                return;
            }
            
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length < 3)
                    continue;
                String flightID = parts[0];
                String seat = parts[2]; 

                int index = -1;
                for (int i = 0; i < flightCount; i++) {
                    if (flightIDs[i].equals(flightID)) {
                        index = i;
                        break;
                    }
                }

                if (index != -1) {
                    int row = seat.charAt(0) - 'A';
                    int col = Integer.parseInt(seat.substring(1)) - 1;
                    if (row >= 0 && row < ROWS && col >= 0 && col < COLS) {
                        seats[index][row][col] = 'X'; // mark as booked
                    }
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(BOOKINGS_FILE + " not found. No bookings loaded.");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing seat number in bookings file.");
        }
    }

    static void mainMenu() {
        while (true) {
            System.out.println("\n=============================\n| FLIGHT RESERVATION SYSTEM |\n=============================");
            System.out.println("1. View all flights");
            System.out.println("2. Book ticket");
            System.out.println("3. Cancel ticket");
            System.out.println("4. Admin login");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayFlights();
                    break;
                case "2":
                    bookTicket();
                    break;
                case "3":
                    cancelTicket();
                    break;
                case "4":
                    adminMenu();
                    break;
                case "5":
                    System.out.println("Exiting system. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void displayFlights() {
        if (flightCount == 0) {
            System.out.println("\nNo flights available.");
            return;
        }
        
        System.out.println("\nAvailable Flights:");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s%n", "ID", "From", "To", "Price", "Seats Left");
        for (int i = 0; i < flightCount; i++) {
            int freeCount = 0;
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    if (seats[i][r][c] == 'O')
                        freeCount++;
                }
            }
            System.out.printf("%-10s %-10s %-10s %-10.1f %-10d%n", flightIDs[i], sources[i], dests[i], prices[i], freeCount);

        }
    }

    static void bookTicket() {
        if (flightCount == 0) {
            System.out.println("\nNo flights available for booking.");
            return;
        }
        
        displayFlights();
        System.out.print("\nEnter Flight ID to book: ");
        String flightID = scanner.nextLine().toUpperCase();

        int index = -1;
        for (int i = 0; i < flightCount; i++) {
            if (flightIDs[i].equals(flightID)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Flight not found.");
            return;
        }

        System.out.println("\nSeat layout (O=available, X=booked):");
        System.out.print("  ");
        for (int c = 1; c <= COLS; c++)
            System.out.print(c + " ");
        System.out.println();
        for (int r = 0; r < ROWS; r++) {
            System.out.print((char) ('A' + r) + " ");
            for (int c = 0; c < COLS; c++) {
                System.out.print(seats[index][r][c] + " ");
            }
            System.out.println();
        }

        System.out.print("Enter seat (e.g., A1): ");
        String seat = scanner.nextLine().toUpperCase();
        if (seat.length() < 2) {
            System.out.println("Invalid seat format.");
            return;
        }

        int row = seat.charAt(0) - 'A';
        int col;
        try {
            col = Integer.parseInt(seat.substring(1)) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid seat number.");
            return;
        }

        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            System.out.println("Seat out of range.");
            return;
        }

        if (seats[index][row][col] == 'X') {
            System.out.println("Seat already booked.");
            return;
        }

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        
        seats[index][row][col] = 'X'; 

        try {
            // Ensure directory exists
            new File(DATA_DIR).mkdirs();
            FileWriter writer = new FileWriter(BOOKINGS_FILE, true);
            writer.write(flightID + "," + name + "," + seat + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error occurred during saving booking.");
            return;
        }

        String ticketFileName = DATA_DIR + "ticket_" + flightID + "_" + seat + ".txt";
        try {
            // Ensure directory exists
            new File(DATA_DIR).mkdirs();
            PrintWriter ticket = new PrintWriter(ticketFileName);
            ticket.println("||| FLIGHT TICKET DETAILS |||");
            ticket.println("Name: " + name);
            ticket.println("Flight ID: " + flightID);
            ticket.println("From: " + sources[index]);
            ticket.println("To: " + dests[index]);
            ticket.println("Seat: " + seat);
            ticket.println("Price: " + prices[index]);
            ticket.close();
            System.out.println("Ticket booked. Details saved to " + ticketFileName);
        } catch (IOException e) {
            System.out.println("Error generating ticket file.");
        }
    }

    static void cancelTicket() {
        if (flightCount == 0) {
            System.out.println("\nNo flights available.");
            return;
        }
        
        System.out.print("\nEnter Flight ID to cancel: ");
        String flightID = scanner.nextLine().toUpperCase();

        int index = -1;
        for (int i = 0; i < flightCount; i++) {
            if (flightIDs[i].equals(flightID)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Flight not found.");
            return;
        }

        System.out.print("Enter seat to cancel (e.g., A1): ");
        String seat = scanner.nextLine().toUpperCase();
        if (seat.length() < 2) {
            System.out.println("Invalid seat format.");
            return;
        }

        int row = seat.charAt(0) - 'A';
        int col;
        try {
            col = Integer.parseInt(seat.substring(1)) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid seat number.");
            return;
        }

        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            System.out.println("Seat out of range.");
            return;
        }

        if (seats[index][row][col] == 'O') {
            System.out.println("This seat is not currently booked.");
            return;
        }

        seats[index][row][col] = 'O';

        try {
            File file = new File(BOOKINGS_FILE);
            File tempFile = new File(DATA_DIR + "temp_bookings.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length < 3)
                    continue;
                String id = parts[0];
                String bookedSeat = parts[2];
                if (id.equals(flightID) && bookedSeat.equals(seat)) {
                    found = true;
                    continue; // Skip this line (don't write it to temp file)
                }
                writer.println(line);
            }
            reader.close();
            writer.close();
            
            if (found) {
                if (file.delete()) {
                    if (!tempFile.renameTo(file)) {
                        System.out.println("Error updating bookings file.");
                    }
                } else {
                    System.out.println("Error updating bookings file.");
                }
                System.out.println("Booking canceled and seat freed.");
            } else {
                tempFile.delete();
                System.out.println("Booking record not found, but seat was freed.");
            }
        } catch (IOException e) {
            System.out.println("Error updating bookings file.");
        }
    }

    static void adminMenu() {
        System.out.print("Enter admin password: ");
        String pwd = scanner.nextLine();
        if (!pwd.equals("admin123")) {
            System.out.println("Incorrect password.");
            return;
        }

        while (true) {
            System.out.println("\n  Admin Menu ");
            System.out.println("1. Add flight");
            System.out.println("2. Remove flight");
            System.out.println("3. Back to main menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                addFlight();
            } else if (choice.equals("2")) {
                removeFlight();
            } else if (choice.equals("3")) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    static void addFlight() {
        if (flightCount >= MAX_FLIGHTS) {
            System.out.println("Maximum number of flights reached.");
            return;
        }

        System.out.print("Enter new Flight ID: ");
        String flightID = scanner.nextLine().toUpperCase();
        if (flightID.trim().isEmpty()) {
            System.out.println("Flight ID cannot be empty.");
            return;
        }

        for (int i = 0; i < flightCount; i++) {
            if (flightIDs[i].equals(flightID)) {
                System.out.println("Flight ID already exists.");
                return;
            }
        }

        System.out.print("Enter source: ");
        String src = scanner.nextLine();
        if (src.trim().isEmpty()) {
            System.out.println("Source cannot be empty.");
            return;
        }
        
        System.out.print("Enter destination: ");
        String dst = scanner.nextLine();
        if (dst.trim().isEmpty()) {
            System.out.println("Destination cannot be empty.");
            return;
        }
        
        System.out.print("Enter price: ");
        double price;
        try {
            price = Double.parseDouble(scanner.nextLine());
            if (price <= 0) {
                System.out.println("Price must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price.");
            return;
        }

        flightIDs[flightCount] = flightID;
        sources[flightCount] = src;
        dests[flightCount] = dst;
        prices[flightCount] = price;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                seats[flightCount][i][j] = 'O';
            }
        }

        try {
            // Create directory if it doesn't exist
            new File(DATA_DIR).mkdirs();
            
            PrintWriter writer = new PrintWriter(new FileWriter(FLIGHTS_FILE, true));
            writer.println(flightID + "," + src + "," + dst + "," + price);
            writer.close();
            flightCount++;
            System.out.println("Flight added.");
        } catch (IOException e) {
            System.out.println("Error writing to flights.txt.");
        }
    }

    static void removeFlight() {
        if (flightCount == 0) {
            System.out.println("No flights to remove.");
            return;
        }
        
        System.out.print("Enter Flight ID to remove: ");
        String flightID = scanner.nextLine().toUpperCase();

        int index = -1;
        for (int i = 0; i < flightCount; i++) {
            if (flightIDs[i].equals(flightID)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Flight not found.");
            return;
        }

        for (int i = index; i < flightCount - 1; i++) {
            flightIDs[i] = flightIDs[i + 1];
            sources[i] = sources[i + 1];
            dests[i] = dests[i + 1];
            prices[i] = prices[i + 1];
            seats[i] = seats[i + 1];
        }

        flightCount--;

        try {
            // Update flights.txt
            File file = new File(FLIGHTS_FILE);
            File tempFile = new File(DATA_DIR + "temp_flights.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 1)
                    continue;
                if (!parts[0].equals(flightID)) {
                    writer.println(line);
                }
            }
            reader.close();
            writer.close();
            
            if (file.delete()) {
                if (!tempFile.renameTo(file)) {
                    System.out.println("Error updating flights file.");
                }
            } else {
                System.out.println("Error updating flights file.");
            }

            // Update bookings.txt
            File bfile = new File(BOOKINGS_FILE);
            if (bfile.exists()) {
                File tempB = new File(DATA_DIR + "temp_bookings.txt");
                BufferedReader bReader = new BufferedReader(new FileReader(bfile));
                PrintWriter bWriter = new PrintWriter(new FileWriter(tempB));
                while ((line = bReader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length < 1)
                        continue;
                    if (!parts[0].equals(flightID)) {
                        bWriter.println(line);
                    }
                }
                bReader.close();
                bWriter.close();
                
                if (bfile.delete()) {
                    if (!tempB.renameTo(bfile)) {
                        System.out.println("Error updating bookings file.");
                    }
                } else {
                    System.out.println("Error updating bookings file.");
                }
            }
            
            System.out.println("Flight and related bookings removed.");
        } catch (IOException e) {
            System.out.println("Error updating flight records.");
        }
    }
}