package siucs.scholarsprogramapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class CalendarEvent {

    public long time;
    public String desc;
    public ArrayList<String> attendees;

    public CalendarEvent() {
    }

    public CalendarEvent(long time, String desc, ArrayList<String> attendees) {
        this.time = time;
        this.desc = desc;
        this.attendees = attendees;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("time", time);
        result.put("desc", desc);
        result.put("attendees", attendees);

        return result;
    }
}