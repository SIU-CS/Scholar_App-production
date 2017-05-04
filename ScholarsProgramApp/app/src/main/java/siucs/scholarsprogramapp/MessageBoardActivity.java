package siucs.scholarsprogramapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class MessageBoardActivity extends AppCompatActivity {

    public ListView mListView;
    public Button buttonWritePost;

    public DatabaseReference mDatabasePosts;
    public ArrayList<String> postStringList = new ArrayList<>();
    public ArrayList<MessageBoardPost> postList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board);


        mListView = (ListView) findViewById(R.id.postListView);
        buttonWritePost = (Button) findViewById(R.id.buttonWritePost);

        mDatabasePosts = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/posts");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, postStringList);
        mListView.setAdapter(arrayAdapter);

        buttonWritePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(MessageBoardActivity.this, AddPostActivity.class));
                finish();


            }
        });




        mDatabasePosts.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //save each child as a post with datasnapshot
                MessageBoardPost tempPost = dataSnapshot.getValue(MessageBoardPost.class);
                postList.add(tempPost);
                postList.trimToSize();
                //add formatted string to be displayed
                postStringList.add(tempPost.shortDesc + "\n" + tempPost.posterName + " - " +
                                   DateFormat.format("MM/dd/yyyy - hh:mm aaa", new Date(tempPost.timeStamp)).toString());
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



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), Integer.toString(position), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MessageBoardActivity.this, MessageBoardPostActivity.class);
                intent.putExtra("post", new Gson().toJson(postList.get(position)));
                startActivity(intent);
                finish();



                //Toast.makeText(getApplicationContext(), Long.toString(dayEventList.get(position).time), Toast.LENGTH_SHORT).show();
            }
        });

    }
    /*
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
    */

}
