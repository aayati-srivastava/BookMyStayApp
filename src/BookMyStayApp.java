import java.util.HashMap;

/**
 * Book My Stay App
 *
 * Use Case 3: Centralized Room Inventory Management
 * Version: 3.1
 *
 * Demonstrates centralized inventory using HashMap.
 *
 * @author Aayati
 * @version 3.1
 */

public class BookMyStayApp {

    /* ---------- ROOM DOMAIN MODEL ---------- */

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

    static class SingleRoom extends Room {
        SingleRoom() {
            super("Single Room", 1, 200, 80);
        }
    }

    static class DoubleRoom extends Room {
        DoubleRoom() {
            super("Double Room", 2, 350, 150);
        }
    }

    static class SuiteRoom extends Room {
        SuiteRoom() {
            super("Suite Room", 3, 600, 300);
        }
    }

    /* ---------- INVENTORY MANAGEMENT ---------- */

    static class RoomInventory {

        private HashMap<String, Integer> inventory;

        RoomInventory() {

            inventory = new HashMap<>();

            // Initialize inventory
            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 2);
        }

        int getAvailability(String roomType) {
            return inventory.get(roomType);
        }

        void updateAvailability(String roomType, int newCount) {
            inventory.put(roomType, newCount);
        }

        void displayInventory() {

            System.out.println("\n--- Current Inventory ---");

            for (String roomType : inventory.keySet()) {
                System.out.println(roomType + " Available: " + inventory.get(roomType));
            }
        }
    }

    /* ---------- APPLICATION ENTRY ---------- */

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("      Hotel Booking System v3.1  ");
        System.out.println("=================================");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        System.out.println("\n--- Room Details ---");

        single.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Single Room"));

        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Double Room"));

        System.out.println();

        suite.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Suite Room"));

        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}