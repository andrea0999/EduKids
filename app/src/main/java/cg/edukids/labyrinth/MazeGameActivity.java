package cg.edukids.labyrinth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import cg.edukids.HomeActivity;
import cg.edukids.R;
import cg.edukids.profile.ProfileActivity;
import cg.edukids.puzzle.PuzzleActivity;

public class MazeGameActivity extends AppCompatActivity {

    private Button ludoNextBtn, ludoBackBtn, ludoBtn, checkProgressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze_game);

        ludoNextBtn = findViewById(R.id.ludoNextBtn);
        ludoNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PuzzleActivity.class));
            }
        });

        ludoBackBtn = findViewById(R.id.ludoBackBtn);
        ludoBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        ludoBtn = findViewById(R.id.ludoBtn);
        ludoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MazeActivity.class));  //LudoMainMenuActivity.class)); //GameManager.class));
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