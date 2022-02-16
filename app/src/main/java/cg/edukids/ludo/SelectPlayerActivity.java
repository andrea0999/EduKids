package cg.edukids.ludo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cg.edukids.R;
import cg.edukids.drawing.StartDrawingActivity;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.close){
            startActivity(new Intent(getApplicationContext(), LudoGameActivity.class));
        }else if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}