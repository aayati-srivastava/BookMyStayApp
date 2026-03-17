import java.util.*;

/**
 * Book My Stay App
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * Version: 10.1
 *
 * Demonstrates safe cancellation using Stack (LIFO)
 * and restoring inventory state.
 *
 * @author Aayati
 * @version 10.1
 */

public class BookMyStayApp {

    /* ---------- RESERVATION ---------- */

    static class Reservation {
        String reservationId;
        String guestName;
        String roomType;
        String roomId;

        Reservation(String reservationId, String guestName, String roomType, String roomId) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
            this.roomId = roomId;
        }
    }

    /* ---------- INVENTORY ---------- */

    static class InventoryService {

        HashMap<String, Integer> inventory = new HashMap<>();

        InventoryService() {
            inventory.put("Single Room", 1);
            inventory.put("Double Room", 1);
        }

        void incrementRoom(String roomType) {
            inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
        }

        void displayInventory() {
            System.out.println("Inventory: " + inventory);
        }
    }

    /* ---------- BOOKING HISTORY ---------- */

    static class BookingHistory {

        HashMap<String, Reservation> bookings = new HashMap<>();

        void add(Reservation r) {
            bookings.put(r.reservationId, r);
        }

        Reservation get(String reservationId) {
            return bookings.get(reservationId);
        }

        void remove(String reservationId) {
            bookings.remove(reservationId);
        }
    }

    /* ---------- CANCELLATION SERVICE ---------- */

    static class CancellationService {

        Stack<String> rollbackStack = new Stack<>();

        InventoryService inventory;
        BookingHistory history;

        CancellationService(InventoryService inventory, BookingHistory history) {
            this.inventory = inventory;
            this.history = history;
        }

        void cancelReservation(String reservationId) {

            System.out.println("\nProcessing cancellation for: " + reservationId);

            Reservation r = history.get(reservationId);

            // Validation
            if (r == null) {
                System.out.println("Invalid reservation. Cannot cancel.");
                return;
            }

            // Push room ID to stack (LIFO rollback)
            rollbackStack.push(r.roomId);

            // Restore inventory
            inventory.incrementRoom(r.roomType);

            // Remove booking from history
            history.remove(reservationId);

            System.out.println("Cancellation successful.");
            System.out.println("Released Room ID: " + r.roomId);
        }

        void showRollbackStack() {
            System.out.println("Rollback Stack: " + rollbackStack);
        }
    }

    /* ---------- APPLICATION ENTRY ---------- */

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("      Hotel Booking System v10.1 ");
        System.out.println("=================================");

        InventoryService inventory = new InventoryService();
        BookingHistory history = new BookingHistory();

        // Sample confirmed bookings (from previous use cases)
        Reservation r1 = new Reservation("RES101", "Alice", "Single Room", "SIN1");
        Reservation r2 = new Reservation("RES102", "Bob", "Double Room", "DOU2");

        history.add(r1);
        history.add(r2);

        CancellationService cancellationService = new CancellationService(inventory, history);

        // Cancel a booking
        cancellationService.cancelReservation("RES101");

        // Try invalid cancellation
        cancellationService.cancelReservation("RES999");

        // Display results
        inventory.displayInventory();
        cancellationService.showRollbackStack();

        System.out.println("\nSystem state restored successfully.");
    }
}