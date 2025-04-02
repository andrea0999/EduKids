package cg.edukids.memory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import cg.edukids.R;
import cg.edukids.math.MathActivity;
import cg.edukids.profile.ProfileActivity;
import cg.edukids.puzzle.PuzzleActivity;

public class MemoryActivity extends AppCompatActivity {

    private Button memoryBackBtn, memoryNextBtn, memoryBtn, checkProgressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        memoryBackBtn = findViewById(R.id.memoryBackBtn);
        memoryBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PuzzleActivity.class));
            }
        });

        memoryNextBtn = findViewById(R.id.memoryNextBtn);
        memoryNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MathActivity.class));
            }
        });

        memoryBtn = findViewById(R.id.memoryBtn);
        memoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MemoryListActivity.class));
            }
        });

        checkProgressBtn = findViewById(R.id.checkProgressBtn);
        checkProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

    }
}