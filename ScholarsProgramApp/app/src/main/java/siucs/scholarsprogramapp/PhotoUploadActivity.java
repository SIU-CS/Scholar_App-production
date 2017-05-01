package siucs.scholarsprogramapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class PhotoUploadActivity extends AppCompatActivity implements OnClickListener {

    //Final variables used to detect request code
    private static final int GALLERY_REQUEST = 2200;

    //Initialize imageviews
    ImageView ivGallery, ivUpload, uploadSpot;

    //Access Firebase Storage
    private StorageReference mStorage;
    //Access Firebase Database
    private DatabaseReference dbRef;
    private DatabaseReference userRef;
    //Access Firebase Auth
    private FirebaseAuth firebaseAuth;

    private int numPhotos;
    private String userID;
    public Uri galleryUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);

        //initialize firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //make sure user is logged in and if not return to login screen
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        //Creating imageview Objects
        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        ivUpload = (ImageView) findViewById(R.id.ivUpload);
        uploadSpot = (ImageView) findViewById(R.id.uploadSpot);

        //Creating onClickListeners
        ivGallery.setOnClickListener(this);
        ivUpload.setOnClickListener(this);

        //Create firebase Storage
        mStorage = FirebaseStorage.getInstance().getReference();

        dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://scholarapp-f2a76.firebaseio.com/users");

        //create firebase user object
        userID = firebaseAuth.getCurrentUser().getUid();

        userRef = dbRef.child(userID).getRef();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                numPhotos = currentUser.numberOfPhotos;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivGallery:
                //Intent used to open gallery
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                //Creating path to data
                File galleryPictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                //Accessing path to data
                String galleryPictureDirectoryPath = galleryPictureDirectory.getPath();
                //URI representation of image
                Uri galleryPictureData = Uri.parse(galleryPictureDirectoryPath);
                //Set data and type
                galleryIntent.setDataAndType(galleryPictureData, "image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
                break;

            //When camera imageview is clicked
            case R.id.ivUpload:
                if (galleryUri != null) {

                    //Creates pathname that will be stored in firebase storage
                    StorageReference filePath = mStorage.child("Photos").child(userID).child(String.valueOf(numPhotos));

                    //Check if upload was successful
                    filePath.putFile(galleryUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        //On success do this
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(PhotoUploadActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                            dbRef.child(userID).child("numberOfPhotos").setValue(++numPhotos);
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        //On failed upload do this
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PhotoUploadActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                    galleryUri = null;

                } else {
                    Toast.makeText(this, "Null", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Initialize firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //Makes sure gallery has been opened, content selected is okay and not null
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {

            //Address of image on SDCard
            galleryUri = data.getData();

            //Declares stream to read image data from SD Card
            InputStream inputStream;

            try {
                //Stores image in stream
                inputStream = getContentResolver().openInputStream(galleryUri);
                //get bitmap from inputStream
                Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                //Sets imageview to Bitmap image
                uploadSpot.setImageBitmap(imageBitmap);

            } catch (FileNotFoundException e) {
                //Checking stack trace
                e.printStackTrace();
                //Shows message if fails
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(PhotoUploadActivity.this, "Photo failed to upload.", Toast.LENGTH_LONG).show();

        }
    }

}