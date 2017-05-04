package siucs.scholarsprogramapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class MessageBoardPostActivity extends AppCompatActivity {



    public TextView postDesc;
    public TextView postNameAndDate;
    public Button buttonAddComment;
    public ListView subPostListView;
    public MessageBoardPost post;


    public ArrayList<String> subPostList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_board_post);




        postDesc = (TextView) findViewById(R.id.postDesc);
        postNameAndDate = (TextView) findViewById(R.id.postNameAndDate);
        buttonAddComment = (Button) findViewById(R.id.buttonAddComment);
        subPostListView = (ListView) findViewById(R.id.subPostListView);



        String jsonPost = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonPost = extras.getString("post");
        }
        post = new Gson().fromJson(jsonPost, MessageBoardPost.class);




        postDesc.setText(post.body);
        postNameAndDate.setText(post.posterName + " - " +
                DateFormat.format("MM/dd/yyyy - hh:mm aaa", new Date(post.timeStamp)).toString());



        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subPostList);
        subPostListView.setAdapter(arrayAdapter);



        for(int i = 0; i < post.subPosts.size(); i++){
            subPostList.add(post.subPosts.get(i).body + "\n" + post.subPosts.get(i).posterName + " - " +
                            DateFormat.format("MM/dd/yyyy - hh:mm aaa", new Date(post.subPosts.get(i).timeStamp)).toString());
            arrayAdapter.notifyDataSetChanged();
        }

        buttonAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MessageBoardPostActivity.this, AddSubPostActivity.class);
                intent.putExtra("post", new Gson().toJson(post));
                startActivity(intent);
                finish();
            }
        });





    }
}
