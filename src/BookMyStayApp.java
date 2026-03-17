import java.util.*;

/**
 * Book My Stay App
 *
 * Use Case 7: Add-On Service Selection
 * Version: 7.1
 *
 * Demonstrates adding optional services to reservations
 * without modifying booking or inventory logic.
 *
 * @author Aayati
 * @version 7.1
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
    }

    /* ---------- ADD-ON SERVICE ---------- */

    static class AddOnService {
        String serviceName;
        double cost;

        AddOnService(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }
    }

    /* ---------- ADD-ON SERVICE MANAGER ---------- */

    static class AddOnServiceManager {

        // Map<ReservationID, List of Services>
        private HashMap<String, List<AddOnService>> serviceMap = new HashMap<>();

        // Add service to a reservation
        void addService(String reservationId, AddOnService service) {

            serviceMap
                    .computeIfAbsent(reservationId, k -> new ArrayList<>())
                    .add(service);

            System.out.println("Added service: " + service.serviceName + " to " + reservationId);
        }

        // Display services for a reservation
        void displayServices(String reservationId) {

            List<AddOnService> services = serviceMap.get(reservationId);

            if (services == null || services.isEmpty()) {
                System.out.println("No add-on services for " + reservationId);
                return;
            }

            System.out.println("\nServices for Reservation " + reservationId + ":");

            for (AddOnService s : services) {
                System.out.println("- " + s.serviceName + " ($" + s.cost + ")");
            }
        }

        // Calculate total add-on cost
        double calculateTotalCost(String reservationId) {

            List<AddOnService> services = serviceMap.get(reservationId);

            double total = 0;

            if (services != null) {
                for (AddOnService s : services) {
                    total += s.cost;
                }
            }

            return total;
        }
    }

    /* ---------- APPLICATION ENTRY ---------- */

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("      Hotel Booking System v7.1  ");
        System.out.println("=================================");

        // Example reservation (already confirmed in Use Case 6)
        Reservation r1 = new Reservation("RES101", "Alice", "Single Room");

        // Initialize Add-On Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects add-on services
        manager.addService(r1.reservationId, new AddOnService("Breakfast", 20));
        manager.addService(r1.reservationId, new AddOnService("Airport Pickup", 50));
        manager.addService(r1.reservationId, new AddOnService("Extra Bed", 30));

        // Display selected services
        manager.displayServices(r1.reservationId);

        // Calculate total add-on cost
        double totalCost = manager.calculateTotalCost(r1.reservationId);

        System.out.println("\nTotal Add-On Cost: $" + totalCost);

        System.out.println("\nCore booking and inventory remain unchanged.");
    }
}