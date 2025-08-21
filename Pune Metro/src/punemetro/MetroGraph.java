package punemetro;

import java.util.*;

/**
 * Represents the metro system as a weighted graph.
 */
public class MetroGraph {

    private final Map<String, Map<String, Integer>> graph;

    public MetroGraph() {
        graph = new HashMap<>();
    }

    // Add connection between two stations
    public void addEdge(String src, String dest, int distance) {
        graph.putIfAbsent(src, new HashMap<>());
        graph.putIfAbsent(dest, new HashMap<>());

        graph.get(src).put(dest, distance);
        graph.get(dest).put(src, distance);
    }

    public Set<String> getStations() {
        return graph.keySet();
    }

    // Dijkstra using custom Heap
    public int dijkstra(String start, String end, List<String> path) {
        if (!graph.containsKey(start) || !graph.containsKey(end)) {
            throw new IllegalArgumentException("Start or end station does not exist.");
        }

        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        Heap<StationPair> pq = new Heap<>();
        Map<String, StationPair> stationToPair = new HashMap<>();

        // Initialize distances
        for (String station : graph.keySet()) {
            dist.put(station, Integer.MAX_VALUE);
        }

        dist.put(start, 0);
        StationPair startPair = new StationPair(start, 0);
        pq.add(startPair);
        stationToPair.put(start, startPair);

        while (!pq.isEmpty()) {
            StationPair current = pq.remove();
            String currentStation = current.station;

            if (currentStation.equals(end)) break;

            for (String neighbor : graph.get(currentStation).keySet()) {
                int newDist = dist.get(currentStation) + graph.get(currentStation).get(neighbor);

                if (newDist < dist.get(neighbor)) {
                    dist.put(neighbor, newDist);
                    parent.put(neighbor, currentStation);

                    StationPair neighborPair = stationToPair.get(neighbor);

                    if (neighborPair == null) {
                        neighborPair = new StationPair(neighbor, newDist);
                        pq.add(neighborPair);
                        stationToPair.put(neighbor, neighborPair);
                    } else {
                        neighborPair.distance = newDist;
                        pq.updatePriority(neighborPair);
                    }
                }
            }
        }

        // If no path found
        if (dist.get(end) == Integer.MAX_VALUE) {
            path.clear();
            return -1;
        }

        // Rebuild path
        path.clear();
        String crawl = end;
        while (crawl != null) {
            path.add(crawl);
            crawl = parent.get(crawl);
        }
        Collections.reverse(path);

        return dist.get(end);
    }

    // Fare calculation
    public static int calculateFare(int distance) {
        if (distance <= 2) return 10;
        else if (distance <= 5) return 20;
        else if (distance <= 12) return 30;
        else if (distance <= 21) return 40;
        else if (distance <= 32) return 50;
        else return 60;
    }

    // Helper class
    static class StationPair implements Comparable<StationPair> {
        String station;
        int distance;

        StationPair(String station, int distance) {
            this.station = station;
            this.distance = distance;
        }

        @Override
        public int compareTo(StationPair other) {
            return Integer.compare(this.distance, other.distance);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            StationPair other = (StationPair) obj;
            return station.equals(other.station);
        }

        @Override
        public int hashCode() {
            return station.hashCode();
        }

        @Override
        public String toString() {
            return station + "(" + distance + ")";
        }
    }
}
