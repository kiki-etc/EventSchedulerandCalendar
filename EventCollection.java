import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

public class EventCollection {
    private Set<Event> events;

    public EventCollection() {
        this.events = new HashSet<>();
    }

    // Adds an event to the collection
    public boolean add(Event event) {
        return events.add(event);
    }

    // Removes an event with the specified eventID
    public boolean remove(String eventID) {
        return events.removeIf(event -> event.id().equals(eventID));
    }

    // Modifies an attribute of the specified event
    public boolean modifyEvent(String eventID, String attribute, Object newValue) {
        for (Event event : events) {
            if (event.id().equals(eventID)) {
                switch (attribute.toLowerCase()) {
                    case "title":
                        event.setTitle((String) newValue);
                        break;
                    case "datetime":
                        event.setDateTime((LocalDateTime) newValue);
                        break;
                    case "venue":
                        event.setVenue((String) newValue);
                        break;
                    case "description":
                        event.setDescription((String) newValue);
                        break;
                    case "priority":
                        event.setPriority((Boolean) newValue);
                        break;
                    default:
                        return false; // Invalid attribute
                }
                return true; // Modification successful
            }
        }
        return false; // Event not found
    }

    // Searches for events based on a specified attribute and value, returns an array of their eventIDs
    public String[] search(String attribute, Object value) {
        List<String> matchingEventIDs = new ArrayList<>();
        for (Event event : events) {
            switch (attribute.toLowerCase()) {
                case "title":
                    if (event.getTitle().equals(value)) {
                        matchingEventIDs.add(event.id());
                    }
                    break;
                case "datetime":
                    if (event.getDateTime().equals(value)) {
                        matchingEventIDs.add(event.id());
                    }
                    break;
                case "venue":
                    if (event.getVenue().equals(value)) {
                        matchingEventIDs.add(event.id());
                    }
                    break;
                case "description":
                    if (event.getDescription().equals(value)) {
                        matchingEventIDs.add(event.id());
                    }
                    break;
                case "organization":
                    if (event.getOrganization().equals(value)) {
                        matchingEventIDs.add(event.id());
                    }
                    break;
                case "priority":
                    if (event.isHighPriority() == (Boolean) value) {
                        matchingEventIDs.add(event.id());
                    }
                    break;
                default:
                    break; // Invalid attribute
            }
        }
        return matchingEventIDs.toArray(new String[0]);
    }

    // Displays events based on a specified filter
    public void view(String attribute, Object value) {
        for (Event event : events) {
            switch (attribute.toLowerCase()) {
                case "title":
                    if (event.getTitle().equals(value)) {
                        event.display();
                    }
                    break;
                case "datetime":
                    if (event.getDateTime().equals(value)) {
                        event.display();
                    }
                    break;
                case "venue":
                    if (event.getVenue().equals(value)) {
                        event.display();
                    }
                    break;
                case "description":
                    if (event.getDescription().equals(value)) {
                        event.display();
                    }
                    break;
                case "organization":
                    if (event.getOrganization().equals(value)) {
                        event.display();
                    }
                    break;
                case "priority":
                    if (event.isHighPriority() == (Boolean) value) {
                        event.display();
                    }
                    break;
                default:
                    break; // Invalid attribute
            }
        }
    }

    // For testing purposes
    public static void main(String[] args) {
        EventCollection eventCollection = new EventCollection();

        Event event1 = new Event(LocalDateTime.of(2023, 7, 1, 14, 0), "Annual Meeting", "ABC Corp", "1");
        Event event2 = new Event(LocalDateTime.of(2023, 8, 1, 9, 0), "Project Kickoff", "XYZ Inc", "2");
        Event event3 = new Event(LocalDateTime.of(2023, 9, 1, 10, 0), "Team Building", "XYZ Inc", "3");

        eventCollection.add(event1);
        eventCollection.add(event2);
        eventCollection.add(event3);

        // Test display
        eventCollection.view("organization", "XYZ Inc");

        // Test modifyEvent
        eventCollection.modifyEvent("2", "venue", "Main Conference Room");
        eventCollection.view("venue", "Main Conference Room");

        // Test search
        String[] eventIDs = eventCollection.search("title", "Annual Meeting");
        for (String id : eventIDs) {
            System.out.println("Found event with ID: " + id);
        }

        // Test remove
        eventCollection.remove("1");
        eventCollection.view("title", "Annual Meeting");
    }
}
