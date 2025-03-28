package org.example;

import java.io.*;
import java.util.*;

import static org.example.FlightFinder.findRoutes;

public class Main {
    public static void main(String[] args) throws IOException {
        Map<String, List<FlightFinder.Flight>> flights = new HashMap<>();
        File file = new File("flights.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        while ((line = br.readLine()) != null) {
            String[] flightData = line.split(" ");
            String origin = flightData[0];
            String destination = flightData[1];
            int price = Integer.parseInt(flightData[2]);

            flights.putIfAbsent(origin, new ArrayList<>());
            flights.get(origin).add(new FlightFinder.Flight(destination, price));
        }
        br.close();

        // Input for origin and destination
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Alice's origin:");
        String origin = scanner.nextLine();

        System.out.println("Enter Alice's destination:");
        String destination = scanner.nextLine();

        // Find routes
        List<FlightFinder.Route> routes = findRoutes(flights, origin, destination);

        // Print the results
        if (routes.isEmpty()) {
            System.out.println("No routes");
        } else {
            for (FlightFinder.Route route : routes) {
                System.out.println(String.join(",", route.path) + "," + route.totalPrice);
            }
        }
    }
}