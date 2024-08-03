import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;

/**
 * The main class for the Event Management Application. 
 * Provide a command-line interface to manage events, including creating, modifying,
 * deleting, viewing, searching, sorting and generating summaries of events.
 */
public class Main {
    private static EventCollection eventCollection = new EventCollection();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nEnter command:");
            String input = scanner.nextLine();
            String[] command = input.split(" ", 2);

            switch (command[0].toLowerCase()) {
                case "create_event":
                    createEvent(command[1]);
                    break;
                case "modify_event":
                    modifyEvent(command[1]);
                    break;
                case "delete_event":
                    deleteEvent(command[1]);
                    break;
                case "view_events":
                    viewEvents(command[1]);
                    break;
                case "search_event":
                    searchEvent(command[1]);
                    break;
                case "sort_events":
                    sortEvents(command[1]);
                    break;
                case "generate_summary":
                    generateSummary(command[1]);
                    break;
                case "exit":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Unknown command. Please try again.");
            }
        }
    }

    /**
     * Creates a new event based on user input
     * @param command
     */
    private static void createEvent(String[] command) {
        if (command.length != 5) {
            System.out.println("Usage: create_event <title> <date> <time> <location> <description>");
            return;
        }

        try {
            String title = parts[0];
            LocalDateTime dateTime = LocalDateTime.parse(parts[1] + "T" + parts[2], DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            String location = parts[3];
            String organization = parts[4];
            String description = parts[5];
            String eventID = generateEventID();
            Event event = new Event(dateTime, title, organization, eventID);
            event.setVenue(location);
            event.setDescription(description);

            if (eventCollection.add(event)) {
                System.out.println("Event created successfully.");
            } else {
                System.out.println("Event with this ID already exists.");
            }
        } catch (Exception e) {
            System.out.println("Error creating event: " + e.getMessage());
        }
    }

    /**
     * Modifies the event based on user input
     * @param command
     */
    private static void modifyEvent(String[] command) {
        if (command.length != 3) {
            System.out.println("Usage: modify_event <event_id> <attribute> <new_value>");
            return;
        }

        String eventID = parts[0];
        String attribute = parts[1];
        String newValue = parts[2];

        try {
            Object value;
            switch (attribute.toLowerCase()) {
                case "datetime":
                    value = LocalDateTime.parse(newValue, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                    break;
                case "priority":
                    value = Boolean.parseBoolean(newValue);
                    break;
                default:
                    value = newValue;
                    break;
            }

            boolean result = eventCollection.modifyEvent(eventID, attribute, value);
            if (result) {
                System.out.println("Event modified successfully.");
            } else {
                System.out.println("Failed to modify event. Check the event ID or attribute.");
            }
        } catch (Exception e) {
            System.out.println("Error modifying event: " + e.getMessage());
        }
    }

    /**
     * Deletes an event based on user input
     * @param command
     */
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

    /**
     * View events based on a specific filter
     * @param command
     */
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

    /**
     * Search for events based on a specified attribute and value
     * @param command
     */
    private static void searchEvent(String[] command) {
        if (command.length != 3) {
            System.out.println("Usage: search_event <attribute> <value>");
            return;
        }

        String attribute = parts[0];
        String value = parts[1];

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

    /**
     * Sort events based on a specific attribute and displays the sorted list
     * @param command
     */
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

    /**
     * Generates a summary based on a specific attribute
     * @param command
     */
    private static void generateSummary(String[] command) {
        if (command.length != 2) {
            System.out.println("Usage: generate_summary <date_range>");
            return;
        }

        String[] dateRange = command[1].split("to");
        if (dateRange.length != 2) {
            System.out.println("Invalid date range format. Use 'yyyy-MM-dd'T'HH:mm' to 'yyyy-MM-dd'T'HH:mm'");
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

    /**
     * Helper method to generate a unique event ID
     * @return unique EventID
    */ 
    private static String generateEventID() {
        return "ID" + System.currentTimeMillis();
    }
}
