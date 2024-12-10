import java.util.ArrayList;
import java.util.Scanner;

class Train {
    String trainNumber;
    String trainName;
    int totalSeats;
    int availableSeats;

    public Train(String trainNumber, String trainName, int totalSeats) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public boolean bookTicket() {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    public boolean cancelTicket() {
        if (availableSeats < totalSeats) {
            availableSeats++;
            return true;
        }
        return false;
    }

    public void displayTrainInfo() {
        System.out.println("Train Name: " + trainName);
        System.out.println("Train Number: " + trainNumber);
        System.out.println("Total Seats: " + totalSeats);
        System.out.println("Available Seats: " + availableSeats);
    }
}

class Ticket {
    String trainNumber;
    String passengerName;
    int seatNumber;

    public Ticket(String trainNumber, String passengerName, int seatNumber) {
        this.trainNumber = trainNumber;
        this.passengerName = passengerName;
        this.seatNumber = seatNumber;
    }

    public void displayTicketInfo() {
        System.out.println("Ticket Info:");
        System.out.println("Train Number: " + trainNumber);
        System.out.println("Passenger Name: " + passengerName);
        System.out.println("Seat Number: " + seatNumber);
    }
}

public class RailwayReservationSystem {
    static ArrayList<Train> trains = new ArrayList<>();
    static ArrayList<Ticket> tickets = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Adding some sample trains to the system
        trains.add(new Train("123A", "Express 1", 100));
        trains.add(new Train("456B", "Express 2", 150));

        while (true) {
            System.out.println("\nRailway Reservation System");
            System.out.println("1. Check Train Availability");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. View Booked Tickets");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    checkTrainAvailability();
                    break;
                case 2:
                    bookTicket();
                    break;
                case 3:
                    cancelTicket();
                    break;
                case 4:
                    viewBookedTickets();
                    break;
                case 5:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public static void checkTrainAvailability() {
        System.out.print("\nEnter Train Number: ");
        String trainNumber = scanner.nextLine();
        
        Train train = findTrain(trainNumber);
        if (train != null) {
            train.displayTrainInfo();
        } else {
            System.out.println("Train not found.");
        }
    }

    public static void bookTicket() {
        System.out.print("\nEnter Train Number: ");
        String trainNumber = scanner.nextLine();
        
        Train train = findTrain(trainNumber);
        if (train != null) {
            if (train.bookTicket()) {
                System.out.print("Enter Passenger Name: ");
                String name = scanner.nextLine();

                Ticket ticket = new Ticket(trainNumber, name, train.totalSeats - train.availableSeats + 1);
                tickets.add(ticket);
                System.out.println("Ticket booked successfully!");
                ticket.displayTicketInfo();
            } else {
                System.out.println("No seats available.");
            }
        } else {
            System.out.println("Train not found.");
        }
    }

    public static void cancelTicket() {
        System.out.print("\nEnter Ticket Train Number: ");
        String trainNumber = scanner.nextLine();

        System.out.print("Enter Passenger Name: ");
        String name = scanner.nextLine();

        Ticket ticketToCancel = null;
        for (Ticket ticket : tickets) {
            if (ticket.trainNumber.equals(trainNumber) && ticket.passengerName.equals(name)) {
                ticketToCancel = ticket;
                break;
            }
        }

        if (ticketToCancel != null) {
            Train train = findTrain(trainNumber);
            if (train != null && train.cancelTicket()) {
                tickets.remove(ticketToCancel);
                System.out.println("Ticket cancelled successfully.");
            }
        } else {
            System.out.println("Ticket not found.");
        }
    }

    public static void viewBookedTickets() {
        if (tickets.isEmpty()) {
            System.out.println("No tickets booked.");
        } else {
            for (Ticket ticket : tickets) {
                ticket.displayTicketInfo();
            }
        }
    }

    public static Train findTrain(String trainNumber) {
        for (Train train : trains) {
            if (train.trainNumber.equals(trainNumber)) {
                return train;
            }
        }
        return null;
    }
}
