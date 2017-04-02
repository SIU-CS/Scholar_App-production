
/*
    THIS IS A ONE TIME USE PROGRAM THAT WAS DESIGNED TO EDIT THE FIREBASE DB
    AND BATCH CREATE USER ACCOUNTS. DO NOT RUN THIS WITHOUT COMPLETELY CHANGING
    IT. IF RAN AS IT CURRENTLY IS IT WOULD FAIL BECAUSE IT MAKES A REFERENCE TO
    A BRANCH OF THE DB THAT DOESN'T EXIST ANYMORE

    -GABE WOHLWEND
 */


package scholarbase.edit.firebaseeditor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public boolean failedSomewhere = false;
    public boolean isReady = false;

    public int usersAddedToArrayList = 0;
    public int usersAuthenticated = 0;
    public String defaultPassword = "123456";

    public DatabaseReference mDatabaseNew;
    public DatabaseReference mDatabaseOld;
    public FirebaseAuth mAuth;

    public ListView mListView;
    public TextView mTextView;
    public Button mButton;


    public ArrayList<String> displayList = new ArrayList<>();
    public ArrayList<User> userList = new ArrayList<>();
    public ArrayList<User> usersFailedList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        mTextView = (TextView) findViewById(R.id.textView);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthNewUser();
            }
        });


        mDatabaseOld = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/users1");
        mDatabaseNew = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/users");

        mAuth = FirebaseAuth.getInstance();


        mDatabaseOld.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                User tempUser = dataSnapshot.getValue(User.class);
                userList.add(tempUser);
                usersAddedToArrayList++;
                userList.trimToSize();

                if (usersAddedToArrayList == 123) {
                    mTextView.setText("Ready");
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
    }

    public void ListUIDs() {

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);

        mListView.setAdapter(arrayAdapter);

        for (int i = 0; i < 123; i++) {
            displayList.add(userList.get(i).firstName + " " + userList.get(i).lastName + ": " + userList.get(i).uid);
            arrayAdapter.notifyDataSetChanged();
        }
        AddToDB();
    }
    public void AuthNewUser() {

        mTextView.setText("Not Ready");
        Log.i("TAG", "STARTING USER AUTH");
        mAuth.createUserWithEmailAndPassword(userList.get(usersAuthenticated).email, defaultPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("TAG", "SUCCESS!!! USER " + userList.get(usersAuthenticated).firstName + " " + userList.get(usersAuthenticated).lastName + " IS AUTHENTICATED");
                    signNewUserIn();
                } else {
                    Log.i("TAG", "FAILED TO MAKE USER " + userList.get(usersAuthenticated).firstName + " " + userList.get(usersAuthenticated).lastName);
                    failedSomewhere = true;
                    usersFailedList.add(userList.get(usersAuthenticated));
                }
            }
        });
    }

    public void signNewUserIn() {
        Log.i("TAG", "STARTING USER SIGNIN");
        mAuth.signInWithEmailAndPassword(userList.get(usersAuthenticated).email, defaultPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("TAG", "SUCCESS!!! USER " + userList.get(usersAuthenticated).firstName + " " + userList.get(usersAuthenticated).lastName + " LOGGED IN ");
                    AddUID();
                } else {
                    Log.i("TAG", "FAILED TO SIGN IN " + userList.get(usersAuthenticated).firstName + " " + userList.get(usersAuthenticated).lastName);
                    usersFailedList.add(userList.get(usersAuthenticated));
                }
            }
        });
    }


    public void AddUID() {
        Log.i("TAG", "STARTING ADDUID");

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.i("TAG", "UID OF SIGNED IN USER " + authUser.getUid());

        User tempUser = userList.get(usersAuthenticated);
        tempUser.uid = authUser.getUid();

        userList.set(usersAuthenticated, tempUser);
        Log.i("TAG", "USER ID OF " + userList.get(usersAuthenticated).firstName + " HAS BEEN SET TO  " + userList.get(usersAuthenticated).uid + " IN userList");
        mAuth.signOut();

        usersAuthenticated++;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(usersAuthenticated < 123) {
            if (!failedSomewhere && user == null) {
                mTextView.setText("Ready");
                AuthNewUser();

            } else {
                mTextView.setText("Failed somewhere or not signed out yet");
            }
        }
        else{
            mTextView.setText("Ready For checking UIDs");
            isReady = true;
            ListUIDs();
        }
    }

    public void AddToDB(){
        for (int i = 0; i < 123; i++) {
            mDatabaseNew.child(userList.get(i).uid).setValue(userList.get(i));
            mDatabaseNew.child(userList.get(i).uid).child("uid").removeValue();
        }
    }


}