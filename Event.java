import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Event implements Comparable<Event> {
    private String title, venue, description;
    private final String eventID, organization;
    private LocalDateTime dateTime;
    private boolean highPriority;

    public Event(LocalDateTime dateTime, String title, String organization, String eventID) {
        this.dateTime = dateTime;
        this.title = title;
        this.organization = organization;
        this.venue = "TBD";
        this.highPriority = false;
        this.description = "No description";
        this.eventID = eventID; 
    }

    // accessor methods
    public String        getTitle()        {return title;}
    public LocalDateTime getDateTime()     {return dateTime;}
    public String        getVenue()        {return venue;}
    public String        getOrganization() {return organization;}
    public boolean       isHighPriority()  {return highPriority;}
    public String        getDescription()  {return description;}
    public String        id()              {return eventID;}

    // mutator methods
    public void setTitle(String title)              {this.title = title;}
    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}
    public void togglePriority()                    {highPriority = !highPriority;}
    public void setVenue(String venue)              {this.venue = venue;}
    public void setDescription(String description)  {this.description = description;}
    public void setPriority(boolean highPriority)   {this.highPriority = highPriority;}

    // return event details as a comprehensive String
    public void display() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = dateTime.format(formatter);
        
        String output = String.format(
                "Event Title: %s%n" +
                "Organizer: %s%n" +
                "Venue: %s%n" +
                "Date and Time: %s%n" +
              "\nDescription: %s%n" +
                "Priority: %s",
                title, organization, venue, formattedDateTime, description, highPriority ? "High" : "Normal"
        );
        
        System.out.println(output);
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
        return Objects.equals(eventID, event.eventID);
    }
    @Override   // for working with collections like HashMaps etc.
    public int hashCode() {
        return Objects.hash(eventID);
    }
}