package siucs.scholarsprogramapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class MessageBoardSubPost {

    public String posterName;
    public String body;
    public long timeStamp;

    public MessageBoardSubPost() {
    }

    public MessageBoardSubPost(String posterName, String body, long timeStamp) {
        this.posterName = posterName;
        this.body = body;
        this.timeStamp = timeStamp;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("posterUID", posterName);
        result.put("body", body);
        result.put("timeStamp", timeStamp);

        return result;
    }
}
