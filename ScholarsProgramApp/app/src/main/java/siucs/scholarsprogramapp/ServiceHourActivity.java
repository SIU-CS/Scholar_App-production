package siucs.scholarsprogramapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.*;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServiceHourActivity extends AppCompatActivity {

    //create Firebase auth object
    private FirebaseAuth firebaseAuth;

    //create objects for the GUI fields and buttons
    private Button viewServiceHourPhotosButton;
    private Button uploadServiceHourPhotoButton;
    private Button updateServiceHoursButton;
    private EditText serviceHourUpdateTextbox;
    private TextView currentHoursOutOfTwentyText;

    private String userEmail;

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

        //create a reference to the firebase database
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = db.getReference("scholarapp-f2a76");

        //link created objects with references to the GUI objects
        viewServiceHourPhotosButton = (Button) findViewById(R.id.viewServiceHourPhotosButton);
        uploadServiceHourPhotoButton = (Button) findViewById(R.id.uploadServiceHourPhotoButton);
        updateServiceHoursButton = (Button) findViewById(R.id.updateServiceHoursButton);
        serviceHourUpdateTextbox = (EditText) findViewById(R.id.serviceHourUpdateTextbox);
        currentHoursOutOfTwentyText = (TextView) findViewById(R.id.currentHoursOutOfTwentyText);

        //create firebase user object
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userEmail = user.getEmail();

        //give the updateServiceHoursButton an OnClickListener
        updateServiceHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateServiceHours();
            }
        });

        //give the viewServiceHourPhotosButton an OnClickListener
        viewServiceHourPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call view photos activity
            }
        });

        //give the uploadServiceHourPhotoButton an OnClickListener
        uploadServiceHourPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call upload photo activity


            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    } //end onCreate method

    public void updateServiceHours() {
        //get current service hours before update


        //if textbox is empty
        if (serviceHourUpdateTextbox.getText().toString().equals(null) || serviceHourUpdateTextbox.getText().toString().equals("")) {
            Toast.makeText(this, "Oops, you didn't make any changes!", Toast.LENGTH_SHORT).show();
        } else {
            String hoursToAddString = serviceHourUpdateTextbox.getText().toString();
            double hoursToAdd = Double.parseDouble(hoursToAddString);
            Toast.makeText(this, hoursToAddString, Toast.LENGTH_SHORT).show();

            //if hours would bring total to a negative number
            if (hoursToAdd > 20 || hoursToAdd < -20) {
                Toast.makeText(this, "Oops, you can't have less than zero hours!", Toast.LENGTH_SHORT).show();
                //if adding negative hours... subtracting hours
            } else if (hoursToAdd < 0) {
                Toast.makeText(this, "Subtracting hours", Toast.LENGTH_SHORT).show();
                //normal adding of hours
            } else {
                Toast.makeText(this, "Adding hours", Toast.LENGTH_SHORT).show();
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
