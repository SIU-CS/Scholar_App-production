package siucs.scholarsprogramapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RSVPActivity extends AppCompatActivity {

    private boolean isAlreadyGoing = false;

    private TextView textRSVP;
    private Button buttonRSVP;

    private DatabaseReference mDatabaseEvents;
    private FirebaseAuth mAuth;

    private ArrayList<CalendarEvent> eventList = new ArrayList<>();

    private CalendarEvent event;

    private Long eventTime;
    private int eventNumber;
    private String eventDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsvp);

        buttonRSVP = (Button) findViewById(R.id.buttonRSVP);
        textRSVP = (TextView) findViewById(R.id.textRSVP);

        mAuth = FirebaseAuth.getInstance();

        Bundle b = getIntent().getExtras();

        if(b != null){
            eventTime = b.getLong("eventTime");
            eventDesc = b.getString("eventDesc");
            eventNumber = b.getInt("eventNumber");
        }

        mDatabaseEvents = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/events");

        mDatabaseEvents.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //save each child as a calendarevent with datasnapshot
                CalendarEvent tempEvent = dataSnapshot.getValue(CalendarEvent.class);

                //add event to eventlist
                eventList.add(tempEvent);
                eventList.trimToSize();

                if(eventList.size() == eventNumber){
                    LoadDetails();
                }

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        buttonRSVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RSVP();
            }
        });



    }

    private void RSVP() {
        if(isAlreadyGoing){
            ArrayList<String> tempAttendees = new ArrayList<>(event.attendees);
            event.attendees.clear();
            for(int i = 0; i < tempAttendees.size(); i++){
                if(tempAttendees.get(i) != mAuth.getCurrentUser().getUid()) {
                    event.attendees.add(tempAttendees.get(i));
                }
            }
            mDatabaseEvents.child(Long.toString(event.time)).removeValue();
            mDatabaseEvents.child(Long.toString(event.time)).setValue(event);
        }
        else {
            event.attendees.add(mAuth.getCurrentUser().getUid());
            mDatabaseEvents.child(Long.toString(event.time)).removeValue();
            mDatabaseEvents.child(Long.toString(event.time)).setValue(event);
        }
        finish();
        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
    }
    private void LoadDetails() {

        //Toast.makeText(getApplicationContext(), Integer.toString(eventList.size()), Toast.LENGTH_SHORT).show();
        for(int i = 0; i < eventList.size(); i++){
            CalendarEvent tempEvent = eventList.get(i);
            //Toast.makeText(getApplicationContext(), Long.toString(tempEvent.time) + "\n"
            //      + Long.toString(eventTime), Toast.LENGTH_SHORT).show();
            if(tempEvent.time == eventTime){
                event = tempEvent;
                i = eventList.size();
            }
        }
        event.attendees.trimToSize();
        if(event != null) {
            textRSVP.setText("Would you like to RSVP for \"" + event.desc +
                    "\"?\nCurrent attendees: " + event.attendees.size());
        }
        else{
            textRSVP.setText("Error retrieving event");
            buttonRSVP.setEnabled(false);
        }
        for(int i = 0; i < event.attendees.size(); i++){
            if(event.attendees.get(i) == mAuth.getCurrentUser().getUid()){
                i = event.attendees.size();
                buttonRSVP.setText("Cancel RSVP");
                isAlreadyGoing = true;
            }
        }

    }
}
