package cg.edukids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PuzzleActivity extends AppCompatActivity {

    private Button puzzleNextBtn, puzzleBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        puzzleNextBtn = findViewById(R.id.puzzleNextBtn);
        puzzleNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MemoryActivity.class));
            }
        });

        puzzleBackBtn = findViewById(R.id.puzzleBackBtn);
        puzzleBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LudoGameActivity.class));
            }
        });
    }
}