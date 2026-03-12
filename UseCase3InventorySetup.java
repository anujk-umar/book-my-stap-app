/**
 * UseCase3InventorySetup
 * Demonstrates centralized room inventory management
 * using HashMap in the Book My Stay application.
 *
 * @author Anuj
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

/**
 * RoomInventory class manages all room availability
 * using a centralized HashMap.
 */
class RoomInventory {

    // HashMap to store room type and available count
    private HashMap<String, Integer> inventory;

    /**
     * Constructor initializes the room inventory
     */
    public RoomInventory() {

        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 2);
    }

    /**
     * Method to get availability of a room type
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Method to update room availability
     */
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /**
     * Display current inventory
     */
    public void displayInventory() {

        System.out.println("\nCurrent Room Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}

/**
 * Main class for Use Case 3
 */
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay - Version 3.1");
        System.out.println(" Centralized Room Inventory");
        System.out.println("=================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating inventory for Single Room...");

        inventory.updateAvailability("Single Room", 8);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication Terminated.");
    }
}