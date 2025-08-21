import java.io.*;
import java.util.*;

// --- Room class ---
class Room {
    int roomNumber;
    String category; // Standard, Deluxe, Suite
    double price;
    boolean isBooked;

    Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isBooked = false;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " | " + category + " | $" + price + " | " + (isBooked ? "Booked" : "Available");
    }
}

// --- Reservation class ---
class Reservation {
    String guestName;
    int roomNumber;
    double amountPaid;

    Reservation(String guestName, int roomNumber, double amountPaid) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.amountPaid = amountPaid;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room: " + roomNumber + " | Paid: $" + amountPaid;
    }
}

// --- Hotel class ---
class Hotel {
    List<Room> rooms = new ArrayList<>();
    List<Reservation> reservations = new ArrayList<>();
    final String FILE_NAME = "reservations.txt";

    Hotel() {
        // Predefined rooms
        rooms.add(new Room(101, "Standard", 100));
        rooms.add(new Room(102, "Standard", 100));
        rooms.add(new Room(201, "Deluxe", 200));
        rooms.add(new Room(202, "Deluxe", 200));
        rooms.add(new Room(301, "Suite", 300));
        loadReservations();
    }

    void viewRooms() {
        System.out.println("\n--- Room Availability ---");
        for (Room r : rooms) {
            System.out.println(r);
        }
    }

    void makeReservation(String guestName, int roomNumber) {
        for (Room r : rooms) {
            if (r.roomNumber == roomNumber && !r.isBooked) {
                r.isBooked = true;
                double payment = simulatePayment(r.price);
                Reservation res = new Reservation(guestName, roomNumber, payment);
                reservations.add(res);
                saveReservations();
                System.out.println("✅ Reservation successful: " + res);
                return;
            }
        }
        System.out.println("❌ Room not available!");
    }

    void cancelReservation(String guestName, int roomNumber) {
        Iterator<Reservation> it = reservations.iterator();
        while (it.hasNext()) {
            Reservation res = it.next();
            if (res.guestName.equalsIgnoreCase(guestName) && res.roomNumber == roomNumber) {
                it.remove();
                for (Room r : rooms) {
                    if (r.roomNumber == roomNumber) r.isBooked = false;
                }
                saveReservations();
                System.out.println("✅ Reservation cancelled for " + guestName + " (Room " + roomNumber + ")");
                return;
            }
        }
        System.out.println("❌ No reservation found to cancel!");
    }

    void viewReservations() {
        System.out.println("\n--- Current Reservations ---");
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation res : reservations) {
                System.out.println(res);
            }
        }
    }

    double simulatePayment(double amount) {
        System.out.println("Processing payment of $" + amount + " ...");
        System.out.println("✅ Payment Successful!");
        return amount;
    }

    // File I/O: Save reservations
    void saveReservations() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Reservation res : reservations) {
                bw.write(res.guestName + "," + res.roomNumber + "," + res.amountPaid);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }

    // File I/O: Load reservations
    void loadReservations() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String guestName = data[0];
                int roomNumber = Integer.parseInt(data[1]);
                double amountPaid = Double.parseDouble(data[2]);
                reservations.add(new Reservation(guestName, roomNumber, amountPaid));
                for (Room r : rooms) {
                    if (r.roomNumber == roomNumber) r.isBooked = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading reservations: " + e.getMessage());
        }
    }
}

// --- Main Class ---
public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();

        while (true) {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Rooms");
            System.out.println("2. Make Reservation");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Reservations");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    hotel.viewRooms();
                    break;
                case 2:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter room number to book: ");
                    int roomNumber = sc.nextInt();
                    hotel.makeReservation(name, roomNumber);
                    break;
                case 3:
                    System.out.print("Enter your name: ");
                    String cname = sc.nextLine();
                    System.out.print("Enter room number to cancel: ");
                    int croom = sc.nextInt();
                    hotel.cancelReservation(cname, croom);
                    break;
                case 4:
                    hotel.viewReservations();
                    break;
                case 5:
                    System.out.println("🚪 Exiting... Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("❌ Invalid option!");
            }
        }
    }
}
