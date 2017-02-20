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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    //create firebase auth object
    private FirebaseAuth firebaseAuth;

    //create objects for GUI components
    private TextView textViewUserEmail;
    private Button buttonLogOut;


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

        //link objects with references to GUI components
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);

        //set textViewUserEmail text to let the user know they're logged in
        textViewUserEmail.setText(user.getEmail() + " is logged in!");

        //create onclicklistener for button
        buttonLogOut.setOnClickListener(this);


    }

    @Override
    public void onClick(View view){
        //check if they pressed the logout button
        if(view == buttonLogOut){
            //sign them out, finish the activity, then go back to login screen
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}
