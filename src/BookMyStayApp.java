import java.util.HashMap;

/**
 * Book My Stay App
 *
 * Use Case 4: Room Search & Availability Check
 * Version: 4.1
 *
 * Demonstrates read-only access to centralized inventory
 * and displays only available room types.
 *
 * @author Aayati
 * @version 4.1
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

            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 0); // Example unavailable room
        }

        int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }
    }

    /* ---------- SEARCH SERVICE (READ ONLY) ---------- */

    static class RoomSearchService {

        RoomInventory inventory;

        RoomSearchService(RoomInventory inventory) {
            this.inventory = inventory;
        }

        void searchAvailableRooms(Room[] rooms) {

            System.out.println("\n--- Available Rooms ---");

            for (Room room : rooms) {

                int available = inventory.getAvailability(room.roomType);

                if (available > 0) {

                    room.displayRoomDetails();
                    System.out.println("Available Rooms: " + available);
                    System.out.println();
                }
            }
        }
    }

    /* ---------- APPLICATION ENTRY ---------- */

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("      Hotel Booking System v4.1  ");
        System.out.println("=================================");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        Room[] rooms = { single, doubleRoom, suite };

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Guest searches available rooms
        searchService.searchAvailableRooms(rooms);

        System.out.println("Search completed. Inventory remains unchanged.");
    }
}