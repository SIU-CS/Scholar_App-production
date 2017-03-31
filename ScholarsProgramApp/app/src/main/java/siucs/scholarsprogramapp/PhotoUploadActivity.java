package siucs.scholarsprogramapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



public class PhotoUploadActivity extends AppCompatActivity implements OnClickListener {

    private static final int GALLERY_REQUEST = 2200;
    private static final int CAMERA_REQUEST = 3300;
    ImageView ivCamera, ivGallery, ivUpload, uploadSpot;


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
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ivCamera:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            case R.id.ivGallery:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
                break;
            case R.id.ivUpload:

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Makes sure gallery has been opened, content selected was not empty and was acceptable
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            uploadSpot.setImageURI(selectedImage);

        }else if(requestCode == CAMERA_REQUEST){
            Uri selectedImage = data.getData();
            uploadSpot.setImageURI(selectedImage);

            //Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            //ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            //thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        }
    }
}