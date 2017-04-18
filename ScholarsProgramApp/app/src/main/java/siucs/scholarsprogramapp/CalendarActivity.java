package siucs.scholarsprogramapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.acl.AclEntry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CalendarActivity extends AppCompatActivity
{

    CalendarView calendarView;
    TextView dateDisplay;
    ListView listView;
    Button addButton;

    public DatabaseReference mDatabaseEvents;
    public FirebaseAuth mAuth;

    Calendar calendar;


    ArrayList<CalendarEvent> eventList = new ArrayList<>();
    ArrayList<CalendarEvent> dayEventList = new ArrayList<>();
    ArrayList<String> eventsOnSelectedDay = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        dateDisplay = (TextView) findViewById(R.id.dateDisplay);
        listView = (ListView) findViewById(R.id.listView);
        addButton = (Button) findViewById(R.id.buttonAddEvent);

        //initialize firebase auth object
        mAuth = FirebaseAuth.getInstance();

        //make sure user is logged in and if not return to login screen
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        //get a reference to users tree
        mDatabaseEvents = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/events");

        calendar = Calendar.getInstance(TimeZone.getTimeZone("CST"));
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        //create ArrayAdapter for the listView and connect it with userList
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventsOnSelectedDay);
        listView.setAdapter(arrayAdapter);

        mDatabaseEvents.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //save each child as a calendarevent with datasnapshot
                CalendarEvent tempEvent = dataSnapshot.getValue(CalendarEvent.class);

                //add event to eventlist
                eventList.add(tempEvent);
                eventList.trimToSize();

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

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                checkCalendar(arrayAdapter);
                //Log.i("TAG", Long.toString(eventList.get(0).time) + " " + Long.toString(calendar.getTimeInMillis())
                //               + " " + Long.toString((calendar.getTimeInMillis() + 86400000L)));

                //Toast.makeText(getApplicationContext(), " ", Toast.LENGTH_LONG).show();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCalendarEvent();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG).show();


                Intent intent = new Intent(CalendarActivity.this, RSVPActivity.class);
                Bundle b = new Bundle();
                b.putLong("eventTime", dayEventList.get(position).time);
                b.putInt("eventNumber", eventList.size());
                b.putString("eventDesc", dayEventList.get(position).desc);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
                finish();

                //Toast.makeText(getApplicationContext(), Long.toString(dayEventList.get(position).time), Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void checkCalendar(ArrayAdapter arrayAdapter) {
        eventsOnSelectedDay.clear();
        dayEventList.clear();
        arrayAdapter.notifyDataSetChanged();
        for (int i = 0; i < eventList.size(); i++) {
            //if ((eventList.get(i).time > calendar.getTimeInMillis()) && (eventList.get(i).time < (calendar.getTimeInMillis() + 86400000L))) {
            if ((eventList.get(i).time == calendar.getTimeInMillis())) {
                eventsOnSelectedDay.add(eventList.get(i).desc);
                dayEventList.add(eventList.get(i));
                arrayAdapter.notifyDataSetChanged();
                calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND) + 1);

            }
        }
    }


    private void addCalendarEvent() {

        Intent intent = new Intent(CalendarActivity.this, AddCalendarEventActivity.class);
        Bundle b = new Bundle();
        b.putLong("dateSelected", calendar.getTimeInMillis()); //Your id
        b.putString("dateToString", (Integer.toString(calendar.get(Calendar.MONTH) + 1) + "/" +
                                     Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + "/" +
                                     Integer.toString(calendar.get(Calendar.YEAR))));
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
        finish();

    }
}
