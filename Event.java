import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * This class represents an event with details such as title, venue, description, organization
 * date, time and priority status. The methods include accessor methods, mutator methods, a ToString
 * method and comparing events, displaying event details and checking equality.
 */

public class Event implements Comparable<Event> {
    private String title, venue, description;
    private final String eventID, organization;
    private LocalDateTime dateTime;
    private boolean highPriority;

    /**
     * Constructs an Event with the specific date and time, title, organization and eventID
     * @param dateTime: the date and time of the event -> LocalDateTime
     * @param title:the title of the event -> String
     * @param organization: the organization hosting the event -> String
     * @param eventID: the unique ID for the event -> String
     */

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
    /** 
     * The title of the event
     * @return title
    */
    public String getTitle() {return title;}

    /**
     * the date and time of the event
     * @return date and time
     */
    public LocalDateTime getDateTime() {return dateTime;}

    /**
     * the venue of the event
     * @return venue
     */
    public String getVenue() {return venue;}

    /**
     * the organization planning the event
     * @return organizaiton
     */
    public String getOrganization() {return organization;}

    /**
     * the priority status of the event
     * @return true if the event is high priority, false otherwise
     */
    public boolean isHighPriority() {return highPriority;}

    /**
     * the description of the event
     * @return description
     */
    public String getDescription() {return description;}

    /**
     * the ID of the event
     * @return eventID
     */
    public String id() {return eventID;}

    // mutator methods

    /**
     * setting the title of the event
     * @param title
     */
    public void setTitle(String title) {this.title = title;}

    /**
     * setting the date and time of the event
     * @param dateTime
     */
    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}

    /**
     * toggling  the  priority status of the event
     * @param
     */
    public void togglePriority() {highPriority = !highPriority;}

    /**
     * setting the venue of the event
     * @param venue
     */
    public void setVenue(String venue) {this.venue = venue;}

    /**
     * setting the description of the event
     * @param description
     */
    public void setDescription(String description) {this.description = description;}

    /**
     * setting the priority of the event
     * @param highPriority
     */
    public void setPriority(boolean highPriority) {this.highPriority = highPriority;}

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
                "Priority: %s" +
                "\nEventID: %s%n",
                title, organization, venue, formattedDateTime, description, highPriority ? "High" : "Normal", eventID
        );
        
        System.out.println(output);
    }

    // overridden methods
    /**
     * compares an event with another based on their date and time
     * 
     * @param other: the other event being compared
     * @return int
     */
    @Override
    public int compareTo(Event other) {
        return this.dateTime.compareTo(other.dateTime);
    }

    /**A string representation of the event
     * 
     * @return a string representation of the event
     */
    @Override
    public String toString() {
        return "Event{" +
                "date & time=" + dateTime +
                ", name='" + title + '\'' +
                '}';
    }

    /**
     * Checks whether another object is equal to tis one
     * @param o: the reference object being compared
     * @return true if this object is the same as the argument or false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventID, event.eventID) ||
               (Objects.equals(title, event.title) &&
                Objects.equals(venue, event.venue) &&
                Objects.equals(organization, event.organization));
    }

    /**
     * Showing a hash code value for the object
     * @return integer hashcode
     */
    @Override   // for working with collections like HashMaps etc.
    public int hashCode() {
        return Objects.hash(eventID);
    }
}