package cg.edukids.math;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import cg.edukids.HomeActivity;
import cg.edukids.R;
import cg.edukids.learn.LearnActivity;
import cg.edukids.math.activity.ProblemActivity;
import cg.edukids.memory.MemoryActivity;
import cg.edukids.profile.ProfileActivity;

public class MathActivity extends AppCompatActivity {
    private Button mathBackBtn, mathNextBtn, mathBtn, checkProgressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        mathBackBtn = findViewById(R.id.mathBackBtn);
        mathBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MemoryActivity.class));
            }
        });

        mathNextBtn = findViewById(R.id.mathNextBtn);
        mathNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LearnActivity.class));
            }
        });

        mathBtn = findViewById(R.id.mathBtn);
        mathBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProblemActivity.class));
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
