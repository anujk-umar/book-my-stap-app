import java.util.*;

// Reservation Class (Confirmed Booking)
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Booking History (State Holder)
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Read-only access
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// Booking Report Service
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Display all bookings
    public void displayAllBookings() {

        System.out.println("\n--- Booking History ---\n");

        List<Reservation> list = history.getAllReservations();

        if (list.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Reservation r : list) {
            System.out.println("Reservation ID: " + r.getReservationId());
            System.out.println("Guest Name: " + r.getGuestName());
            System.out.println("Room Type: " + r.getRoomType());
            System.out.println("---------------------------");
        }
    }

    // Generate summary report
    public void generateSummaryReport() {

        System.out.println("\n--- Booking Summary Report ---\n");

        List<Reservation> list = history.getAllReservations();

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : list) {
            roomCount.put(r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1);
        }

        for (String type : roomCount.keySet()) {
            System.out.println("Room Type: " + type +
                    " | Total Bookings: " + roomCount.get(type));
        }
    }
}

// Main Class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        // Step 1: Booking History
        BookingHistory history = new BookingHistory();

        // Step 2: Add confirmed reservations (simulate UC6 output)
        history.addReservation(new Reservation("R1001", "Anuj", "Single"));
        history.addReservation(new Reservation("R1002", "Rahul", "Suite"));
        history.addReservation(new Reservation("R1003", "Priya", "Single"));
        history.addReservation(new Reservation("R1004", "Amit", "Double"));

        // Step 3: Reporting Service
        BookingReportService reportService = new BookingReportService(history);

        // Step 4: Admin views reports
        reportService.displayAllBookings();
        reportService.generateSummaryReport();
    }
}