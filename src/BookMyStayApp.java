import java.io.*;
import java.util.*;

/**
 * Book My Stay App
 *
 * Use Case 12: Data Persistence & System Recovery
 * Version: 12.1
 *
 * Demonstrates saving and restoring system state using serialization.
 *
 * @author Aayati
 * @version 12.1
 */

public class BookMyStayApp {

    /* ---------- RESERVATION ---------- */

    static class Reservation implements Serializable {
        String reservationId;
        String guestName;
        String roomType;

        Reservation(String reservationId, String guestName, String roomType) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    /* ---------- SYSTEM STATE ---------- */

    static class SystemState implements Serializable {
        HashMap<String, Integer> inventory;
        List<Reservation> bookingHistory;

        SystemState(HashMap<String, Integer> inventory, List<Reservation> bookingHistory) {
            this.inventory = inventory;
            this.bookingHistory = bookingHistory;
        }
    }

    /* ---------- PERSISTENCE SERVICE ---------- */

    static class PersistenceService {

        private static final String FILE_NAME = "system_state.ser";

        // Save state to file
        void save(SystemState state) {

            try (ObjectOutputStream out =
                         new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

                out.writeObject(state);
                System.out.println("System state saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving system state.");
            }
        }

        // Load state from file
        SystemState load() {

            try (ObjectInputStream in =
                         new ObjectInputStream(new FileInputStream(FILE_NAME))) {

                SystemState state = (SystemState) in.readObject();
                System.out.println("System state restored successfully.");
                return state;

            } catch (FileNotFoundException e) {
                System.out.println("No previous data found. Starting fresh.");
            } catch (Exception e) {
                System.out.println("Error loading data. Starting with safe defaults.");
            }

            return null;
        }
    }

    /* ---------- APPLICATION ENTRY ---------- */

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("      Hotel Booking System v12.1 ");
        System.out.println("=================================");

        PersistenceService persistence = new PersistenceService();

        // Try loading previous state
        SystemState state = persistence.load();

        HashMap<String, Integer> inventory;
        List<Reservation> history;

        if (state == null) {
            // Initialize fresh data
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);

            history = new ArrayList<>();

            history.add(new Reservation("RES101", "Alice", "Single Room"));
            history.add(new Reservation("RES102", "Bob", "Double Room"));

        } else {
            inventory = state.inventory;
            history = state.bookingHistory;
        }

        // Display current state
        System.out.println("\nCurrent Inventory: " + inventory);

        System.out.println("\nBooking History:");
        for (Reservation r : history) {
            System.out.println(r.reservationId + " | " + r.guestName + " | " + r.roomType);
        }

        // Save state before shutdown
        SystemState newState = new SystemState(inventory, history);
        persistence.save(newState);

        System.out.println("\nSystem ready for safe shutdown and recovery.");
    }
}