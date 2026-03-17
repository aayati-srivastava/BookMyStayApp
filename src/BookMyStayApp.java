import java.util.*;

/**
 * Book My Stay App
 *
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 * Version: 11.1
 *
 * Demonstrates thread-safe booking using synchronized methods.
 *
 * @author Aayati
 * @version 11.1
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

    /* ---------- SHARED BOOKING QUEUE ---------- */

    static class BookingQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        // synchronized → thread-safe add
        synchronized void addRequest(Reservation r) {
            queue.add(r);
            System.out.println(Thread.currentThread().getName() +
                    " added request: " + r.guestName);
        }

        // synchronized → thread-safe retrieval
        synchronized Reservation getRequest() {
            return queue.poll();
        }

        synchronized boolean hasRequests() {
            return !queue.isEmpty();
        }
    }

    /* ---------- INVENTORY SERVICE ---------- */

    static class InventoryService {

        private HashMap<String, Integer> inventory = new HashMap<>();

        InventoryService() {
            inventory.put("Single Room", 2);
        }

        // synchronized critical section
        synchronized boolean allocateRoom(String roomType) {

            int available = inventory.getOrDefault(roomType, 0);

            if (available > 0) {
                inventory.put(roomType, available - 1);
                return true;
            }

            return false;
        }

        synchronized void displayInventory() {
            System.out.println("Final Inventory: " + inventory);
        }
    }

    /* ---------- BOOKING PROCESSOR (THREAD) ---------- */

    static class BookingProcessor extends Thread {

        BookingQueue queue;
        InventoryService inventory;

        BookingProcessor(BookingQueue queue, InventoryService inventory, String name) {
            super(name);
            this.queue = queue;
            this.inventory = inventory;
        }

        public void run() {

            while (true) {

                Reservation r;

                // Critical section for queue access
                synchronized (queue) {
                    if (!queue.hasRequests()) break;
                    r = queue.getRequest();
                }

                if (r != null) {

                    // Critical section for allocation
                    synchronized (inventory) {

                        if (inventory.allocateRoom(r.roomType)) {
                            System.out.println(getName() +
                                    " SUCCESS: Room allocated to " + r.guestName);
                        } else {
                            System.out.println(getName() +
                                    " FAILED: No room available for " + r.guestName);
                        }
                    }
                }
            }
        }
    }

    /* ---------- APPLICATION ENTRY ---------- */

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("      Hotel Booking System v11.1 ");
        System.out.println("=================================");

        BookingQueue queue = new BookingQueue();
        InventoryService inventory = new InventoryService();

        // Simulate multiple guests (more requests than rooms)
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Single Room"));

        // Multiple threads processing simultaneously
        Thread t1 = new BookingProcessor(queue, inventory, "Thread-1");
        Thread t2 = new BookingProcessor(queue, inventory, "Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();

        System.out.println("\nAll bookings processed safely without race conditions.");
    }
}