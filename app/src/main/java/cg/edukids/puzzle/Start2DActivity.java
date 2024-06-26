package cg.edukids.puzzle;

import static cg.edukids.puzzle.adapters.ImageAdapter.getSelectPicture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cg.edukids.R;
import cg.edukids.puzzle.adapters.CustomAdapter;



public class Start2DActivity extends AppCompatActivity {

 private static GestureDetectGridView mGridView;

    private static final int COLUMNS = 3;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;

    private static int mColumnWidth, mColumnHeight;

    public static final String up = "up";
    public static final String down = "down";
    public static final String left = "left";
    public static final String right = "right";

    private static Integer[] tileList;
    private static Integer[] initList;

    private  static ArrayList<Drawable> pieceArray = new ArrayList();
    private  static Bitmap bitmap;
    private  static Drawable pDrawable;

    private static TextView timerText;
    private static TimerTask timerTask;
    private Timer timer;
    private Double time = 0.0;

    private  int attention = 0;
    private  int patience = 0;
    private  int getAttention;
    private  int getPatience;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");;
    private  String date = dateFormat.format(calendar.getTime());

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private  DatabaseReference reff = db.getReference();
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start2_dactivity);
        context = this;

        bitmap = BitmapFactory.decodeResource(getResources(), getSelectPicture);
        System.out.println("bitmap Start2D: " + bitmap);
        timer = new Timer();

        init();

        scramble();

        setDimensions();

        //startTimer();

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
        System.out.println("timerTask: " + timerTask);
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

    private void init() {
        mGridView =  (GestureDetectGridView) findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);

        tileList = new Integer[DIMENSIONS];
        for (int i = 0; i < DIMENSIONS; i++) {
            tileList[i] = i;
            System.out.println("start2d init tileList: "+tileList[i]);
        }
    }

    private void scramble() {
        int index;
        int temp;
        Random random = new Random();

        for (int i = tileList.length - 1; i >= 0; i--) {
            index = random.nextInt(i + 1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;
        }
    }

    private void setDimensions() {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusbarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;

                mColumnWidth = displayWidth / COLUMNS; //bitmap.getWidth()/COLUMNS;
                mColumnHeight = requiredHeight / COLUMNS; //bitmap.getHeight()/COLUMNS;
                System.out.println(mColumnWidth + " " + mColumnHeight);

                splitImage(bitmap,3,3);
                display(getApplicationContext());

            }
        });
    }

   private void splitImage(Bitmap bitmapi, int xCount, int yCount) {
        //int z = 0;
       // Allocate a two dimensional array to hold the individual images.
       Bitmap[][] bitmaps = new Bitmap[xCount][yCount];
       int width, height;
       // Divide the original bitmap width by the desired vertical column count
       width = bitmapi.getWidth() / xCount;
       // Divide the original bitmap height by the desired horizontal row count
       height = bitmapi.getHeight() / yCount;
       // Loop the array and create bitmaps for each coordinate
       for(int x = 0; x < xCount; x++) {
           for(int y = 0; y < yCount; y++) {
               // Create the sliced bitmap
               bitmaps[x][y] = Bitmap.createBitmap(bitmapi, x * width, y * height, width, height);
               System.out.println("bitmaps[][]: " + bitmaps[x][y]);
               pDrawable = new BitmapDrawable(getResources(), bitmaps[x][y]);
               System.out.println("pDrawable: " + pDrawable);
               pieceArray.add(pDrawable);

               //initList = new Integer[DIMENSIONS];

               /*if(x == 0) {
                   initList[z] = x + y;
                   System.out.println("z: " + z);
                   System.out.println("initList[" + z + "]: " + initList[z]);
                   z = x + y;
               }else if(x == 1){
                   initList[z] = x + y +2;
                   System.out.println("z: " + z);
                   System.out.println("initList[" + z + "]: " + initList[z]);
                   z = x + y +2;
               }else if(x == 2){
                   initList[z] = x + y +4;
                   System.out.println("z: " + z);
                   System.out.println("initList[" + z + "]: " + initList[z]);
                   z = x + y +4;
               }*/
           }
       }/*
       initList = new Integer[DIMENSIONS];
       for (int i = 0; i < DIMENSIONS; i++) {
           initList[i] = i;
           //System.out.println("start2d init initList: "+ initList[i]);
       }*/
       initList = new Integer[DIMENSIONS];
       initList[0] = 0; //ok
       initList[1] = 3; //ok
       initList[2] = 6; //ok
       initList[3] = 1; //ok
       initList[4] = 4; //ok
       initList[5] = 7; //ok
       initList[6] = 2; //ok
       initList[7] = 5; //ok
       initList[8] = 8; //ok
   }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }


    private static void display(Context context) { //static
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        for (int i = 0; i < tileList.length; i++) {
            button = new Button(context);

            if (tileList[i] == 0)
                button.setBackground(pieceArray.get(0));
                //button.setBackgroundResource(pieceArray.indexOf(i));//R.drawable.monkey);
            else if (tileList[i] == 1)
                button.setBackground(pieceArray.get(1));
                //button.setBackgroundResource(pieceArray.indexOf(i));//R.drawable.panda);
            else if (tileList[i] == 2)
                button.setBackground(pieceArray.get(2));
                //button.setBackgroundResource(pieceArray.indexOf(i));//R.drawable.butterfly);
            else if (tileList[i] == 3)
                button.setBackground(pieceArray.get(3));
                //button.setBackgroundResource(pieceArray.indexOf(i));//R.drawable.bear);
            else if (tileList[i] == 4)
                button.setBackground(pieceArray.get(4));
                //button.setBackgroundResource(pieceArray.indexOf(i));//R.drawable.binoculars);
            else if (tileList[i] == 5)
                button.setBackground(pieceArray.get(5));
                //button.setBackgroundResource(pieceArray.indexOf(i));//R.drawable.flamingo);
            else if (tileList[i] == 6)
                button.setBackground(pieceArray.get(6));
                //button.setBackgroundResource(pieceArray.indexOf(i));//R.drawable.art);
            else if (tileList[i] == 7)
                button.setBackground(pieceArray.get(7));
                //button.setBackgroundResource(pieceArray.indexOf(i));//R.drawable.dolphin);
            else if (tileList[i] == 8)
                button.setBackground(pieceArray.get(8));
                //button.setBackgroundResource(pieceArray.indexOf(i));//R.drawable.cat);

            buttons.add(button);
        }

        mGridView.setAdapter(new CustomAdapter(buttons, mColumnWidth, mColumnHeight));


    }

    private void swap(Context context, int currentPosition, int swap) {
        int newPosition = tileList[currentPosition + swap];
        tileList[currentPosition + swap] = tileList[currentPosition];
        tileList[currentPosition] = newPosition;
        display(context);

        if (isSolved() == 1) {
            timerTask.cancel();
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            System.out.println("timerText: " + timerText);
            String getTimer = (String) timerText.getText();
            System.out.println(getTimer);
            String seconds = getTimer.substring(10,12);
            System.out.println(seconds);
            int sec = Integer.parseInt(seconds);
            System.out.println(sec);

            String minute = getTimer.substring(5,7);
            int min = Integer.parseInt(minute);

            if( sec <= 30 && min <= 2){
                attention = 7;
                patience = 7;
            }else if( (min == 2 && sec > 30) && (min == 5 && sec == 0)){
                attention = 3;
                patience = 3;
            }else {
                attention = 1;
                patience = 1;
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
                    getPatience = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Scor").child(total).child("patience").getValue(Integer.class);
                    System.out.println("getAttention: " + getAttention);

                    if(getAttention < attention){
                        System.out.println("if condition");
                        reff.child(currentFirebaseUser.getUid()).child("Scor").child(total).child("attention").setValue(attention);
                    }
                    if(getPatience < patience){
                        reff.child(currentFirebaseUser.getUid()).child("Scor").child(total).child("patience").setValue(patience);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

            System.out.println("date: "+ date);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    AlertDialog.Builder alertDialog =  new AlertDialog.Builder(context);
                    alertDialog.setTitle("Congratulations, great job!")
                            .setMessage("Attention: " + attention +
                                    "\nPatience: " + patience)
                            .setCancelable(false)
                            .setPositiveButton("Start new game", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(context, PuzzleListActivity.class);
                                    context.startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(context, PuzzleActivity.class);
                                    context.startActivity(intent);
                                    finish();
                                }
                            });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }
            }, 1500);




        }
    }

    public  void moveTiles(Context context, String direction, int position) { // static
        // Upper-left-corner tile
        if (position == 0) {

            if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-center tiles
        } else if (position > 0 && position < COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-right-corner tile
        } else if (position == COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Left-side tiles
        } else if (position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS &&
                position % COLUMNS == 0) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Right-side AND bottom-right-corner tiles
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= DIMENSIONS - COLUMNS - 1) swap(context, position,
                        COLUMNS);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-left corner tile
        } else if (position == DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-center tiles
        } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Center tiles
        } else {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else swap(context, position, COLUMNS);
        }
    }

    private static int isSolved() {
        int k = 0;
        System.out.println("-------------------");
        for (int i = 0; i < DIMENSIONS; i++) {
            System.out.println("tileList: " + tileList[i]);
            System.out.println("initList: " + initList[i]);
            if (tileList[i] == initList[i]) {
                 k = 1;
            }else {
                k = 0;
                break;
            }
        }
        System.out.println("k: " + k);
        return k;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timer,menu);

        MenuItem timerItem = menu.findItem(R.id.timerPuzzle);
        System.out.println("timerItem P: " + timerItem);
        timerText = (TextView) MenuItemCompat.getActionView(timerItem);
        System.out.println("timerText P: " + timerText);
        startTimer();

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home) {
            finish();
        } else if(id == R.id.close) {
            startActivity(new Intent(getApplicationContext(), PuzzleListActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}