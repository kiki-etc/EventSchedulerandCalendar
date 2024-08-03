import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EventSummary {
    private Set<Event> events;

    /**
     * Constructor that takes an EventCollection
     * @param eventCollection
    */ 
    public EventSummary(EventCollection eventCollection) {
        this.events = eventCollection.getEvents();
        generateSummary();
    }

    /**
     * Constructor that takes an EventCollection and two dates
     * @param eventCollection
     * @param start
     * @param end
    */ 
    public EventSummary(EventCollection eventCollection, LocalDateTime start, LocalDateTime end) {
        this.events = eventCollection.getEvents().stream()
                .filter(event -> !event.getDateTime().isBefore(start) && !event.getDateTime().isAfter(end))
                .collect(Collectors.toSet());
        generateSummary();
    }

    /**
     * Generates a summary of the events
    */ 
    private void generateSummary() {
        System.out.println("Total number of events: " + events.size());

        Set<String> organizers = new HashSet<>();
        int highPriorityCount = 0;

        for (Event event : events) {
            organizers.add(event.getOrganization());
            if (event.isHighPriority()) {
                highPriorityCount++;
            }
        }

        System.out.println("Number of organizers: " + organizers.size());
        System.out.println("High priority events: " + highPriorityCount);
        System.out.println("List of organizers: " + organizers);
    }
}
