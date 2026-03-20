import java.util.*;

// Reservation Class (Represents booking intent)
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

// Booking Request Queue (FIFO Handling)
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request (enqueue)
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // View all requests (without removing)
    public void viewRequests() {
        System.out.println("\nCurrent Booking Requests (FIFO Order):\n");

        if (requestQueue.isEmpty()) {
            System.out.println("No booking requests available.");
            return;
        }

        for (Reservation r : requestQueue) {
            System.out.println("Guest: " + r.getGuestName() +
                               " | Room Type: " + r.getRoomType());
        }
    }
}

// Main Class
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        // Step 1: Create Booking Queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Step 2: Simulate Guest Booking Requests
        Reservation r1 = new Reservation("Anuj", "Single");
        Reservation r2 = new Reservation("Rahul", "Suite");
        Reservation r3 = new Reservation("Priya", "Double");

        // Step 3: Add requests to queue (FIFO)
        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);

        // Step 4: View queued requests
        queue.viewRequests();

        // NOTE: No inventory update or room allocation here
    }
}