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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class PhotoUploadActivity extends AppCompatActivity implements OnClickListener {

    private static final int GALLERY_REQUEST = 2200;
    private static final int CAMERA_REQUEST = 3300;
    private static final int UPLOAD_REQUEST = 4400;
    ImageView ivCamera, ivGallery, ivUpload, uploadSpot;

    private StorageReference mStorage;

    //static Uri galleryUri, cameraUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);

        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        ivUpload = (ImageView) findViewById(R.id.ivUpload);
        uploadSpot = (ImageView) findViewById(R.id.uploadSpot);

        ivCamera.setOnClickListener(this);
        ivGallery.setOnClickListener(this);
        ivUpload.setOnClickListener(this);

        mStorage = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ivCamera:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            case R.id.ivGallery:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                //Where to find the data
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                //URI representation
                Uri pictureData = Uri.parse(pictureDirectoryPath);
                //Set data and type
                galleryIntent.setDataAndType(pictureData, "image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
                break;

            case R.id.ivUpload:
                /*if (cameraUri != null) {

                    StorageReference filePath = mStorage.child("Photos").child(cameraUri.getLastPathSegment());
                    filePath.putFile(cameraUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(PhotoUploadActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PhotoUploadActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                }else if (galleryUri != null){
                    StorageReference filePath = mStorage.child("Photos").child(galleryUri.getLastPathSegment());
                    filePath.putFile(galleryUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(PhotoUploadActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PhotoUploadActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;*/
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Makes sure gallery has been opened, content selected was not empty and was acceptable
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {

            //Address of image on SDCard
            Uri galleryUri = data.getData();
            //Declares stream to read image data from SD Card
            InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(galleryUri);
                //get bitmap from inputStream
                Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                uploadSpot.setImageBitmap(imageBitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                //Shows message
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }

            StorageReference filePath = mStorage.child("Photos").child(galleryUri.getLastPathSegment());
            filePath.putFile(galleryUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(PhotoUploadActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PhotoUploadActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
                }
            });

        }else if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null){

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            uploadSpot.setImageBitmap(thumbnail);

            Uri cameraUri = data.getData();
            StorageReference filePath = mStorage.child("Photos").child(cameraUri.getLastPathSegment());
            filePath.putFile(cameraUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(PhotoUploadActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                }
            });

        }else{
            Toast.makeText(PhotoUploadActivity.this, "FAILLLLLLLL", Toast.LENGTH_LONG).show();

        }
    }

}