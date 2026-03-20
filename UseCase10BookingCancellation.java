import java.util.*;

// Booking class
class Booking {
    int bookingId;
    String guestName;
    String roomType;
    String roomId;
    boolean isCancelled;

    Booking(int bookingId, String guestName, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }
}

// Main system class
public class UseCase10BookingCancellation {

    // Inventory: roomType -> available count
    static Map<String, Integer> inventory = new HashMap<>();

    // Available room IDs per type
    static Map<String, Queue<String>> availableRooms = new HashMap<>();

    // Booking records
    static Map<Integer, Booking> bookings = new HashMap<>();

    // Stack for rollback (LIFO)
    static Stack<String> rollbackStack = new Stack<>();

    static int bookingCounter = 1;

    public static void main(String[] args) {

        // Initialize inventory
        initializeInventory();

        // Create bookings
        createBooking("Anuj", "Deluxe");
        createBooking("Rahul", "Standard");

        // Cancel booking
        cancelBooking(1);

        // Try invalid cancellation
        cancelBooking(1);

        // Display final inventory
        displayInventory();
    }

    // Initialize rooms
    static void initializeInventory() {
        inventory.put("Deluxe", 2);
        inventory.put("Standard", 2);

        availableRooms.put("Deluxe", new LinkedList<>(Arrays.asList("D1", "D2")));
        availableRooms.put("Standard", new LinkedList<>(Arrays.asList("S1", "S2")));
    }

    // Booking creation
    static void createBooking(String guestName, String roomType) {

        if (!inventory.containsKey(roomType) || inventory.get(roomType) == 0) {
            System.out.println("No rooms available for " + roomType);
            return;
        }

        String roomId = availableRooms.get(roomType).poll();
        inventory.put(roomType, inventory.get(roomType) - 1);

        Booking booking = new Booking(bookingCounter, guestName, roomType, roomId);
        bookings.put(bookingCounter, booking);

        System.out.println("Booking Confirmed: ID=" + bookingCounter + ", Room=" + roomId);

        bookingCounter++;
    }

    // Cancellation with rollback
    static void cancelBooking(int bookingId) {

        System.out.println("\nProcessing cancellation for Booking ID: " + bookingId);

        // Validation
        if (!bookings.containsKey(bookingId)) {
            System.out.println("Invalid Booking ID!");
            return;
        }

        Booking booking = bookings.get(bookingId);

        if (booking.isCancelled) {
            System.out.println("Booking already cancelled!");
            return;
        }

        // Step 1: Push room ID to rollback stack
        rollbackStack.push(booking.roomId);

        // Step 2: Restore inventory
        inventory.put(booking.roomType, inventory.get(booking.roomType) + 1);

        // Step 3: Add room back to available pool
        availableRooms.get(booking.roomType).offer(booking.roomId);

        // Step 4: Update booking status
        booking.isCancelled = true;

        System.out.println("Cancellation successful. Room " + booking.roomId + " released.");
    }

    // Display inventory
    static void displayInventory() {
        System.out.println("\nFinal Inventory Status:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type) + " rooms available");
        }
    }
}