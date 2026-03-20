import java.util.*;

// Booking Request class
class BookingRequest {
    String guestName;
    String roomType;

    BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking System
class BookingSystem {

    // Shared inventory
    private Map<String, Integer> inventory = new HashMap<>();

    // Shared queue
    private Queue<BookingRequest> requestQueue = new LinkedList<>();

    public BookingSystem() {
        inventory.put("Deluxe", 2);
        inventory.put("Standard", 2);
    }

    // Add request to queue
    public synchronized void addRequest(BookingRequest request) {
        requestQueue.offer(request);
        System.out.println("Request added: " + request.guestName + " -> " + request.roomType);
    }

    // Process booking (CRITICAL SECTION)
    public synchronized void processBooking() {

        if (requestQueue.isEmpty()) {
            return;
        }

        BookingRequest request = requestQueue.poll();

        if (request == null) return;

        String roomType = request.roomType;

        // Critical section (protected)
        if (inventory.get(roomType) > 0) {
            System.out.println(Thread.currentThread().getName() +
                    " allocated " + roomType + " to " + request.guestName);

            inventory.put(roomType, inventory.get(roomType) - 1);
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " FAILED for " + request.guestName + " (No rooms)");
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// Worker Thread
class BookingProcessor extends Thread {

    BookingSystem system;

    BookingProcessor(BookingSystem system, String name) {
        super(name);
        this.system = system;
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            system.processBooking();

            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Main Class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        // Simulate concurrent requests
        system.addRequest(new BookingRequest("Anuj", "Deluxe"));
        system.addRequest(new BookingRequest("Rahul", "Deluxe"));
        system.addRequest(new BookingRequest("Amit", "Deluxe")); // extra
        system.addRequest(new BookingRequest("Neha", "Standard"));
        system.addRequest(new BookingRequest("Priya", "Standard"));

        // Multiple threads (concurrent users)
        BookingProcessor t1 = new BookingProcessor(system, "Thread-1");
        BookingProcessor t2 = new BookingProcessor(system, "Thread-2");

        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final inventory
        system.displayInventory();
    }
}