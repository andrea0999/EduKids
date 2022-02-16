package cg.edukids.memory;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import cg.edukids.HomeActivity;
import cg.edukids.R;

public class MemoryListActivity extends AppCompatActivity {

    private Button easy, medium, hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_list);

        easy = findViewById(R.id.easyBtn);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StartMemoryEasyActivity.class));
            }
        });

        medium = findViewById(R.id.mediumBtn);
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StartMemoryMediumActivity.class));
            }
        });

        hard = findViewById(R.id.hardBtn);
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StartMemoryHardActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back,menu);
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.close)
            startActivity(new Intent(getApplicationContext(), MemoryActivity.class));

        return super.onOptionsItemSelected(item);
    }
}