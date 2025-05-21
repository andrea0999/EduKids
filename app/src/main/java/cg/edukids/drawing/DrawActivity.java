package cg.edukids.drawing;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

import cg.edukids.MainActivity;
import cg.edukids.R;
import cg.edukids.drawing.general.General;
import cg.edukids.drawing.widget.DrawView;

public class DrawActivity extends AppCompatActivity implements SpectrumPalette.OnColorSelectedListener {

    private static final int PERMISION_REQUEST = 10001;
    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        drawView = findViewById(R.id.drawView);
        SpectrumPalette spectrumPalette = findViewById(R.id.palette);
        spectrumPalette.setOnColorSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.save){
            showDialogForSaveImage();
        }else if(id == R.id.close){
            startActivity(new Intent(getApplicationContext(), StartDrawingActivity.class));
        }else if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showDialogForSaveImage() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISION_REQUEST);
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Save Your Draw ?");

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.setPositiveButton("Save in phone", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    save();
                    dialog.dismiss();
                }
            });

            builder.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            save();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void save() {

        Bitmap bitmap = drawView.getBitmap();
        String file_name = UUID.randomUUID() + ".jpg";
        File root = Environment.getExternalStorageDirectory();
        File folder = new File(root.toString() + File.separator + Environment.DIRECTORY_PICTURES + File.separator + getString(R.string.app_name));

        FileOutputStream fos = null;

        if(!folder.exists()) {
            folder.mkdir();
        }
            try {
                File outFile = new File(folder,file_name);
                fos = new FileOutputStream(outFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                try {
                    fos.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    fos.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        Toast.makeText(this, "Your drawing is saved!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onColorSelected(int color) {
        General.COLOR_SELECTED = color;
    }

    public void selectColor(View view) {
        ColorPickerDialogBuilder.with(this)
                .initialColor(General.COLOR_SELECTED)
                .setTitle("Select Color")
                .density(15)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .setPositiveButton("OK", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int lastSelectedColor, Integer[] allColors) {
                        General.COLOR_SELECTED = lastSelectedColor;
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d, int i) {
                        d.dismiss();
                    }
                })
                .build()
                .show();
    }

    public void undoAction(View view) {
        drawView.undoAction();
    }
}