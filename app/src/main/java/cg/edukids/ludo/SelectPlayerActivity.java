package cg.edukids.ludo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cg.edukids.R;

public class SelectPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player);
    }

    public void StartGame(View view) {
        Intent i = new Intent(SelectPlayerActivity.this, StartLudoActivity.class);
        startActivity(i);
    }
}