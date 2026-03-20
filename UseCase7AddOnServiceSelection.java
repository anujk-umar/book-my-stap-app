import java.util.*;

// Add-On Service Class
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println("Added " + service.getServiceName() +
                " to Reservation ID: " + reservationId);
    }

    // View services for a reservation
    public void viewServices(String reservationId) {

        System.out.println("\nServices for Reservation ID: " + reservationId);

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services added.");
            return;
        }

        for (AddOnService s : services) {
            System.out.println("- " + s.getServiceName() + " : ₹" + s.getPrice());
        }
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }
}

// Main Class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        // Step 1: Create Add-On Services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService spa = new AddOnService("Spa", 1500);
        AddOnService pickup = new AddOnService("Airport Pickup", 800);

        // Step 2: Assume existing reservation IDs (from Use Case 6)
        String res1 = "R1001";
        String res2 = "R1002";

        // Step 3: Create Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Step 4: Add services to reservations
        manager.addService(res1, breakfast);
        manager.addService(res1, spa);

        manager.addService(res2, pickup);

        // Step 5: View services
        manager.viewServices(res1);
        manager.viewServices(res2);

        // Step 6: Calculate total cost
        System.out.println("\nTotal Add-On Cost for " + res1 + " = ₹" +
                manager.calculateTotalCost(res1));

        System.out.println("Total Add-On Cost for " + res2 + " = ₹" +
                manager.calculateTotalCost(res2));

        // NOTE: No booking or inventory logic is modified
    }
}
