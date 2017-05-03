package siucs.scholarsprogramapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class MessageBoardPost {

    public String posterName;
    public String shortDesc;
    public String body;
    public long timeStamp;
    public ArrayList<MessageBoardSubPost> subPosts = new ArrayList<>();

    public MessageBoardPost() {
    }

    public MessageBoardPost(String posterName, String shortDesc, String body, long timeStamp, ArrayList<MessageBoardSubPost> subPosts) {
        this.posterName = posterName;
        this.shortDesc = shortDesc;
        this.body = body;
        this.timeStamp = timeStamp;
        this.subPosts = subPosts;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("posterName", posterName);
        result.put("shortDesc", shortDesc);
        result.put("body", body);
        result.put("timeStamp", timeStamp);
        result.put("subPosts", subPosts);

        return result;
    }
}


