package org.example;

import java.util.*;

class FlightFinder {
    static class Flight {
        String destination;
        int price;

        Flight(String destination, int price) {
            this.destination = destination;
            this.price = price;
        }
    }

    static class Route {
        List<String> path;
        int totalPrice;

        Route(List<String> path, int totalPrice) {
            this.path = new ArrayList<>(path);
            this.totalPrice = totalPrice;
        }
    }

    public static List<Route> findRoutes(Map<String, List<Flight>> flights, String origin, String destination) {
        Queue<Route> queue = new LinkedList<>();
        queue.offer(new Route(Arrays.asList(origin), 0));

        List<Route> validRoutes = new ArrayList<>();
        Map<List<String>, Integer> routePrices = new HashMap<>();

        while (!queue.isEmpty()) {
            Route current = queue.poll();
            String lastCity = current.path.get(current.path.size() - 1);

            if (lastCity.equals(destination)) {
                validRoutes.add(new Route(current.path, current.totalPrice));
                continue;
            }

            if (!flights.containsKey(lastCity)) continue;

            for (Flight flight : flights.get(lastCity)) {
                if (!current.path.contains(flight.destination)) {
                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add(flight.destination);
                    queue.offer(new Route(newPath, current.totalPrice + flight.price));
                }
            }
        }

        // Sort by total price
        validRoutes.sort(Comparator.comparingInt(route -> route.totalPrice));

        return validRoutes;
    }
}
