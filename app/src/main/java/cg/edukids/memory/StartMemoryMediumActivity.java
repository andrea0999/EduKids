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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import cg.edukids.HomeActivity;
import cg.edukids.R;

public class StartMemoryMediumActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12;
    private Integer[] cardsArray= {1, 2, 3, 4, 5, 6,
                                    7, 8, 9, 10, 11, 12};
    private int imgArt1, imgCat1, imgDog1, imgDolphin1, imgSpy1, imgTiger1,
            imgArt2, imgCat2, imgDog2, imgDolphin2, imgSpy2, imgTiger2;
    private int firstCard, secondCard;
    private int clickedFirst, clickedSecond;
    private int cardNumber = 1;
    private TextView timerText;
    private Timer timer;
    private TimerTask timerTask;
    private Double time = 0.0;
    private int attention, memory;
    private int getAttention, getMemory;
    private Calendar calendar = Calendar.getInstance();;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");;
    private String date = dateFormat.format(calendar.getTime());

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reff = db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_memory_medium);

        timerText = findViewById(R.id.timerMemoryMedium);
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
    }

    private void frontOfCardResource() {
        imgArt1 = R.drawable.art;
        imgCat1 = R.drawable.cat;
        imgDog1 = R.drawable.dog;
        imgDolphin1 = R.drawable.dolphin;
        imgSpy1 = R.drawable.spy;
        imgTiger1 = R.drawable.tiger;

        imgArt2 = R.drawable.art;
        imgCat2 = R.drawable.cat;
        imgDog2 = R.drawable.dog;
        imgDolphin2 = R.drawable.dolphin;
        imgSpy2 = R.drawable.spy;
        imgTiger2 = R.drawable.tiger;
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
    }

    private void duStuff(Button btn, int theCard) {

        if(cardsArray[theCard] == 0){
            btn.setBackgroundResource(imgArt1);
        }else if(cardsArray[theCard] == 1) {
            btn.setBackgroundResource(imgCat1);
        }else if(cardsArray[theCard] == 2) {
            btn.setBackgroundResource(imgDog1);
        }else if(cardsArray[theCard] == 3) {
            btn.setBackgroundResource(imgDolphin1);
        }else if(cardsArray[theCard] == 4) {
            btn.setBackgroundResource(imgSpy1);
        }else if(cardsArray[theCard] == 5) {
            btn.setBackgroundResource(imgTiger1);
        }else if(cardsArray[theCard] == 6) {
            btn.setBackgroundResource(imgArt2);
        }else if(cardsArray[theCard] == 7) {
            btn.setBackgroundResource(imgCat2);
        }else if(cardsArray[theCard] == 8) {
            btn.setBackgroundResource(imgDog2);
        }else if(cardsArray[theCard] == 9) {
            btn.setBackgroundResource(imgDolphin2);
        }else if(cardsArray[theCard] == 10) {
            btn.setBackgroundResource(imgSpy2);
        }else if(cardsArray[theCard] == 11) {
            btn.setBackgroundResource(imgTiger2);
        }

        if(cardNumber == 1){
            firstCard = cardsArray[theCard];
            if(firstCard > 6)
                firstCard = firstCard - 6;
            cardNumber = 2;
            clickedFirst = theCard;

            btn.setEnabled(false);
        }else if(cardNumber == 2){
            secondCard = cardsArray[theCard];
            if(secondCard > 6)
                secondCard = secondCard - 6;
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
                btn12.getVisibility() == View.INVISIBLE ){

            timerTask.cancel();
            String getTimer = (String) timerText.getText();
            System.out.println(getTimer);
            String seconds = getTimer.substring(10,12);
            System.out.println(seconds);
            int sec = Integer.parseInt(seconds);
            System.out.println(sec);

            String minute = getTimer.substring(5,7);
            int min = Integer.parseInt(minute);

            if( sec <= 45 && min == 0){
                attention = 7;
                memory = 7;
            }else if( (min == 0 && sec > 45) && (min == 1 && sec <= 15)){
                attention = 3;
                memory = 3;
            }else {
                attention = 1;
                memory = 1;
            }

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String x = date.substring(0,2);
            String y = date.substring(3,5);
            String z = date.substring(6,10);
            System.out.println(x + " " + y + " " + z);
            String total = x + y + z;

            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    getAttention = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Scor").child(total).child("attention").getValue(Integer.class);
                    getMemory = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Scor").child(total).child("memory").getValue(Integer.class);
                    System.out.println("getAttention: " + getAttention);

                    if(getAttention < attention){
                        System.out.println("if condition");
                        reff.child(currentFirebaseUser.getUid()).child("Scor").child(total).child("attention").setValue(attention);
                    }
                    if(getMemory < memory){
                        reff.child(currentFirebaseUser.getUid()).child("Scor").child(total).child("memory").setValue(memory);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    AlertDialog.Builder alertDialog =  new AlertDialog.Builder(StartMemoryMediumActivity.this);
                    alertDialog.setTitle("You win, congrats!")
                            .setMessage("Attention: " + attention +
                                    "\nMemory: " + memory)
                            .setCancelable(false)
                            .setPositiveButton("Start new game", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getApplicationContext(), StartMemoryMediumActivity.class);
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
            }, 500);

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