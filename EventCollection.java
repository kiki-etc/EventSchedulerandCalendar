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

    public EventCollection() {
        this.events = new HashSet<>();
    }

    /**
     * Adding an event to the collection
     * @param event
     * @return whether or not adding event was successful
     */
    public boolean add(Event event) {
        return events.add(event);
    }

    /**
     * removes an event with the specified eventID
     * 
     * @param eventID the ID number of the event, as a String
     * @return whether or not the event was successfully removed
     */
    public boolean remove(String eventID) {
        return events.removeIf(event -> event.id().equals(eventID));
    }

    /**
     * retrieve an event by its ID
     * @param eventID the ID number of the event, as a String
     * @return the event with the specified eventID
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
     * @param eventID the ID number of the event, as a String
     * @param attribute the attribute of the event to modify, e.g. 'title'
     * @param newValue the value to update the specified attribute to
     * 
     * @return whether or not the modification was successful
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
     * @param attribute the attribute of the event to sort by, e.g. 'title'
     * @return a list of the events, sorted by the specified attribute
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
     * @param attribute the attribute of the events we're looking, e.g. 'title'
     * @param value the value of the specified attribute, e.g. all events with the title 'Anniversary'
     * @return an array of strings with all the eventIDs of the events that match the search criteria
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
     * @param attribute the attribute of the events to filter by, e.g. 'title'
     * @param value the value that the attribute should be equal to
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
     * @return the set of events
    */ 
    public Set<Event> getEvents() {
        return events;
    }
}