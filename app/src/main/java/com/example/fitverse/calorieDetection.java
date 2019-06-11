package com.example.fitverse;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;


import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Calendar;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for calculating the number
 * of calories and other nutrients present in the food.
 */
public class calorieDetection extends AppCompatActivity {
    private final PermissionsDelegate permissionsDelegate = new PermissionsDelegate(this);
    private ImageView img;
    private Button detect;
    private Button gallery;
    private List<Recognition> recognitions = null;
    private TextView txt;
    private int GALLERY = 1, CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private Bitmap bitmap;
    private ObjectDetector detector = null;
    private boolean hasCameraPermission ;


    /**
     * This function is responsible for passing the
     * bitmap values to get the recognitions
     *
     * @param bitmap
     */
    public void RecognizeTakenPhotoTask(Bitmap bitmap) {

        recognitions = Utils.getImageRecognitionResult(
                detector, bitmap, 0.3f);
        int rotation = Utils.getRotation(90);
        Matrix matrix = new Matrix();
        matrix.setRotate(rotation);
        Bitmap rotatedBitmap = null;
        if (rotation != 0) {
            rotatedBitmap = Bitmap.createBitmap(
                    bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        } else {
            rotatedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }
        Canvas canvas = new Canvas(rotatedBitmap);
        Paint rectPaint = new Paint();
        Paint textPaint = new Paint();
        float textSize = Utils.setAttributes(
                calorieDetection.this, rectPaint, textPaint);
        Utils.drawRecognitions(canvas, recognitions, rectPaint, textPaint, textSize);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_detection);
        img = (ImageView) findViewById(R.id.imageView);
        detect = (Button) findViewById(R.id.button);
        gallery = (Button) findViewById(R.id.button3);
        txt = (TextView) findViewById(R.id.textView2);
        txt.setMovementMethod(new ScrollingMovementMethod());
        int PERMISSION_ALL = 1;
        detector = ObjectDetector.get(this);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }

    /**
     * This function is responsible for letting the
     * user choose whether to upload an logo from
     * the gallery or the cammera
     */
    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    /**
     * The logo has been been chosen from the gallery
     * and the activity is started
     */
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    /**
     * The logo is clicked from the camera
     */
    private void takePhotoFromCamera() {
        hasCameraPermission = permissionsDelegate.hasCameraPermission();

        if (hasCameraPermission) {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        } else {
            permissionsDelegate.requestCameraPermission();
        }

    }

    // This methos is used to get the image from gallery or from the camera
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(calorieDetection.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(calorieDetection.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");

            //saveImage(bitmap);
            Toast.makeText(calorieDetection.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
        img.setImageBitmap(bitmap);
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";
                String fact = " ";
                RecognizeTakenPhotoTask(bitmap);
                HashMap<String, Integer> res = Utils.PrintCalorie(recognitions);
                Iterator re = res.entrySet().iterator();
                while (re.hasNext()) {
                    Map.Entry finalresult = (Map.Entry) re.next();
                    System.out.println(finalresult.getValue());
                    fact = (String) finalresult.getKey();
                    text += displayNutrients.countCalorie(fact, (int) finalresult.getValue()) + "\n" + "\n";

                }
                System.out.println(text);

                txt.setText(text);
            }
        });
    }

    /**
     * Image is saved
     *
     * @param myBitmap
     * @return
     */
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"logo/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

}

