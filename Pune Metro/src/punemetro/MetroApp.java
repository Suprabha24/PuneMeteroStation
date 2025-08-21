package punemetro;

import java.util.*;

public class MetroApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MetroGraph metro = new MetroGraph();

        // 🚇 Preload Pune Metro Routes
        preloadStations(metro);

        while (true) {
            System.out.println("\n====== Pune Metro System ======");
            System.out.println("1. Show All Stations");
            System.out.println("2. Find Shortest Path & Fare");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nStations in Pune Metro:");
                    for (String station : metro.getStations()) {
                        System.out.println("- " + station);
                    }
                    break;

                case 2:
                    System.out.print("Enter source station: ");
                    String src = sc.nextLine();

                    System.out.print("Enter destination station: ");
                    String dest = sc.nextLine();

                    if (!metro.getStations().contains(src) || !metro.getStations().contains(dest)) {
                        System.out.println("❌ Invalid station name. Please try again.");
                        break;
                    }

                    List<String> path = new ArrayList<>();
                    int distance = metro.dijkstra(src, dest, path);

                    if (distance == Integer.MAX_VALUE) {
                        System.out.println("⚠️ No path found between " + src + " and " + dest);
                    } else {
                        int fare = MetroGraph.calculateFare(distance);
                        System.out.println("\n✅ Shortest Path: " + path);
                        System.out.println("🚉 Total Distance: " + distance + " km");
                        System.out.println("💰 Fare: ₹" + fare);
                    }
                    break;

                case 3:
                    System.out.println("👋 Exiting Pune Metro App. Thank you!");
                    return;

                default:
                    System.out.println("❌ Invalid choice! Please select again.");
            }
        }
    }

    // ✅ Preload Purple Line & Aqua Line routes
    private static void preloadStations(MetroGraph metro) {
        // Purple Line (PCMC – Swargate)
        metro.addEdge("PCMC", "Sant Tukaram Nagar", 2);
        metro.addEdge("Sant Tukaram Nagar", "Nashik Phata", 1);
        metro.addEdge("Nashik Phata", "Kasarwadi", 2);
        metro.addEdge("Kasarwadi", "Bopodi", 2);
        metro.addEdge("Bopodi", "Khadki", 2);
        metro.addEdge("Khadki", "Range Hills", 2);
        metro.addEdge("Range Hills", "Shivajinagar", 2);
        metro.addEdge("Shivajinagar", "Civil Court", 1);
        metro.addEdge("Civil Court", "Budhwar Peth", 2);
        metro.addEdge("Budhwar Peth", "Mandai", 1);
        metro.addEdge("Mandai", "Swargate", 1);

        // Aqua Line (Vanaz – Ramwadi)
        metro.addEdge("Vanaz", "Anand Nagar", 1);
        metro.addEdge("Anand Nagar", "Ideal Colony", 1);
        metro.addEdge("Ideal Colony", "Nal Stop", 1);
        metro.addEdge("Nal Stop", "Garware College", 1);
        metro.addEdge("Garware College", "Deccan Gymkhana", 1);
        metro.addEdge("Deccan Gymkhana", "Sambhaji Park", 1);
        metro.addEdge("Sambhaji Park", "Civil Court", 2); // interchange with Purple Line
        metro.addEdge("Civil Court", "Mangalwar Peth", 1);
        metro.addEdge("Mangalwar Peth", "Pune Railway Station", 1);
        metro.addEdge("Pune Railway Station", "Ruby Hall Clinic", 1);
        metro.addEdge("Ruby Hall Clinic", "Bund Garden", 1);
        metro.addEdge("Bund Garden", "Yerawada", 2);
        metro.addEdge("Yerawada", "Kalyani Nagar", 1);
        metro.addEdge("Kalyani Nagar", "Ramwadi", 1);
    }
}
