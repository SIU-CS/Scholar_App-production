package siucs.scholarsprogramapp;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;


/**
 * Created by court on 5/3/2017.
 */

public class CourtneyUnitTests {
    CalendarEvent event;
    ArrayList<String> arrayList;

    @Before
    public void setUp() {
        event = new CalendarEvent(((long)1345679135), "test event", arrayList);
    }

    @Test
    public void checkTime() throws Exception {
        assertEquals(((long)1345679135), event.time);
    }

    @Test
    public void checkDescription() throws Exception {
        assertEquals("test event", event.desc);
    }

    @Test
    public void checkAttendees() throws Exception {
        assertEquals(arrayList, event.attendees);
    }


}