package siucs.scholarsprogramapp;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {

    public int classYear;
    public String email;
    public String faculty;
    public String firstName;
    public String lastName;
    public int serviceHours;
    public int numberOfPhotos;

    public User() {
    }

    public User(int classYear, String email, String faculty, String firstName, String lastName, int serviceHours, int numberOfPhotos) {
        this.classYear = classYear;
        this.email = email;
        this.faculty = faculty;
        this.firstName = firstName;
        this.lastName = lastName;
        this.serviceHours = serviceHours;
        this.numberOfPhotos = numberOfPhotos;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("classYear", classYear);
        result.put("email", email);
        result.put("faculty", faculty);
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("serviceHours", serviceHours);
        result.put("numberOfPhotos", numberOfPhotos);

        return result;
    }
}