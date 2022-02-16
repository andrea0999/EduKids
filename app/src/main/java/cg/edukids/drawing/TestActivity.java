package cg.edukids.drawing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import cg.edukids.R;

public class TestActivity extends AppCompatActivity {

    private DrawActivity drawActivity = new DrawActivity();

    private Bitmap bitmap;
    private ImageView shareImage;
    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        shareImage = findViewById(R.id.shareImage);

        //SharedPreferences sp = getSharedPreferences(drawActivity.getTestFile(), Context.MODE_PRIVATE);    //YourFileName= Any file name you give it
        //String levelCompleted = sp.getString("levels", "0");     //levels=key at which no. of levels is saved.

        //SharedPreferences sp = getSharedPreferences(drawActivity.getFileShare(), MODE_PRIVATE);
        //selectedImagePath = sp.getString("ImagePath", "");
        //bitmap = BitmapFactory.decodeFile(selectedImagePath);
        //String imageS = myPrefrence.getString("imagePreferance", "");
        //bitmap = decodeToBase64(drawActivity.getSelectedImagePath());
        //shareImage.setImageBitmap(bitmap);
    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}