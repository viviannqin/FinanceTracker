package model;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("Finance added");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Finance added", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testEquality() {
        // Create two events with the same description and date
        Event event1 = new Event("Event 1");
        Event event2 = new Event("Event 1");

        // Check if they are equal
        assertEquals(event1, event2);
        assertFalse(event1.equals(null));

        Event event3 = new Event("Event 3");
        assertFalse(event1.equals(event3));


    }

    @Test
    public void testInequality() {
        // Create two events with different descriptions
        Event event1 = new Event("Event 1");
        Event event2 = new Event("Event 2");

        // Check if they are not equal
        assertEquals(false, event1.equals(event2));

        Event event4 = new Event("Event 1") {
            @Override
            public boolean equals(Object obj) {
                return false;
            }
        };

        assertFalse(event1.equals(event4));
    }

    @Test
    public void testHashCodeConsistency() {
        // Create an event
        Event event = new Event("Test event");

        // Get the hash code twice
        int hashCode1 = event.hashCode();
        int hashCode2 = event.hashCode();

        // Check if the hash codes are consistent
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Finance added", e.toString());
    }
}
