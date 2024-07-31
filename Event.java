import java.time.LocalDateTime;
import java.util.Objects;

public class Event implements Comparable<Event> {
    private String title, venue, organization;
    private LocalDateTime dateTime;
    private boolean highPriority;

    public Event(LocalDateTime dateTime, String title) {
        this.dateTime = dateTime;
        this.title = title;
    }

    // accessor methods
    public String getTitle() {
        return title;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public String getVenue() {
        return venue;
    }
    public String getOrganization() {
        return organization;
    }
    public boolean isHighPriority() {
        return highPriority;
    }

    // mutator methods
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public void togglePriority() {
        highPriority = !highPriority;
    }
    public void setVenue(String venue) {
        this.venue = venue;
    }

    // overridden methods
    @Override
    public int compareTo(Event other) {
        return this.dateTime.compareTo(other.dateTime);
    }
    @Override
    public String toString() {
        return "Event{" +
                "date & time=" + dateTime +
                ", name='" + title + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(dateTime, event.dateTime) &&
               title.equals(event.title)                &&
               organization.equals(event.organization);
    }
    @Override   // for working with collections like HashMaps etc.
    public int hashCode() {
        return Objects.hash(dateTime, title);
    }
}