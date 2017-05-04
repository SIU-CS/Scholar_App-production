package siucs.scholarsprogramapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    //create firebase auth object
    private FirebaseAuth firebaseAuth;

    //create database reference objects
    private DatabaseReference dbRef;
    private DatabaseReference userRef;

    //create objects for GUI components
    private TextView textViewWelcome;
    private Button buttonLogOut;
    private Button buttonContactList;
    private Button buttonMessageBoard;
    private Button buttonCalendar;
    private Button buttonServiceHours;

    //create other variables
    private String userID;
    private String userFirstName;
    private String userLastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initialize firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if no user logged in and take user back to login screen
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        //create firebase user object
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        //get a reference to users tree
        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/users");
        userRef = dbRef.child(userID).getRef();

        //link objects with references to GUI components
        textViewWelcome = (TextView) findViewById(R.id.textViewWelcome);
        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);
        buttonContactList = (Button) findViewById(R.id.buttonContactList);
        buttonMessageBoard = (Button) findViewById(R.id.buttonMessageBoard);
        buttonCalendar = (Button) findViewById(R.id.buttonCalendar);
        buttonServiceHours = (Button) findViewById(R.id.buttonServiceHours);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                userFirstName = currentUser.firstName;
                userLastName = currentUser.lastName;

                //set textViewUserEmail text to let the user know they're logged in
                textViewWelcome.setText("Welcome, " + userFirstName + " " + userLastName + "!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //create onclicklistener for button
        buttonLogOut.setOnClickListener(this);
        buttonContactList.setOnClickListener(this);
        buttonMessageBoard.setOnClickListener(this);
        buttonCalendar.setOnClickListener(this);
        buttonServiceHours.setOnClickListener(this);


    }

    @Override
    public void onClick(View view){
        //check if they pressed the logout button
        if(view == buttonLogOut){
            //sign them out, finish the activity, then go back to login screen
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else if (view == buttonContactList) {
            // when contact button hit, start ContactPage Activity
            startActivity(new Intent(this, ContactPageActivity.class));

        }else if(view == buttonMessageBoard){
            // when message board button hit, start MessageBoard Activity
            startActivity(new Intent(this, MessageBoardActivity.class));
        }else if(view == buttonCalendar){
            // when calendar button hit, start Calendar Activity
            startActivity(new Intent(this, CalendarActivity.class));
        }else if( view == buttonServiceHours){
            // when service hour button hit, start ServiceHour Activity
            startActivity(new Intent(this, ServiceHourActivity.class));
        }

    }


}
