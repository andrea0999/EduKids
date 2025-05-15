package cg.edukids.labyrinth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cg.edukids.R;
import cg.edukids.labyrinth.controller.AccelerometerController;
import cg.edukids.labyrinth.utils.LevelManager;
import cg.edukids.labyrinth.view.GameView;

public class MazeActivity extends AppCompatActivity {

    private GameView gameView;
    private AccelerometerController accelerometerController;
    private LevelManager levelManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int level = getIntent().getIntExtra("level", 0);
        levelManager = new LevelManager(level);

        gameView = new GameView(this, levelManager);
        setContentView(gameView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometerController != null) {
            accelerometerController.register();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (accelerometerController != null) {
            accelerometerController.unregister();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && accelerometerController == null) {
            accelerometerController = new AccelerometerController(this, gameView.getPlayer());
            accelerometerController.register();
        }
    }

    public void showVictoryDialog() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                AlertDialog.Builder alertDialog =  new AlertDialog.Builder(MazeActivity.this);
                alertDialog.setTitle("You win, congrats!")
                        .setMessage("Do you want to continue?")
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int nextLevel = levelManager.getLevel() + 1;
                                Intent intent = new Intent(MazeActivity.this, MazeActivity.class);
                                intent.putExtra("level", nextLevel);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                AlertDialog dialog = alertDialog.create();
                gameView.setGamePaused(true);
                dialog.show();
            }
        }, 10);

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

        if(id == R.id.close)
            startActivity(new Intent(getApplicationContext(), MazeGameActivity.class));

        return super.onOptionsItemSelected(item);
    }
}
