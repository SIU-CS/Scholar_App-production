package siucs.scholarsprogramapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceHourActivity extends AppCompatActivity {

    //create Firebase auth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    private DatabaseReference userRef;

    //create objects for the GUI fields and buttons
    private Button uploadServiceHourPhotoButton;
    private Button updateServiceHoursButton;
    private Button viewServiceHourButton;
    private EditText serviceHourUpdateTextbox;
    private TextView currentHoursOutOfTwentyText;


    private String userID;
    private String userHoursString;
    private int userHours;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_hour);

        //initialize firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //make sure user is logged in and if not return to login screen
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        //get a reference to users tree
        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/users");

        //link created objects with references to the GUI objects
        uploadServiceHourPhotoButton = (Button) findViewById(R.id.uploadServiceHourPhotoButton);
        updateServiceHoursButton = (Button) findViewById(R.id.updateServiceHoursButton);
        viewServiceHourButton = (Button) findViewById(R.id.viewServiceHourPhotoButton);
        serviceHourUpdateTextbox = (EditText) findViewById(R.id.serviceHourUpdateTextbox);
        currentHoursOutOfTwentyText = (TextView) findViewById(R.id.currentHoursOutOfTwentyText);

        //create firebase user object
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        userRef = dbRef.child(userID).getRef();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                userHours = currentUser.serviceHours;
                userHoursString = Integer.toString(userHours);
                currentHoursOutOfTwentyText.setText(userHoursString + " / 20");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //give the updateServiceHoursButton an OnClickListener
        updateServiceHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateServiceHours();
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User currentUser = dataSnapshot.getValue(User.class);
                        userHours = currentUser.serviceHours;
                        userHoursString = Integer.toString(userHours);
                        currentHoursOutOfTwentyText.setText(userHoursString + " / 20");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        //give the uploadServiceHourPhotoButton an OnClickListener
        uploadServiceHourPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call upload photo activity
                startActivity(new Intent(ServiceHourActivity.this, PhotoUploadActivity.class));
            }
        });

        viewServiceHourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call upload photo activity
                startActivity(new Intent(ServiceHourActivity.this, ViewPhotoActivity.class));
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    } //end onCreate method

    public void updateServiceHours() {
        //if textbox is empty
        if (serviceHourUpdateTextbox.getText().toString().equals(null) || serviceHourUpdateTextbox.getText().toString().equals("")) {
            Toast.makeText(this, "Oops, you didn't make any changes!", Toast.LENGTH_SHORT).show();
        } else {
            String hoursToAddString = serviceHourUpdateTextbox.getText().toString();
            int hoursToAdd = Integer.parseInt(hoursToAddString);

            int  newTotalHours = userHours + hoursToAdd;
            String newTotalHoursString = Integer.toString(newTotalHours);

            //if hours would bring total to a negative number
            if (newTotalHours < 0) {
                Toast.makeText(this, "Oops, you can't have less than zero hours!", Toast.LENGTH_SHORT).show();
            } else {
                dbRef.child(userID).child("serviceHours").setValue(newTotalHours);
            }
        }
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ServiceHour Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
