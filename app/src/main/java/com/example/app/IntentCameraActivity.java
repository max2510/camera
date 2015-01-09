package com.example.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Maxime on 25/09/14.
 */
public class IntentCameraActivity extends Activity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private File file;
    public static final int MEDIA_TYPE_IMAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        file = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                ImageView preview = (ImageView) findViewById(R.id.intentCameraPreview);
                String filepath = file.getAbsolutePath();
                Bitmap myBitmap = BitmapFactory.decodeFile(filepath);
                preview.setImageBitmap(myBitmap);
                MediaStore.Images.Media.insertImage(getContentResolver(), myBitmap, "phototest", "taken with intent camera");
                MediaScannerConnection.scanFile(IntentCameraActivity.this, new String[]{filepath}, null, null);





            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
        }


    }
}

    /**
     * Create a File for saving an image or video
     */



    private static File getOutputMediaFileUri(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "PhotoTest");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
               // Log.d("PhotoTest", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

       //Log.v("PhotoTest", "storing at " + mediaFile.getAbsolutePath());
        return mediaFile;
    }
}
