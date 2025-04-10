package cg.edukids.math.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import cg.edukids.R;
import cg.edukids.memory.MemoryActivity;
import cg.edukids.memory.StartMemoryEasyActivity;
import cg.edukids.memory.StartMemoryHardActivity;
import cg.edukids.memory.StartMemoryMediumActivity;

public class MathListActivity extends AppCompatActivity {

    private Button plusBtn, minusBtn, inmultitBtn, impartitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_list);

        plusBtn = findViewById(R.id.plusBtn);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProblemActivity.class));
            }
        });

        minusBtn = findViewById(R.id.minusBtn);
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProblemActivity.class));
            }
        });

        inmultitBtn = findViewById(R.id.inmultitBtn);
        inmultitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProblemActivity.class));
            }
        });

        impartitBtn = findViewById(R.id.impartitBtn);
        impartitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProblemActivity.class));
            }
        });

        plusBtn.setOnClickListener(v -> launchProblemActivity('+'));
        minusBtn.setOnClickListener(v -> launchProblemActivity('-'));
        inmultitBtn.setOnClickListener(v -> launchProblemActivity('*'));
        impartitBtn.setOnClickListener(v -> launchProblemActivity('/'));
    }

    private void launchProblemActivity(char operator) {
        Intent intent = new Intent(MathListActivity.this, ProblemActivity.class);
        intent.putExtra("operator", String.valueOf(operator));
        startActivity(intent);
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
