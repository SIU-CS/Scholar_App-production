package siucs.scholarsprogramapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewPhotoActivity extends AppCompatActivity {

    //create Firebase auth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbRef;
    private DatabaseReference userRef;
    private StorageReference pStorage;

    private String userID;
    private int numPhotos;
    private ImageView tempIV;
    private Uri tempUri;
    private ArrayList<StorageReference> photoRefArray = new ArrayList<>();
    private ArrayList<ImageView> imagesArrayList = new ArrayList<>();
    private ArrayList<String> stringArray = new ArrayList<>();
    private ArrayAdapter<ImageView> arrayAdapter;

    private int counter = 0;
    private int tempIvId;
    private ImageView[] imageArray;

    //public ListView mListView;

    private ImageView iv0;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private ImageView iv6;
    private ImageView iv7;
    private ImageView iv8;
    private ImageView iv9;
    private ImageView iv10;
    private ImageView iv11;
    private ImageView iv12;
    private ImageView iv13;
    private ImageView iv14;
    private ImageView iv15;
    private ImageView iv16;
    private ImageView iv17;
    private ImageView iv18;
    private ImageView iv19;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);

        //initialize firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //make sure user is logged in and if not return to login screen
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        //mListView = (ListView) findViewById(R.id.mListView);

        iv0 = (ImageView) findViewById(R.id.iv0);
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        iv5 = (ImageView) findViewById(R.id.iv5);
        iv6 = (ImageView) findViewById(R.id.iv6);
        iv7 = (ImageView) findViewById(R.id.iv7);
        iv8 = (ImageView) findViewById(R.id.iv8);
        iv9 = (ImageView) findViewById(R.id.iv9);
        iv10 = (ImageView) findViewById(R.id.iv10);
        iv11 = (ImageView) findViewById(R.id.iv11);
        iv12 = (ImageView) findViewById(R.id.iv12);
        iv13 = (ImageView) findViewById(R.id.iv13);
        iv14 = (ImageView) findViewById(R.id.iv14);
        iv15 = (ImageView) findViewById(R.id.iv15);
        iv16 = (ImageView) findViewById(R.id.iv16);
        iv17 = (ImageView) findViewById(R.id.iv17);
        iv18 = (ImageView) findViewById(R.id.iv18);
        iv19 = (ImageView) findViewById(R.id.iv19);

        //get a reference to users tree
        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/users");

        pStorage = FirebaseStorage.getInstance().getReference();

        //create firebase user object
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userID = user.getUid();

        userRef = dbRef.child(userID).getRef();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                numPhotos = currentUser.classYear; ///////////////CHANGE TO PHOTOSSUBMITTED
                viewPhotos();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    protected void viewPhotos() {
        //imageArray = new ImageView[numPhotos];
        //Toast.makeText(this, "Value end of viewPhotos" + String.valueOf(numPhotos), Toast.LENGTH_SHORT).show();
        addPhotosToArray();
        //displayArrayAdapter();




    }

    protected void addPhotosToArray() {
        pStorage.child("Photos").child(userID).child(Integer.toString(counter)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //tempIV = new ImageView(ViewPhotoActivity.this);
                //tempIvId = 100 + counter;
                //tempIV.setId(tempIvId);
                //imageArray[counter] = (ImageView) findViewById(tempIvId);

                switch(counter) {
                    case 0: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv0);
                        }
                    }
                    case 1: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv1);
                        }
                    }
                    case 2: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv2);
                        }
                    }
                    case 3: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv3);
                        }
                    }
                    case 4: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv4);
                        }
                    }
                    case 5: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv5);
                        }
                    }
                    case 6: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv6);
                        }
                    }
                    case 7: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv7);
                        }
                    }
                    case 8: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv8);
                        }
                    }
                    case 9: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv9);
                        }
                    }
                    case 10: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv10);
                        }
                    }
                    case 11: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv11);
                        }
                    }
                    case 12: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv12);
                        }
                    }
                    case 13: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv13);
                        }
                    }
                    case 14: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv14);
                        }
                    }
                    case 15: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv15);
                        }
                    }
                    case 16: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv16);
                        }
                    }
                    case 17: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv17);
                        }
                    }
                    case 18: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv18);
                        }
                    }
                    case 19: {
                        if(counter < numPhotos) {
                            Picasso.with(ViewPhotoActivity.this).load(uri).into(iv19);
                        }
                    }
                }

                //imagesArrayList.add(imageArray[counter]);
                //Toast.makeText(ViewPhotoActivity.this, "Added photo #" + String.valueOf(counter), Toast.LENGTH_SHORT).show();

                if(counter < numPhotos-1){
                    incrementCounter();
                } else {
                    displayArrayAdapter();
                }


                return;
            }
        });
    }

    protected void incrementCounter() {
        counter++;
        addPhotosToArray();
        return;
    }

    protected void displayArrayAdapter() {
        //create ArrayAdapter for the listView and connect it with imagesArrayList
        Toast.makeText(ViewPhotoActivity.this, "Aaaaaand we're back to display our ArrayAdapter", Toast.LENGTH_SHORT).show();
        //arrayAdapter = new ArrayAdapter<>(ViewPhotoActivity.this, android.R.layout.simple_list_item_1, imagesArrayList);
        //mListView.setAdapter(arrayAdapter);
        return;
    }

}
