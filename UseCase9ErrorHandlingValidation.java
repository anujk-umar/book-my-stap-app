import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation Class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> availability;

    public InventoryService() {
        availability = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public boolean isValidRoomType(String type) {
        return availability.containsKey(type);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void reduceRoom(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

// Validator Class (Fail-Fast)
class InvalidBookingValidator {

    public static void validate(Reservation r, InventoryService inventory)
            throws InvalidBookingException {

        // Validate guest name
        if (r.getGuestName() == null || r.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Validate room type
        if (!inventory.isValidRoomType(r.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
        }

        // Validate availability
        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + r.getRoomType());
        }
    }
}

// Booking Service
class BookingService {

    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void confirmBooking(Reservation r) {

        try {
            // Step 1: Validate (Fail-Fast)
            InvalidBookingValidator.validate(r, inventory);

            // Step 2: Proceed only if valid
            inventory.reduceRoom(r.getRoomType());

            System.out.println("Booking Confirmed for " + r.getGuestName() +
                    " (" + r.getRoomType() + ")");

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// Main Class
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        // Step 1: Setup Inventory
        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 1);
        inventory.addRoom("Double", 0);

        // Step 2: Booking Service
        BookingService service = new BookingService(inventory);

        // Step 3: Test Cases

        // Valid booking
        service.confirmBooking(new Reservation("Anuj", "Single"));

        // Invalid: No availability
        service.confirmBooking(new Reservation("Rahul", "Single"));

        // Invalid: Wrong room type
        service.confirmBooking(new Reservation("Priya", "Suite"));

        // Invalid: Empty name
        service.confirmBooking(new Reservation("", "Double"));
    }
}