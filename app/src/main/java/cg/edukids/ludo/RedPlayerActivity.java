package cg.edukids.ludo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import cg.edukids.R;

public class RedPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_player);
    }

    public void startGame(View view){
        Intent in= new Intent(RedPlayerActivity.this, StartGameActivity.class);
        startActivity(in);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.close)
            startActivity(new Intent(getApplicationContext(), LudoGameActivity.class));

        return super.onOptionsItemSelected(item);
    }
}