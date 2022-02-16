package cg.edukids.puzzle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import cg.edukids.R;
import cg.edukids.ludo.LudoGameActivity;
import cg.edukids.memory.MemoryActivity;
import cg.edukids.profile.ProfileActivity;

public class PuzzleActivity extends AppCompatActivity {

    private Button puzzleNextBtn, puzzleBackBtn, puzzleBtn, checkProgressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        puzzleNextBtn = findViewById(R.id.puzzleNextBtn);
        puzzleNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MemoryActivity.class));
            }
        });

        puzzleBackBtn = findViewById(R.id.puzzleBackBtn);
        puzzleBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LudoGameActivity.class));
            }
        });

        puzzleBtn = findViewById(R.id.puzzleBtn);
        puzzleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PuzzleListActivity.class));
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