import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventCollection {
    private Set<Event> events;

    public EventCollection() {
        this.events = new HashSet<>();
    }

    // adds an event to the collection
    public boolean add(Event event) {
        return events.add(event);
    }

    // removes an event with the specified eventID
    public boolean remove(String eventID) {
        return events.removeIf(event -> event.id().equals(eventID));
    }

    // retrieve an event by its ID
    public Event getEventByID(String eventID) {
        for (Event event : events) {
            if (event.id().equals(eventID)) {
                return event;
            }
        }
        return null; // event not found
    }

    // modifies an attribute of the specified event
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
                        return false; // invalid attribute
                }
                return true; // modification successful
            }
        }
        return false; // event not found
    }

    // creates a list and returns the sorted list
    public List<Event> sort(String attribute) {
        List<Event> sortedEvents = new ArrayList<>(events);
        Comparator<Event> comparator;

        switch (attribute.toLowerCase()) {
            case "title":
                comparator = Comparator.comparing(Event::getTitle);
                break;
            case "datetime":
                comparator = Comparator.comparing(Event::getDateTime);
                break;
            case "venue":
                comparator = Comparator.comparing(Event::getVenue);
                break;
            case "description":
                comparator = Comparator.comparing(Event::getDescription);
                break;
            case "priority":
                comparator = Comparator.comparing(Event::isHighPriority);
                break;
            case "organization":
                comparator = Comparator.comparing(Event::getOrganization);
                break;
            default:
                throw new IllegalArgumentException("Invalid attribute: " + attribute);
        }

        sortedEvents.sort(comparator);
        return sortedEvents;
    }

    // searches for events based on a specified attribute and value, returns an array of their eventIDs
    public String[] search(String attribute, Object value) {
        return events.stream()
                .filter(event -> {
                    switch (attribute.toLowerCase()) {
                        case "title":
                            return event.getTitle().equals(value);
                        case "datetime":
                            return event.getDateTime().equals(value);
                        case "venue":
                            return event.getVenue().equals(value);
                        case "description":
                            return event.getDescription().equals(value);
                        case "priority":
                            return event.isHighPriority() == (boolean) value;
                        case "organization":
                            return event.getOrganization().equals(value);
                        default:
                            return false;
                    }
                })
                .map(Event::id)
                .toArray(String[]::new);
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
}