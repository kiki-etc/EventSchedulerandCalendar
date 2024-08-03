import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * The main class for the Event Management Application. 
 * Provides a command-line interface to manage events, including creating, modifying,
 * deleting, viewing, searching, sorting, and generating summaries of events.
 */
public class Main {
    private static EventCollection eventCollection = new EventCollection();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nEnter command:");
            String input = scanner.nextLine();
            String[] command = input.split(" ", 2);
            
            command[1] = transformInput(command[1]);

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
                case "view_all_events":
                    viewAllEvents();
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

    private static String transformInput(String input) {
        // Replace all occurrences of double quotes (") with empty strings
        String noDoubleQuotes = input.replace("\" \"", ",");
    
        // Replace all occurrences of spaces (" ") with commas (,)
        String result = noDoubleQuotes.replace("\"", "");
    
        return result;
    }

    /**
     * Creates a new event based on user input.
     * @param args
     */
    private static void createEvent(String args) {
        // Use a regular expression to split the input string by quotes, taking care of spaces and quotes
        String[] parts = args.split(",");
        
        // Check if the parts length is correct (should be 6 elements due to splitting by quotes)
        if (parts.length != 6) {
            System.out.println("Usage: create_event \"<title>\" \"<date>\" \"<time>\" \"<location>\" \"<organization>\" \"<description>\"");
            return;
        }
    
        try {
            String title = parts[0].trim();
            String dateString = parts[1].trim();
            String timeString = parts[2].trim();
            LocalDateTime dateTime = LocalDateTime.parse(dateString + "T" + timeString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            String location = parts[3].trim();
            String organization = parts[4].trim();
            String description = parts[5].trim();
            String eventID = generateEventID();
            Event event = new Event(dateTime, title, organization, eventID);
            event.setVenue(location);
            event.setDescription(description);
    
            if (eventCollection.add(event)) {
                System.out.println("Event created successfully.");
            } else {
                System.out.println("Event with this ID already exists.");
            }
    
            System.out.println("Current number of events: " + eventCollection.getEvents().size()); // Verify event count
        } catch (Exception e) {
            System.out.println("Error creating event: " + e.getMessage());
        }
    }

    /**
     * Modifies the event based on user input.
     * @param args
     */
    private static void modifyEvent(String args) {
        String[] parts = args.split(",", 3);
        if (parts.length != 3) {
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
     * Deletes an event based on user input.
     * @param args
     */
    private static void deleteEvent(String args) {
        String[] parts = args.split(" ");
        if (parts.length != 1) {
            System.out.println("Usage: delete_event <event_id>");
            return;
        }

        String eventID = parts[0];
        boolean result = eventCollection.remove(eventID);
        if (result) {
            System.out.println("Event deleted successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }

    /**
     * View events based on a specific filter.
     * @param args
     */
    private static void viewEvents(String args) {
        String[] parts = args.split(" ");
        if (parts.length != 1) {
            System.out.println("Usage: view_events <filter>");
            return;
        }

        String filter = parts[0];
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
     * View all events in the collection.
     */
    private static void viewAllEvents() {
        if (eventCollection.getEvents().isEmpty()) {
            System.out.println("No events found.");
        } else {
            for (Event event : eventCollection.getEvents()) {
                event.display();
            }
        }
    }    

    /**
     * Search for events based on a specified attribute and value.
     * @param args
     */
    private static void searchEvent(String args) {
        String[] parts = args.split(" ", 2);
        if (parts.length != 2) {
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
     * Sort events based on a specific attribute and displays the sorted list.
     * @param args
     */
    private static void sortEvents(String args) {
        String[] parts = args.split(" ");
        if (parts.length != 1) {
            System.out.println("Usage: sort_events <attribute>");
            return;
        }

        String attribute = parts[0];
        List<Event> sortedEvents = eventCollection.sort(attribute);
        System.out.println("Sorted events:");
        for (Event event : sortedEvents) {
            event.display();
        }
    }

    /**
     * Generates a summary based on a specific attribute.
     * @param args
     */
    private static void generateSummary(String args) {
        String[] dateRange = args.split("to");
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
     * Helper method to generate a unique event ID.
     * @return unique EventID
    */ 
    private static String generateEventID() {
        return "ID" + System.currentTimeMillis();
    }
}
