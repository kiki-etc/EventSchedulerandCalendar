import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is a collection class for managing a set of events. 
 * It has methods for adding an event, removing an event, searching for an event,
 * and viewing events based on various attributes.
 */
public class EventCollection {
    private Set<Event> events;


    /**
     * A constructor for an empty set of events
     */
    public EventCollection() {
        this.events = new HashSet<>();
    }

    /**
     * Adding an event to the collection
     * @param event
     * @return adding event
     */
    public boolean add(Event event) {
        return events.add(event);
    }

    /**
     * removes an event with the specified eventID
     * 
     * @param eventID
     * @return
     */
    public boolean remove(String eventID) {
        return events.removeIf(event -> event.id().equals(eventID));
    }

    /**
     * retrieve an event by its ID
     * @param eventID
     * @return
    */ 
    public Event getEventByID(String eventID) {
        for (Event event : events) {
            if (event.id().equals(eventID)) {
                return event;
            }
        }
        return null; // event not found
    }

    /** 
     * modifies an attribute of the specified event
     * @param eventID
     * @param attribute
     * @param newValue
     * 
     * @return true
     */
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

    /**
     * creates a list and returns the sorted list
     * @param attribute
     * @return
    */ 
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

    /**
     * searches for events based on a specified attribute and value, returns an array of their eventIDs
     * @param attribute
     * @param value
     * @return
    */ 
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

    /**
     * Displays events based on a specified filter
     * @param attribute
     * @param value
    */ 
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

    /**
     * returns the set of events
     * @return
    */ 
    public Set<Event> getEvents() {
        return events;
    }
}