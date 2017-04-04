package siucs.scholarsprogramapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class PhotoUploadActivity extends AppCompatActivity implements OnClickListener {

    //Final variables used to detect request code
    private static final int GALLERY_REQUEST = 2200;
    private static final int CAMERA_REQUEST = 3300;

    //Initialize imageviews
    ImageView ivCamera, ivGallery, ivUpload, uploadSpot;

    //Access Firebase Storage
    private StorageReference mStorage;

    //Access Firebase Database
    private FirebaseAuth firebaseAuth;

    public Uri cameraUri, galleryUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);

        //Creating imageview Objects
        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        ivUpload = (ImageView) findViewById(R.id.ivUpload);
        uploadSpot = (ImageView) findViewById(R.id.uploadSpot);

        //Creating onClickListeners
        ivCamera.setOnClickListener(this);
        ivGallery.setOnClickListener(this);
        ivUpload.setOnClickListener(this);

        //Create firebase Storage
        mStorage = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //When camera imageview is clicked
            case R.id.ivCamera:
                //Intent used to open camera
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);

                String file = "nameFile" + ".jpg";
                File newfile = new File(file);
                try {
                    newfile.createNewFile();
                } catch (IOException e) {}
                cameraUri = Uri.fromFile(newfile);
                break;

            //When camera imageview is clicked
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
                if(galleryUri != null){
                    //Creates pathname that will be stored in firebase storage
                    StorageReference filePath = mStorage.child("Photos").child(String.valueOf(firebaseAuth.getCurrentUser())).child(galleryUri.getLastPathSegment());

                    //Check if upload was successful
                    filePath.putFile(galleryUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        //On success do this
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(PhotoUploadActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        //On failed upload do this
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PhotoUploadActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                    galleryUri = null;

                }else if(cameraUri != null){
                    //Creates pathname that will be stored in firebase storage
                    StorageReference filePath = mStorage.child("Photos").child(String.valueOf(firebaseAuth.getCurrentUser())).child(cameraUri.getLastPathSegment());

                    //Check if upload was successful
                    filePath.putFile(cameraUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        //On success do this
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(PhotoUploadActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        //On failed upload do this
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PhotoUploadActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                    cameraUri = null;
                }
                else{
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
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {

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

        }else if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            //Converting data into bitmap
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            //Setting imageview to bitmap image
            uploadSpot.setImageBitmap(thumbnail);

            Toast.makeText(PhotoUploadActivity.this, "Loaded...", Toast.LENGTH_LONG).show();



        }else{
            Toast.makeText(PhotoUploadActivity.this, "FAILLLLLLLL", Toast.LENGTH_LONG).show();

        }
    }


}