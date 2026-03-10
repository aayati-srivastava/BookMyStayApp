/**
 * Book My Stay App
 * Version 2.1
 *
 * Use Case 2: Basic Room Types & Static Availability
 * Demonstrates abstraction, inheritance and polymorphism.
 *
 * @author Aayati
 * @version 2.1
 */

public class BookMyStayApp {

    /**
     * Abstract Room class
     */
    static abstract class Room {

        String roomType;
        int beds;
        int size;
        double price;

        Room(String roomType, int beds, int size, double price) {
            this.roomType = roomType;
            this.beds = beds;
            this.size = size;
            this.price = price;
        }

        void displayRoomDetails() {
            System.out.println("Room Type: " + roomType);
            System.out.println("Beds: " + beds);
            System.out.println("Size: " + size + " sq ft");
            System.out.println("Price per night: $" + price);
        }
    }

    /**
     * Single Room
     */
    static class SingleRoom extends Room {

        SingleRoom() {
            super("Single Room", 1, 200, 80);
        }
    }

    /**
     * Double Room
     */
    static class DoubleRoom extends Room {

        DoubleRoom() {
            super("Double Room", 2, 350, 150);
        }
    }

    /**
     * Suite Room
     */
    static class SuiteRoom extends Room {

        SuiteRoom() {
            super("Suite Room", 3, 600, 300);
        }
    }

    /**
     * Application Entry Point
     */
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("      Hotel Booking System v2.1  ");
        System.out.println("=================================");

        // Creating room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("\n--- Room Details ---");

        single.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailable);

        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailable);

        System.out.println();

        suite.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailable);

        System.out.println("\nApplication terminated.");
    }
}