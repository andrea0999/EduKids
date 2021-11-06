package cg.edukids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LudoGameActivity extends AppCompatActivity {

    private Button ludoNextBtn, ludoBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludo_game);

        ludoNextBtn = findViewById(R.id.ludoNextBtn);
        ludoNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PuzzleActivity.class));
            }
        });

        ludoBackBtn = findViewById(R.id.ludoBackBtn);
        ludoBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });
    }
}