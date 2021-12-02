package cg.edukids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cg.edukids.drawing.StartDrawingActivity;

public class HomeActivity extends AppCompatActivity {

    private Button drawingNextBtn,drawingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawingNextBtn = findViewById(R.id.drawingNextBtn);
        drawingNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LudoGameActivity.class));
            }
        });

        drawingBtn = findViewById(R.id.drawingBtn);
        drawingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StartDrawingActivity.class));
            }
        });

    }
}