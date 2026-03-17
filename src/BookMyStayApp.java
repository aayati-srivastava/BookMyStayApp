import java.util.*;

/**
 * Book My Stay App
 *
 * Use Case 8: Booking History & Reporting
 * Version: 8.1
 *
 * Demonstrates storing confirmed bookings and generating reports.
 *
 * @author Aayati
 * @version 8.1
 */

public class BookMyStayApp {

    /* ---------- RESERVATION ---------- */

    static class Reservation {

        String reservationId;
        String guestName;
        String roomType;

        Reservation(String reservationId, String guestName, String roomType) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
        }

        void display() {
            System.out.println("Reservation ID: " + reservationId +
                    ", Guest: " + guestName +
                    ", Room: " + roomType);
        }
    }

    /* ---------- BOOKING HISTORY ---------- */

    static class BookingHistory {

        // List preserves insertion order
        private List<Reservation> history = new ArrayList<>();

        void addReservation(Reservation r) {
            history.add(r);
            System.out.println("Added to history: " + r.reservationId);
        }

        List<Reservation> getAllReservations() {
            return history;
        }
    }

    /* ---------- REPORT SERVICE ---------- */

    static class BookingReportService {

        void generateReport(List<Reservation> reservations) {

            System.out.println("\n--- Booking Report ---");

            if (reservations.isEmpty()) {
                System.out.println("No bookings found.");
                return;
            }

            for (Reservation r : reservations) {
                r.display();
            }

            System.out.println("\nTotal Bookings: " + reservations.size());
        }
    }

    /* ---------- APPLICATION ENTRY ---------- */

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("      Hotel Booking System v8.1  ");
        System.out.println("=================================");

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Example confirmed bookings (from Use Case 6)
        Reservation r1 = new Reservation("RES101", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES102", "Bob", "Double Room");
        Reservation r3 = new Reservation("RES103", "Charlie", "Suite Room");

        // Store bookings
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history.getAllReservations());

        System.out.println("\nBooking history remains unchanged after reporting.");
    }
}