package siucs.scholarsprogramapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ContactPageActivity extends AppCompatActivity {

    public ListView mListView;

    public DatabaseReference mDatabaseUsers;
    public FirebaseAuth mAuth;

    public ArrayList<String> contactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);

        //attach reference to listview
        mListView = (ListView) findViewById(R.id.listView);

        //initialize firebase auth object
        mAuth = FirebaseAuth.getInstance();

        //make sure user is logged in and if not return to login screen
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        //get a reference to users tree
        mDatabaseUsers = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/users");

        //create ArrayAdapter for the listView and connect it with userList
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);
        mListView.setAdapter(arrayAdapter);

        mDatabaseUsers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //save each child as a user with datasnapshot
                User tempUser = dataSnapshot.getValue(User.class);

                //add formatted string to contactList to be displayed
                contactList.add(tempUser.firstName + " " + tempUser.lastName + "\n"
                                + tempUser.email + " - Class of " + tempUser.classYear);
                arrayAdapter.notifyDataSetChanged();

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
}
