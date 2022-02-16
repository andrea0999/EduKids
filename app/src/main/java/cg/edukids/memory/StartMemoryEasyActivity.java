package cg.edukids.memory;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import cg.edukids.HomeActivity;
import cg.edukids.R;

public class StartMemoryEasyActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    private Integer[] cardsArray= {1, 2, 3, 4,
                                    5, 6, 7, 8} ;
    private int imgArt1, imgAtomi1, imgBinocurals1, imgRocket1,
            imgArt2, imgAtomi2, imgBinocurals2, imgRocket2;
    private int firstCard, secondCard;
    private int clickedFirst, clickedSecond;
    private int cardNumber = 1;
    private TextView timerText;
    private Timer timer;
    private TimerTask timerTask;
    private Double time = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_memory_easy);

        timerText = findViewById(R.id.timerMemoryEasy);
        timer = new Timer();
        startTimer();

        findButton();
        setTagForButton();

        frontOfCardResource();

        Collections.shuffle(Arrays.asList(cardsArray));

        buttonsSetOnClick();
    }

    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }
    private String getTimerText(){
        int getTime = (int) Math.round(time);

        int hours = ((getTime % 86400) % 3600) % 60;
        int minutes = ((getTime % 86400) % 3600) / 60;
        int seconds = ((getTime % 86400) /3600);

        return formatTime(seconds, minutes, hours);
    }
    private String formatTime(int hours, int minutes, int seconds){
        return String.format("%02d", hours) + " : "
                + String.format("%02d", minutes) + " : "
                + String.format("%02d", seconds);
    }

    private void findButton(){
        btn1 = findViewById(R.id.btn1e);
        btn2 = findViewById(R.id.btn2e);
        btn3 = findViewById(R.id.btn3e);
        btn4 = findViewById(R.id.btn4e);
        btn5 = findViewById(R.id.btn5e);
        btn6 = findViewById(R.id.btn6e);
        btn7 = findViewById(R.id.btn7e);
        btn8 = findViewById(R.id.btn8e);
    }

    private void setTagForButton() {
        btn1.setTag("0");
        btn2.setTag("1");
        btn3.setTag("2");
        btn4.setTag("3");
        btn5.setTag("4");
        btn6.setTag("5");
        btn7.setTag("6");
        btn8.setTag("7");
    }

    private void frontOfCardResource() {
        imgArt1 = R.drawable.art;
        imgAtomi1 = R.drawable.atomi;
        imgBinocurals1 = R.drawable.binoculars;
        imgRocket1 = R.drawable.rocket;

        imgArt2 = R.drawable.art;
        imgAtomi2 = R.drawable.atomi;
        imgBinocurals2 = R.drawable.binoculars;
        imgRocket2 = R.drawable.rocket;
    }

    private void buttonsSetOnClick() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn1, theCard);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn2, theCard);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn3, theCard);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn4, theCard);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn5, theCard);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn6, theCard);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn7, theCard);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn8, theCard);
            }
        });
    }

    private void duStuff(Button btn, int theCard) {

        if(cardsArray[theCard] == 0){
            btn.setBackgroundResource(imgArt1);
        }else if(cardsArray[theCard] == 1) {
            btn.setBackgroundResource(imgAtomi1);
        }else if(cardsArray[theCard] == 2) {
            btn.setBackgroundResource(imgBinocurals1);
        }else if(cardsArray[theCard] == 3) {
            btn.setBackgroundResource(imgRocket1);
        }else if(cardsArray[theCard] == 4) {
            btn.setBackgroundResource(imgArt2);
        }else if(cardsArray[theCard] == 5) {
            btn.setBackgroundResource(imgAtomi2);
        }else if(cardsArray[theCard] == 6) {
            btn.setBackgroundResource(imgBinocurals2);
        }else if(cardsArray[theCard] == 7) {
            btn.setBackgroundResource(imgRocket2);
        }

        if(cardNumber == 1){
            firstCard = cardsArray[theCard];
            if(firstCard > 4)
                firstCard = firstCard - 4;
            cardNumber = 2;
            clickedFirst = theCard;

            btn.setEnabled(false);
        }else if(cardNumber == 2){
            secondCard = cardsArray[theCard];
            if(secondCard > 4)
                secondCard = secondCard - 4;
            cardNumber = 1;
            clickedSecond = theCard;

            btn1.setEnabled(false);
            btn2.setEnabled(false);
            btn3.setEnabled(false);
            btn4.setEnabled(false);
            btn5.setEnabled(false);
            btn6.setEnabled(false);
            btn7.setEnabled(false);
            btn8.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //check if images are the same
                    calculate();
                }
            }, 1000);
        }

    }

    private void calculate() {
        if(firstCard == secondCard){
            if(clickedFirst == 0) {
                btn1.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 1) {
                btn2.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 2) {
                btn3.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 3) {
                btn4.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 4) {
                btn5.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 5) {
                btn6.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 6) {
                btn7.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 7) {
                btn8.setVisibility(View.INVISIBLE);
            }

            if(clickedSecond == 0) {
                btn1.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 1) {
                btn2.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 2) {
                btn3.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 3) {
                btn4.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 4) {
                btn5.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 5) {
                btn6.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 6) {
                btn7.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 7) {
                btn8.setVisibility(View.INVISIBLE);
            }
        }else{
            btn1.setBackgroundResource(R.drawable.questionmark);
            btn2.setBackgroundResource(R.drawable.questionmark);
            btn3.setBackgroundResource(R.drawable.questionmark);
            btn4.setBackgroundResource(R.drawable.questionmark);
            btn5.setBackgroundResource(R.drawable.questionmark);
            btn6.setBackgroundResource(R.drawable.questionmark);
            btn7.setBackgroundResource(R.drawable.questionmark);
            btn8.setBackgroundResource(R.drawable.questionmark);
        }

        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn5.setEnabled(true);
        btn6.setEnabled(true);
        btn7.setEnabled(true);
        btn8.setEnabled(true);

        //check if the game is finish
        checkFinish();
    }

    private void checkFinish() {
        if(btn1.getVisibility() == View.INVISIBLE &&
                btn2.getVisibility() == View.INVISIBLE &&
                btn3.getVisibility() == View.INVISIBLE &&
                btn4.getVisibility() == View.INVISIBLE &&
                btn5.getVisibility() == View.INVISIBLE &&
                btn6.getVisibility() == View.INVISIBLE &&
                btn7.getVisibility() == View.INVISIBLE &&
                btn8.getVisibility() == View.INVISIBLE ){

            timerTask.cancel();

            AlertDialog.Builder alertDialog =  new AlertDialog.Builder(StartMemoryEasyActivity.this);
            alertDialog.setMessage("You win, congrats!")
                    .setCancelable(false)
                    .setPositiveButton("Start new game", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), StartMemoryEasyActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            AlertDialog dialog = alertDialog.create();
            dialog.show();

        }
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
            startActivity(new Intent(getApplicationContext(), MemoryListActivity.class));

        return super.onOptionsItemSelected(item);
    }

}