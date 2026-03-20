import java.io.*;
import java.util.*;

// Booking class (Serializable)
class Booking implements Serializable {
    int bookingId;
    String guestName;
    String roomType;

    Booking(int bookingId, String guestName, String roomType) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Wrapper class for persistence
class SystemState implements Serializable {
    Map<String, Integer> inventory;
    Map<Integer, Booking> bookings;

    SystemState(Map<String, Integer> inventory, Map<Integer, Booking> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Main Class
public class UseCase12DataPersistenceRecovery {

    static Map<String, Integer> inventory = new HashMap<>();
    static Map<Integer, Booking> bookings = new HashMap<>();
    static int bookingCounter = 1;

    static final String FILE_NAME = "system_state.dat";

    public static void main(String[] args) {

        // Step 1: Load previous state
        loadState();

        // Step 2: Simulate operations
        createBooking("Anuj", "Deluxe");
        createBooking("Rahul", "Standard");

        // Step 3: Save state before shutdown
        saveState();

        System.out.println("\n--- Restart Simulation ---\n");

        // Clear memory (simulate restart)
        inventory.clear();
        bookings.clear();

        // Step 4: Recover state
        loadState();

        // Step 5: Display recovered data
        displayState();
    }

    // Create booking
    static void createBooking(String name, String roomType) {

        if (!inventory.containsKey(roomType)) {
            System.out.println("Invalid room type!");
            return;
        }

        if (inventory.get(roomType) == 0) {
            System.out.println("No rooms available!");
            return;
        }

        Booking booking = new Booking(bookingCounter, name, roomType);
        bookings.put(bookingCounter, booking);

        inventory.put(roomType, inventory.get(roomType) - 1);

        System.out.println("Booking Confirmed: ID=" + bookingCounter);

        bookingCounter++;
    }

    // Save state (Serialization)
    static void saveState() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            SystemState state = new SystemState(inventory, bookings);
            oos.writeObject(state);

            System.out.println("System state saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state (Deserialization)
    static void loadState() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No previous data found. Initializing new system...");
            initializeInventory();
            return;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();

            inventory = state.inventory;
            bookings = state.bookings;

            // Update booking counter
            bookingCounter = bookings.size() + 1;

            System.out.println("System state restored successfully!");

        } catch (Exception e) {
            System.out.println("Error loading state. Starting fresh...");
            initializeInventory();
        }
    }

    // Initialize default inventory
    static void initializeInventory() {
        inventory.put("Deluxe", 2);
        inventory.put("Standard", 2);
    }

    // Display system state
    static void displayState() {
        System.out.println("Recovered Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }

        System.out.println("\nRecovered Bookings:");
        for (Booking b : bookings.values()) {
            System.out.println("ID: " + b.bookingId +
                    ", Name: " + b.guestName +
                    ", Room: " + b.roomType);
        }
    }
}