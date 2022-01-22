package cg.edukids.memory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import cg.edukids.R;
import cg.edukids.puzzle.PuzzleActivity;

public class MemoryActivity extends AppCompatActivity {

    private Button memoryBackBtn,memoryBtn;

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

        memoryBtn = findViewById(R.id.memoryBtn);
        memoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StartMemoryActivity.class));
            }
        });

    }
}