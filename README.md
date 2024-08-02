# Event Management CLI Instructions

**Welcome to the Event Management CLI!** This application allows you to create, modify, delete, view, search, sort, and summarize events. Below is a guide to using each command.

---

#### **1. Create a New Event**
**Command:**
```
create_event <title> <date> <time> <location> <description>
```
**Description:**
Creates a new event with the specified details.

**Example:**
```
create_event "Meeting" "2024-08-01" "14:30" "Conference Room" "Annual team meeting"
```
- **`<title>`**: The event's title.
- **`<date>`**: The event's date in `yyyy-MM-dd` format.
- **`<time>`**: The event's time in `HH:mm` format.
- **`<location>`**: The event's location.
- **`<description>`**: A brief description of the event.

---

#### **2. Modify an Existing Event**
**Command:**
```
modify_event <event_id> <attribute> <new_value>
```
**Description:**
Modifies an attribute of an existing event with the new value.

**Example:**
```
modify_event ID123 title "Updated Meeting"
```
- **`<event_id>`**: The unique ID of the event you want to modify.
- **`<attribute>`**: The attribute to change (e.g., `title`, `datetime`, `venue`, `description`, `priority`).
- **`<new_value>`**: The new value for the specified attribute.

---

#### **3. Delete an Event**
**Command:**
```
delete_event <event_id>
```
**Description:**
Deletes the event with the specified event ID.

**Example:**
```
delete_event ID123
```
- **`<event_id>`**: The unique ID of the event you want to delete.

---

#### **4. View Events**
**Command:**
```
view_events <filter>
```
**Description:**
Views events based on the specified filter.

**Example:**
```
view_events today
```
- **`<filter>`**: Filter to apply (e.g., `today`, `week`, `month`).

---

#### **5. Search for Events**
**Command:**
```
search_event <attribute> <value>
```
**Description:**
Searches for events based on a specified attribute and value.

**Example:**
```
search_event title "Annual Meeting"
```
- **`<attribute>`**: The attribute to search by (e.g., `title`, `datetime`, `venue`, `description`, `priority`, `organization`).
- **`<value>`**: The value to search for.

---

#### **6. Sort Events**
**Command:**
```
sort_events <attribute>
```
**Description:**
Sorts events by the specified attribute.

**Example:**
```
sort_events datetime
```
- **`<attribute>`**: The attribute to sort by (e.g., `title`, `datetime`, `venue`, `description`, `priority`).

---

#### **7. Generate Summary**
**Command:**
```
generate_summary <date_range>
```
**Description:**
Generates a summary of events within a specified date range.

**Example:**
```
generate_summary "2024-08-01T00:00 to 2024-08-31T23:59"
```
- **`<date_range>`**: Date range for the summary in `yyyy-MM-ddTHH:mm to yyyy-MM-ddTHH:mm` format.

---

#### **8. Exit the Application**
**Command:**
```
exit
```
**Description:**
Exits the CLI application.

---

**Note:** Make sure to follow the command format exactly to avoid errors. For commands requiring date and time, use the specified formats to ensure proper parsing.

---

If you encounter any issues, ensure you’ve followed the command format correctly and try again.