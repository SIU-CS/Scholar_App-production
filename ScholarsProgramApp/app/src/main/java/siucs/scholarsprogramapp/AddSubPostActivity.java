package siucs.scholarsprogramapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class AddSubPostActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public DatabaseReference mDatabaseUsers;
    public DatabaseReference mDatabasePosts;

    public MessageBoardPost post;

    public EditText textComment;
    public Button buttonAddSubPost;
    public User currentUser;

    public ArrayList<User> userList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_post);

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

        textComment = (EditText) findViewById(R.id.textComment);
        buttonAddSubPost = (Button) findViewById(R.id.buttonAddSubPost);

        String jsonPost = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonPost = extras.getString("post");
        }
        post = new Gson().fromJson(jsonPost, MessageBoardPost.class);

        //initialize firebase auth object
        mAuth = FirebaseAuth.getInstance();

        //make sure user is logged in and if not return to login screen
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }


        buttonAddSubPost.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addComment();
        }
    });




    }

    private void addComment() {
        for(int i = 0; i < userList.size(); i++){
            if(userList.get(i).email.equals(mAuth.getCurrentUser().getEmail())){
                currentUser = userList.get(i);
            }
        }
        if(currentUser != null){
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("CST"));
            MessageBoardSubPost tempSubPost = new MessageBoardSubPost(currentUser.firstName + " " + currentUser.lastName,
                                                                      textComment.getText().toString(), c.getTimeInMillis());
            post.subPosts.add(tempSubPost);

            mDatabasePosts.child(Long.toString(post.timeStamp + 1L)).setValue(post);
            mDatabasePosts.child(Long.toString(post.timeStamp)).removeValue();

            Intent intent = new Intent(AddSubPostActivity.this, MessageBoardPostActivity.class);
            intent.putExtra("post", new Gson().toJson(post));
            startActivity(intent);
            finish();
        }
    }
}
