import java.util.*;

// Reservation (from Use Case 5)
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

// Inventory Service (State Holder)
class InventoryService {
    private Map<String, Integer> availability;

    public InventoryService() {
        availability = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }

    // Update inventory after allocation
    public void reduceRoom(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

// Booking Service (Core Logic)
class BookingService {

    private Queue<Reservation> requestQueue;

    // Track allocated room IDs (global uniqueness)
    private Set<String> allocatedRoomIds;

    // Map room type → allocated room IDs
    private Map<String, Set<String>> roomAllocations;

    private InventoryService inventory;

    public BookingService(Queue<Reservation> queue, InventoryService inventory) {
        this.requestQueue = queue;
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
    }

    // Generate Unique Room ID
    private String generateRoomId(String type) {
        return type.substring(0, 1).toUpperCase() + UUID.randomUUID().toString().substring(0, 4);
    }

    // Process Booking Requests (FIFO)
    public void processBookings() {

        System.out.println("\nProcessing Booking Requests...\n");

        while (!requestQueue.isEmpty()) {

            Reservation r = requestQueue.poll(); // FIFO
            String type = r.getRoomType();

            // Check availability
            if (inventory.getAvailability(type) > 0) {

                String roomId;

                // Ensure uniqueness
                do {
                    roomId = generateRoomId(type);
                } while (allocatedRoomIds.contains(roomId));

                // Add to global set
                allocatedRoomIds.add(roomId);

                // Map to room type
                roomAllocations.putIfAbsent(type, new HashSet<>());
                roomAllocations.get(type).add(roomId);

                // Update inventory immediately (Atomic Step)
                inventory.reduceRoom(type);

                // Confirm booking
                System.out.println("Booking Confirmed for " + r.getGuestName());
                System.out.println("Room Type: " + type);
                System.out.println("Allocated Room ID: " + roomId);
                System.out.println("---------------------------");

            } else {
                System.out.println("Booking Failed for " + r.getGuestName() +
                        " (No " + type + " rooms available)");
                System.out.println("---------------------------");
            }
        }
    }
}

// Main Class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        // Step 1: Create Inventory
        InventoryService inventory = new InventoryService();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Double", 1);
        inventory.addRoom("Suite", 1);

        // Step 2: Create Booking Queue (FIFO)
        Queue<Reservation> queue = new LinkedList<>();

        queue.offer(new Reservation("Anuj", "Single"));
        queue.offer(new Reservation("Rahul", "Single"));
        queue.offer(new Reservation("Priya", "Single")); // Should fail (only 2 available)
        queue.offer(new Reservation("Amit", "Suite"));

        // Step 3: Booking Service
        BookingService service = new BookingService(queue, inventory);

        // Step 4: Process Bookings
        service.processBookings();
    }
}