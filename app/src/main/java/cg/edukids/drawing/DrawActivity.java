package cg.edukids.drawing;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.thebluealliance.spectrum.SpectrumPalette;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

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

        //initToolbar();

        SpectrumPalette spectrumPalette = findViewById(R.id.palette);
        spectrumPalette.setOnColorSelectedListener(this);

        drawView = findViewById(R.id.drawView);
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

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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

        // Merge dar nu salveaza in folder, ci doar in stocarea interna
        /*Bitmap bitmap = drawView.getBitmap();
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/DCIM/EduKids"); //+ "/saved_images"
        String file_name = UUID.randomUUID() + ".jpg";
        File file = new File (myDir, file_name);
        System.out.println("!!!!! FILE: " + file.getAbsolutePath() + " " + file.getName());

        if(!myDir.exists()){
            myDir.mkdir();
            System.out.println("!!!!! DIR: " + myDir.getAbsolutePath());
        }
        System.out.println("!!!!! DIR: " + myDir.getAbsolutePath());

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // TEST SAVE VAR 1
        Bitmap bitmap = drawView.getBitmap();
        String file_name = UUID.randomUUID() + ".jpg";
        System.out.println("file_name: "+ file_name.getBytes());
        File root = Environment.getExternalStorageDirectory();
        System.out.println("file: "+ root.getName());
        File folder = new File(root.toString() + File.separator + Environment.DIRECTORY_PICTURES + File.separator + getString(R.string.app_name));
        System.out.println("folder: "+ folder.getName() + " " + folder.getAbsolutePath());

        FileOutputStream fos = null;

        if(!folder.exists()) {
            folder.mkdir();
            System.out.println("!!!!! DIR: " + folder.getAbsolutePath());
        }
        System.out.println("!!!!! DIR: " + folder.getAbsolutePath());
            try {
                File outFile = new File(folder,file_name);
                System.out.println("outFILE: " + outFile.getName());
                fos = new FileOutputStream(outFile);
                System.out.println("out: "+ fos.getChannel());
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

        // TEST SAVE VAR 3 - Nu merge !!!!
        /*File dir = new File(Environment.getDataDirectory(),"My Draw");
        FileOutputStream outputStream = null;

        if(!dir.exists()){
            dir.mkdir();
            System.out.println("!!!!! DIR: " + dir.getAbsolutePath());
        }

        Bitmap bitmap = drawView.getBitmap();
        File file = new File(dir, System.currentTimeMillis()+".jpg");
        System.out.println("!!!!!!!!!!! FILE: " + file.getName());
        try{
            outputStream = new FileOutputStream(file);
            System.out.println("!!!!! OUTPUT: "+ outputStream.getChannel());
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);*/


            // TEST SAVE VAR 2 - Nu merge!!!
        /*Bitmap bitmap = drawView.getBitmap();
        FileOutputStream fileOutputStream = null;
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath() + "MyDraw");
        dir.mkdirs();

        String filename = String.format("%d.png",System.currentTimeMillis());
        File outFile = new File(file,filename);
        try {
            fileOutputStream = new FileOutputStream(outFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        try {
            fileOutputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }*/

        Toast.makeText(this, "Your drawing is saved!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onColorSelected(int color) {
        General.COLOR_SELECTED = color;
    }
}