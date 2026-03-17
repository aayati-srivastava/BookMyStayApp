import java.util.*;

/**
 * Book My Stay App
 *
 * Use Case 9: Error Handling & Validation
 * Version: 9.1
 *
 * Demonstrates validation using custom exceptions.
 *
 * @author Aayati
 * @version 9.1
 */

public class BookMyStayApp {

    /* ---------- CUSTOM EXCEPTION ---------- */

    static class InvalidBookingException extends Exception {
        InvalidBookingException(String message) {
            super(message);
        }
    }

    /* ---------- VALIDATOR ---------- */

    static class BookingValidator {

        void validate(String roomType, Map<String, Integer> inventory)
                throws InvalidBookingException {

            // Check if room type exists
            if (!inventory.containsKey(roomType)) {
                throw new InvalidBookingException("Invalid room type selected.");
            }

            // Check availability
            if (inventory.get(roomType) <= 0) {
                throw new InvalidBookingException("No rooms available for selected type.");
            }
        }
    }

    /* ---------- APPLICATION ENTRY ---------- */

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay Application   ");
        System.out.println("      Hotel Booking System v9.1  ");
        System.out.println("=================================");

        // Inventory setup
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 0);

        BookingValidator validator = new BookingValidator();

        // Test cases (valid + invalid)
        String[] requests = {"Single Room", "Double Room", "Suite"};

        for (String roomType : requests) {

            System.out.println("\nRequesting: " + roomType);

            try {
                // Validate before allocation
                validator.validate(roomType, inventory);

                // If valid → allocate
                inventory.put(roomType, inventory.get(roomType) - 1);

                System.out.println("Booking successful for " + roomType);

            } catch (InvalidBookingException e) {

                // Graceful error handling
                System.out.println("Booking failed: " + e.getMessage());
            }
        }

        System.out.println("\nFinal Inventory: " + inventory);
        System.out.println("\nSystem continues running safely.");
    }
}