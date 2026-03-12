/**
 * UseCase2RoomInitialization
 * This program demonstrates basic room types and static availability
 * in the Book My Stay application.
 *
 * Version: 2.1 (Refactored version)
 * 
 * @author Anuj
 * @version 2.1
 */

// Abstract class representing a general Room
abstract class Room {

    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    // Constructor
    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    // Method to display room details
    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq ft");
        System.out.println("Price     : ₹" + price);
    }
}


// Single Room class
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 1500);
    }
}


// Double Room class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 2500);
    }
}


// Suite Room class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 600, 5000);
    }
}


// Main application class
public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        System.out.println("===================================");
        System.out.println("     Book My Stay - Version 2.1");
        System.out.println("===================================");

        // Creating room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleroom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 10;
        int doubleAvailable = 5;
        int suiteAvailable = 2;

        System.out.println("\n--- Single Room Details ---");
        single.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailable);

        System.out.println("\n--- Double Room Details ---");
        doubleroom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailable);

        System.out.println("\n--- Suite Room Details ---");
        suite.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailable);

        System.out.println("\nApplication Terminated.");
    }
}