import java.util.*;

/**
 * Book My Stay App
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Version: 6.1
 *
 * Demonstrates safe room allocation using Queue, HashMap, and Set
 * to prevent double booking.
 *
 * @author Aayati
 * @version 6.1
 */

public class BookMyStayApp {

    /* ---------- RESERVATION ---------- */

    static class Reservation {

        String guestName;
        String roomType;

        Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    /* ---------- BOOKING REQUEST QUEUE ---------- */

    static class BookingRequestQueue {

        Queue<Reservation> queue = new LinkedList<>();

        void addRequest(Reservation r) {
            queue.add(r);
            System.out.println("Request added: " + r.guestName + " -> " + r.roomType);
        }

        Reservation getNextRequest() {
            return queue.poll(); // FIFO
        }

        boolean hasRequests() {
            return !queue.isEmpty();
        }
    }

    /* ---------- INVENTORY SERVICE ---------- */

    static class InventoryService {

        HashMap<String, Integer> inventory = new HashMap<>();

        InventoryService() {
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 2);
            inventory.put("Suite Room", 1);
        }

        boolean isAvailable(String roomType) {
            return inventory.getOrDefault(roomType, 0) > 0;
        }

        void decrementRoom(String roomType) {
            inventory.put(roomType, inventory.get(roomType) - 1);
        }

        void displayInventory() {
            System.out.println("\nCurrent Inventory: " + inventory);
        }
    }

    /* ---------- BOOKING SERVICE ---------- */

    static class BookingService {

        Set<String> allocatedRoomIds = new HashSet<>();

        HashMap<String, Set<String>> allocatedRoomsByType = new HashMap<>();

        InventoryService inventory;

        int roomCounter = 1;

        BookingService(InventoryService inventory) {
            this.inventory = inventory;
        }

        void processRequest(Reservation reservation) {

            System.out.println("\nProcessing request for: " + reservation.guestName);

            if (!inventory.isAvailable(reservation.roomType)) {
                System.out.println("No rooms available for " + reservation.roomType);
                return;
            }

            // Generate unique room ID
            String roomId = reservation.roomType.replace(" ", "").substring(0,3).toUpperCase() + roomCounter++;

            // Ensure uniqueness using Set
            if (allocatedRoomIds.contains(roomId)) {
                System.out.println("Duplicate room ID detected.");
                return;
            }

            allocatedRoomIds.add(roomId);

            // Map room type to allocated room IDs
            allocatedRoomsByType
                    .computeIfAbsent(reservation.roomType, k -> new HashSet<>())
                    .add(roomId);

            // Update inventory
            inventory.decrementRoom(reservation.roomType);

            System.out.println("Reservation Confirmed!");
            System.out.println("Guest: " + reservation.guestName);
            System.out.println("Room Type: " + reservation.roomType);
            System.out.println("Allocated Room ID: " + roomId);
        }
    }

    /* ---------- APPLICATION ENTRY ---------- */

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("      Hotel Booking System v6.1  ");
        System.out.println("=================================");

        BookingRequestQueue requestQueue = new BookingRequestQueue();
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        // Guest booking requests
        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Double Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        requestQueue.addRequest(new Reservation("David", "Single Room"));

        // Process requests in FIFO order
        while (requestQueue.hasRequests()) {

            Reservation r = requestQueue.getNextRequest();

            bookingService.processRequest(r);
        }

        inventory.displayInventory();

        System.out.println("\nAll booking requests processed.");
    }
}