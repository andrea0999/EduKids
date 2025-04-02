package cg.edukids.learn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import cg.edukids.HomeActivity;
import cg.edukids.R;
import cg.edukids.learn.activity.StartLearnActivity;
import cg.edukids.math.MathActivity;
import cg.edukids.memory.MemoryListActivity;
import cg.edukids.profile.ProfileActivity;
import cg.edukids.puzzle.PuzzleActivity;

public class LearnActivity extends AppCompatActivity {

    private Button learnBackBtn, learnNextBtn, learnBtn, checkProgressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        learnBackBtn = findViewById(R.id.learnBackBtn);
        learnBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MathActivity.class));
            }
        });

        learnNextBtn = findViewById(R.id.learnNextBtn);
        learnNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        learnBtn = findViewById(R.id.learnBtn);
        learnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StartLearnActivity.class));
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
