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

public class StartMemoryHardActivity extends AppCompatActivity { //implements TimerAbstract {

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18;
    private Integer[] cardsArray= {1, 2, 3, 4, 5, 6, 7, 8, 9,
                                    10, 11, 12, 13, 14, 15, 16, 17, 18};
    private int imgMonkey1, imgPanda1, imgParrot1, imgBat1, imgBear1, imgButterfly1, imgDolphin1, imgFlamingo1, imgFox1,
                    imgMonkey2, imgPanda2, imgParrot2, imgBat2, imgBear2, imgButterfly2, imgDolphin2, imgFlamingo2, imgFox2;
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
        setContentView(R.layout.activity_start_memory_hard);

        timerText = findViewById(R.id.timerMemoryHard);
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
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn10 = findViewById(R.id.btn10);
        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        btn13 = findViewById(R.id.btn13);
        btn14 = findViewById(R.id.btn14);
        btn15 = findViewById(R.id.btn15);
        btn16 = findViewById(R.id.btn16);
        btn17 = findViewById(R.id.btn17);
        btn18 = findViewById(R.id.btn18);
    }

    private void setTagForButton(){
        btn1.setTag("0");
        btn2.setTag("1");
        btn3.setTag("2");
        btn4.setTag("3");
        btn5.setTag("4");
        btn6.setTag("5");
        btn7.setTag("6");
        btn8.setTag("7");
        btn9.setTag("8");
        btn10.setTag("9");
        btn11.setTag("10");
        btn12.setTag("11");
        btn13.setTag("12");
        btn14.setTag("13");
        btn15.setTag("14");
        btn16.setTag("15");
        btn17.setTag("16");
        btn18.setTag("17");
    }

    private void frontOfCardResource() {
        imgMonkey1 = R.drawable.monkey;
        imgPanda1 = R.drawable.panda;
        imgParrot1 = R.drawable.parrot;
        imgBat1 = R.drawable.bat;
        imgBear1 = R.drawable.bear;
        imgButterfly1 = R.drawable.butterfly;
        imgDolphin1 = R.drawable.dolphin;
        imgFlamingo1 = R.drawable.flamingo;
        imgFox1 = R.drawable.fox;
        imgMonkey2 = R.drawable.monkey;
        imgPanda2 = R.drawable.panda;
        imgParrot2 = R.drawable.parrot;
        imgBat2 = R.drawable.bat;
        imgBear2 = R.drawable.bear;
        imgButterfly2 = R.drawable.butterfly;
        imgDolphin2 = R.drawable.dolphin;
        imgFlamingo2 = R.drawable.flamingo;
        imgFox2 = R.drawable.fox;
    }

    private void buttonsSetOnClick(){
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
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn9, theCard);
            }
        });

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn10, theCard);
            }
        });
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn11, theCard);
            }
        });
        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn12, theCard);
            }
        });
        btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn13, theCard);
            }
        });
        btn14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn14, theCard);
            }
        });
        btn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn15, theCard);
            }
        });
        btn16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn16, theCard);
            }
        });
        btn17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn17, theCard);
            }
        });
        btn18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                duStuff(btn18, theCard);
            }
        });
    }

    private void duStuff(Button btn, int theCard) {

        if(cardsArray[theCard] == 0){
            btn.setBackgroundResource(imgMonkey1);
        }else if(cardsArray[theCard] == 1) {
            btn.setBackgroundResource(imgPanda1);
        }else if(cardsArray[theCard] == 2) {
            btn.setBackgroundResource(imgParrot1);
        }else if(cardsArray[theCard] == 3) {
            btn.setBackgroundResource(imgBat1);
        }else if(cardsArray[theCard] == 4) {
            btn.setBackgroundResource(imgBear1);
        }else if(cardsArray[theCard] == 5) {
            btn.setBackgroundResource(imgButterfly1);
        }else if(cardsArray[theCard] == 6) {
            btn.setBackgroundResource(imgDolphin1);
        }else if(cardsArray[theCard] == 7) {
            btn.setBackgroundResource(imgFlamingo1);
        }else if(cardsArray[theCard] == 8) {
            btn.setBackgroundResource(imgFox1);
        }else if(cardsArray[theCard] == 9) {
            btn.setBackgroundResource(imgMonkey2);
        }else if(cardsArray[theCard] == 10) {
            btn.setBackgroundResource(imgPanda2);
        }else if(cardsArray[theCard] == 11) {
            btn.setBackgroundResource(imgParrot2);
        }else if(cardsArray[theCard] == 12) {
            btn.setBackgroundResource(imgBat2);
        }else if(cardsArray[theCard] == 13) {
            btn.setBackgroundResource(imgBear2);
        }else if(cardsArray[theCard] == 14) {
            btn.setBackgroundResource(imgButterfly2);
        }else if(cardsArray[theCard] == 15) {
            btn.setBackgroundResource(imgDolphin2);
        }else if(cardsArray[theCard] == 16) {
            btn.setBackgroundResource(imgFlamingo2);
        }else if(cardsArray[theCard] == 17) {
            btn.setBackgroundResource(imgFox2);
        }

        if(cardNumber == 1){
            firstCard = cardsArray[theCard];
            if(firstCard > 9)
                firstCard = firstCard - 9;
            cardNumber = 2;
            clickedFirst = theCard;

            btn.setEnabled(false);
        }else if(cardNumber == 2){
            secondCard = cardsArray[theCard];
            if(secondCard > 9)
                secondCard = secondCard - 9;
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
            btn9.setEnabled(false);
            btn10.setEnabled(false);
            btn11.setEnabled(false);
            btn12.setEnabled(false);
            btn13.setEnabled(false);
            btn14.setEnabled(false);
            btn15.setEnabled(false);
            btn16.setEnabled(false);
            btn17.setEnabled(false);
            btn18.setEnabled(false);

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
            }else if(clickedFirst == 8) {
                btn9.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 9) {
                btn10.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 10) {
                btn11.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 11) {
                btn12.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 12) {
                btn13.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 13) {
                btn14.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 14) {
                btn15.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 15) {
                btn16.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 16) {
                btn17.setVisibility(View.INVISIBLE);
            }else if(clickedFirst == 17) {
                btn18.setVisibility(View.INVISIBLE);
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
            }else if(clickedSecond == 8) {
                btn9.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 9) {
                btn10.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 10) {
                btn11.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 11) {
                btn12.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 12) {
                btn13.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 13) {
                btn14.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 14) {
                btn15.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 15) {
                btn16.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 16) {
                btn17.setVisibility(View.INVISIBLE);
            }else if(clickedSecond == 17) {
                btn18.setVisibility(View.INVISIBLE);
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
            btn9.setBackgroundResource(R.drawable.questionmark);
            btn10.setBackgroundResource(R.drawable.questionmark);
            btn11.setBackgroundResource(R.drawable.questionmark);
            btn12.setBackgroundResource(R.drawable.questionmark);
            btn13.setBackgroundResource(R.drawable.questionmark);
            btn14.setBackgroundResource(R.drawable.questionmark);
            btn15.setBackgroundResource(R.drawable.questionmark);
            btn16.setBackgroundResource(R.drawable.questionmark);
            btn17.setBackgroundResource(R.drawable.questionmark);
            btn18.setBackgroundResource(R.drawable.questionmark);
        }

        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn5.setEnabled(true);
        btn6.setEnabled(true);
        btn7.setEnabled(true);
        btn8.setEnabled(true);
        btn9.setEnabled(true);
        btn10.setEnabled(true);
        btn11.setEnabled(true);
        btn12.setEnabled(true);
        btn13.setEnabled(true);
        btn14.setEnabled(true);
        btn15.setEnabled(true);
        btn16.setEnabled(true);
        btn17.setEnabled(true);
        btn18.setEnabled(true);

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
                btn8.getVisibility() == View.INVISIBLE &&
                btn9.getVisibility() == View.INVISIBLE &&
                btn10.getVisibility() == View.INVISIBLE &&
                btn11.getVisibility() == View.INVISIBLE &&
                btn12.getVisibility() == View.INVISIBLE &&
                btn13.getVisibility() == View.INVISIBLE &&
                btn14.getVisibility() == View.INVISIBLE &&
                btn15.getVisibility() == View.INVISIBLE &&
                btn16.getVisibility() == View.INVISIBLE &&
                btn17.getVisibility() == View.INVISIBLE &&
                btn18.getVisibility() == View.INVISIBLE ){

            timerTask.cancel();

            AlertDialog.Builder alertDialog =  new AlertDialog.Builder(StartMemoryHardActivity.this);
            alertDialog.setMessage("You win, congrats!")
                       .setCancelable(false)
                       .setPositiveButton("Start new game", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Intent intent = new Intent(getApplicationContext(), StartMemoryHardActivity.class);
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