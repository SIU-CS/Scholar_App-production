package siucs.scholarsprogramapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class AddPostActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public DatabaseReference mDatabaseUsers;
    public DatabaseReference mDatabasePosts;

    public MessageBoardPost post;

    public EditText textSubject;
    public EditText textBody;
    public Button buttonAddPost;
    public User currentUser;

    public ArrayList<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mDatabaseUsers = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/users");
        mDatabasePosts = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/posts");

        mDatabaseUsers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //save each child as a user with datasnapshot
                User tempUser = dataSnapshot.getValue(User.class);

                userList.add(tempUser);
                userList.trimToSize();

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

        textSubject = (EditText) findViewById(R.id.textSubject);
        textBody = (EditText) findViewById(R.id.textBody);
        buttonAddPost = (Button) findViewById(R.id.buttonAddPost);

        //initialize firebase auth object
        mAuth = FirebaseAuth.getInstance();

        //make sure user is logged in and if not return to login screen
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        buttonAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost();
            }
        });



    }

    private void addPost() {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).email.equals(mAuth.getCurrentUser().getEmail())) {
                currentUser = userList.get(i);
            }
        }
        if (currentUser != null) {
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("CST"));

            post = new MessageBoardPost(currentUser.firstName + " " + currentUser.lastName, textSubject.getText().toString(),
                    textBody.getText().toString(), c.getTimeInMillis());


            mDatabasePosts.child(Long.toString(post.timeStamp)).setValue(post);

            startActivity(new Intent(AddPostActivity.this, MessageBoardActivity.class));
            finish();
        }
    }
}
