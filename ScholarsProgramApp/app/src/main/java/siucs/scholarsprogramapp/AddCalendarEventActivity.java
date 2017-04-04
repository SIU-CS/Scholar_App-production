package siucs.scholarsprogramapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddCalendarEventActivity extends AppCompatActivity {

    public DatabaseReference mDatabaseEvents;
    public FirebaseAuth mAuth;

    public CalendarEvent event;

    public Button postButton;
    public TextView txtViewEvent;
    public EditText descView;

    public long dateSelected;
    public String dateToString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar_event);

        postButton = (Button) findViewById(R.id.buttonPostEvent);
        txtViewEvent = (TextView) findViewById(R.id.textEvent);
        descView = (EditText) findViewById(R.id.descView);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCalendarEvent();
            }
        });

        mAuth = FirebaseAuth.getInstance();




        Bundle b = getIntent().getExtras();

        if(b != null)
            dateSelected = b.getLong("dateSelected");
            dateToString = b.getString("dateToString");
            txtViewEvent.setText("Add event for " + dateToString);


        mDatabaseEvents = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/events");





    }

    private void postCalendarEvent() {

        String descEvent = descView.getText().toString();
        ArrayList<String> tempAL = new ArrayList<>();
        tempAL.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
        tempAL.trimToSize();

        event = new CalendarEvent(dateSelected, descEvent, tempAL);
        mDatabaseEvents.child(Long.toString(dateSelected)).setValue(event);

        finish();
        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));

    }
}
