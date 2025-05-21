package cg.edukids.learn.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

import cg.edukids.R;
import cg.edukids.learn.utils.localization.ContextWrapper;

public class FruitActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(newBase);
        String languageCode = prefs.getString("app_language", "en");
        Context context = ContextWrapper.wrap(newBase, new Locale(languageCode));
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);

        String name = getIntent().getStringExtra("name").toLowerCase();

        ImageView fruitImage = findViewById(R.id.fruitImage);
        int imgRes = getResources().getIdentifier(name, "drawable", getPackageName());
        fruitImage.setImageResource(imgRes);

        String audioName = name.toLowerCase();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String lang = prefs.getString("selected_lang", "en");

        String fileName = lang.equals("ro") ? audioName + "_ro" : audioName;
        int resId = getResources().getIdentifier(fileName, "raw", getPackageName());
        if (resId != 0) {
            MediaPlayer mediaPlayer = MediaPlayer.create(this, resId);
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
        } else {
            Log.e("FruitActivity", "Fișierul audio lipsă: " + fileName);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.close) {
            startActivity(new Intent(getApplicationContext(), StartLearnActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}