import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay App
 *
 * Use Case 5: Booking Request Queue (FIFO)
 * Version: 5.1
 *
 * Demonstrates fair booking request handling using Queue.
 *
 * @author Aayati
 * @version 5.1
 */

public class BookMyStayApp {

    /* ---------- RESERVATION OBJECT ---------- */

    static class Reservation {

        String guestName;
        String roomType;

        Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        void displayRequest() {
            System.out.println("Guest: " + guestName + " requested " + roomType);
        }
    }

    /* ---------- BOOKING REQUEST QUEUE ---------- */

    static class BookingRequestQueue {

        private Queue<Reservation> requestQueue;

        BookingRequestQueue() {
            requestQueue = new LinkedList<>();
        }

        void addRequest(Reservation reservation) {
            requestQueue.add(reservation);
            System.out.println("Booking request added to queue.");
        }

        void displayQueue() {

            System.out.println("\nCurrent Booking Request Queue:");

            for (Reservation r : requestQueue) {
                r.displayRequest();
            }
        }
    }

    /* ---------- APPLICATION ENTRY ---------- */

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("      Hotel Booking System v5.1  ");
        System.out.println("=================================");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add requests to queue (FIFO order)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue
        bookingQueue.displayQueue();

        System.out.println("\nRequests are stored in FIFO order.");
        System.out.println("No inventory updates occur at this stage.");
    }
}