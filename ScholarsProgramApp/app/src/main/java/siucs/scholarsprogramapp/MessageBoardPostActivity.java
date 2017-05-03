package siucs.scholarsprogramapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class MessageBoardPostActivity extends AppCompatActivity {

    public ListView mListView;
    public Button buttonAddPost;

    public DatabaseReference mDatabasePosts;
    public ArrayList<String> postStringList = new ArrayList<>();
    public ArrayList<MessageBoardPost> postList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board_post);

        mListView = (ListView) findViewById(R.id.postListView);
        buttonAddPost = (Button) findViewById(R.id.buttonAddPost);

        mDatabasePosts = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/posts");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, postStringList);
        mListView.setAdapter(arrayAdapter);

        buttonAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlaceholderPost();
            }
        });





        /*

        mDatabasePosts.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //save each child as a post with datasnapshot
                MessageBoardPost tempPost = dataSnapshot.getValue(MessageBoardPost.class);
                postList.add(tempPost);
                postList.trimToSize();
                //add formatted string to be displayed
                postStringList.add(tempPost.shortDesc + "\n" + tempPost.posterName + " - " +
                                   DateFormat.format("MM/dd/yyyy", new Date(tempPost.timeStamp)).toString());
                postStringList.trimToSize();
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
        */
    }

    private void addPlaceholderPost(){
        MessageBoardSubPost tempSubPost1 = new MessageBoardSubPost("Ron Swanson", "this is a comment", 1495540800000L);
        MessageBoardSubPost tempSubPost2 = new MessageBoardSubPost("Swan Ronson", "this is also a comment", 1495540800001L);
        ArrayList<MessageBoardSubPost> subPosts = new ArrayList<>();
        subPosts.add(tempSubPost1);
        subPosts.add(tempSubPost2);
        subPosts.trimToSize();

        MessageBoardPost tempPost = new MessageBoardPost("Gabe Wohlwend", "Placeholder post about things",
                "This is the long body of the placeholder post about things",
                1495540800000L,  subPosts);

        mDatabasePosts.child("1495540800000").setValue(tempPost);
    }

}
