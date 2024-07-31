import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Event implements Comparable<Event> {
    private LocalDateTime dateTime;
    private String title;
    //private String ;

    public Event(LocalDateTime dateTime, String title) {
        this.dateTime = dateTime;
        this.title = title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int compareTo(Event other) {
        return this.dateTime.compareTo(other.dateTime);
    }

    @Override
    public String toString() {
        return "Event{" +
                "dateTime=" + dateTime +
                ", name='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(dateTime, event.dateTime) &&
                Objects.equals(title, event.title);
    }

    @Override   // for working with collections like HashMaps etc.
    public int hashCode() {
        return Objects.hash(dateTime, title);
    }
}