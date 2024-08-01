import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    private static EventCollection eventCollection = new EventCollection();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nEnter command:");
            String input = scanner.nextLine();
            String[] command = input.split(" ", 3);

            switch (command[0].toLowerCase()) {
                case "create_event":
                    createEvent(command);
                    break;
                case "modify_event":
                    modifyEvent(command);
                    break;
                case "delete_event":
                    deleteEvent(command);
                    break;
                case "view_events":
                    viewEvents(command);
                    break;
                case "search_event":
                    searchEvent(command);
                    break;
                case "sort_events":
                    sortEvents(command);
                    break;
                case "generate_summary":
                    generateSummary(command);
                    break;
                case "exit":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Unknown command. Please try again.");
            }
        }
    }

    private static void createEvent(String[] command) {
        if (command.length != 5) {
            System.out.println("Usage: create_event <title> <date> <time> <location> <description>");
            return;
        }

        try {
            String title = command[1];
            LocalDateTime dateTime = LocalDateTime.parse(command[2] + "T" + command[3], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            String organization = "Unknown"; // Default or specify how to get this
            String eventID = generateEventID(); // Implement this method to generate a unique event ID
            Event event = new Event(dateTime, title, organization, eventID);
            event.setVenue(command[4]); // Use this for location
            event.setDescription(command[5]); // Use this for description

            if (eventCollection.add(event)) {
                System.out.println("Event created successfully.");
            } else {
                System.out.println("Event with this ID already exists.");
            }
        } catch (Exception e) {
            System.out.println("Error creating event: " + e.getMessage());
        }
    }

    private static void modifyEvent(String[] command) {
        if (command.length != 3) {
            System.out.println("Usage: modify_event <event_id> <attribute> <new_value>");
            return;
        }

        String eventID = command[1];
        String attribute = command[2];
        String newValue = command[3];

        boolean result = eventCollection.modifyEvent(eventID, attribute, newValue);
        if (result) {
            System.out.println("Event modified successfully.");
        } else {
            System.out.println("Failed to modify event. Check the event ID or attribute.");
        }
    }

    private static void deleteEvent(String[] command) {
        if (command.length != 2) {
            System.out.println("Usage: delete_event <event_id>");
            return;
        }

        String eventID = command[1];
        boolean result = eventCollection.remove(eventID);
        if (result) {
            System.out.println("Event deleted successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }

    private static void viewEvents(String[] command) {
        if (command.length != 2) {
            System.out.println("Usage: view_events <filter>");
            return;
        }

        String filter = command[1];
        switch (filter.toLowerCase()) {
            case "today":
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime endOfDay = now.toLocalDate().atTime(23, 59);
                eventCollection.view("datetime", now);
                eventCollection.view("datetime", endOfDay);
                break;
            case "week":
                // Implement week filter
                break;
            case "month":
                // Implement month filter
                break;
            default:
                System.out.println("Invalid filter. Use 'today', 'week', or 'month'.");
        }
    }

    private static void searchEvent(String[] command) {
        if (command.length != 3) {
            System.out.println("Usage: search_event <attribute> <value>");
            return;
        }

        String attribute = command[1];
        String value = command[2];

        String[] eventIDs = eventCollection.search(attribute, value);
        if (eventIDs.length > 0) {
            System.out.println("Found events:");
            for (String id : eventIDs) {
                Event event = eventCollection.getEventByID(id);
                if (event != null) {
                    event.display();
                }
            }
        } else {
            System.out.println("No events found.");
        }
    }

    private static void sortEvents(String[] command) {
        if (command.length != 2) {
            System.out.println("Usage: sort_events <attribute>");
            return;
        }

        String attribute = command[1];
        List<Event> sortedEvents = eventCollection.sort(attribute);
        System.out.println("Sorted events:");
        for (Event event : sortedEvents) {
            event.display();
        }
    }

    private static void generateSummary(String[] command) {
        if (command.length != 2) {
            System.out.println("Usage: generate_summary <date_range>");
            return;
        }

        String[] dateRange = command[1].split("to");
        if (dateRange.length != 2) {
            System.out.println("Invalid date range format. Use 'yyyy-MM-dd' or 'yyyy-MM-dd HH:mm'");
            return;
        }

        try {
            LocalDateTime start = LocalDateTime.parse(dateRange[0].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            LocalDateTime end = LocalDateTime.parse(dateRange[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            new EventSummary(eventCollection, start, end);
        } catch (Exception e) {
            System.out.println("Error generating summary: " + e.getMessage());
        }
    }

    // Helper method to generate a unique event ID
    private static String generateEventID() {
        // Implement a unique ID generation logic
        return "ID" + System.currentTimeMillis();
    }
}
