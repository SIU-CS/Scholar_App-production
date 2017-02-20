package siucs.scholarsprogramapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //create Firebase auth object
    private FirebaseAuth firebaseAuth;

    //create objects for the GUI fields and button
    private Button buttonLogIn;
    private EditText editTextEmail;
    private EditText editTextPassword;

    //progress dialog for validation
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //check if user is already logged on
        if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        }

        //link created objects with references to the GUI objects
        buttonLogIn = (Button) findViewById(R.id.buttonLogIn);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        //give the button an OnClickListener
        buttonLogIn.setOnClickListener(this);

        //instantiate progress dialog object
        progressDialog = new ProgressDialog(this);
    }

    private void logInUser(){
        //get email and password from editText fields and store them
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //check for whether the email and password are empty
        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please Enter Email Address", Toast.LENGTH_SHORT).show();
            //return stops the function from going further
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            //return stops the function from going further
            return;
        }

        //if info passes validation show a progress dialog
        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        //call firebase sign in method
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    //this method calls when sign in is done and uses
                    //the object task for info like whether the log in was
                    //successful or not
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        //check if auth was successful
                        if(task.isSuccessful()){
                            //start profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }

                    }
                });

    }

    @Override
    public void onClick(View view){
        if(view == buttonLogIn){
            logInUser(); //will log user in
        }
    }
}
