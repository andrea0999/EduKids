package cg.edukids.learn.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import cg.edukids.R;

public class FruitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);

        String name = getIntent().getStringExtra("name").toLowerCase();
        //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

        ImageView fruitImage = findViewById(R.id.fruitImage);
        int imgRes = getResources().getIdentifier(name, "drawable", getPackageName());
        fruitImage.setImageResource(imgRes);

        int mediaRes = getResources().getIdentifier(name, "raw", getPackageName());
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), mediaRes);
        mp.start();
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