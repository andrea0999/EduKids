package cg.edukids.ludo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cg.edukids.R;

public class BluePlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_player);
    }

    public void startGame(View view){
        Intent in= new Intent(BluePlayerActivity.this, StartGameActivity.class);
        startActivity(in);
    }
}