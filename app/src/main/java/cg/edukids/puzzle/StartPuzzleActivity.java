package cg.edukids.puzzle;

import static java.lang.Math.abs;

import static cg.edukids.puzzle.PuzzleListActivity.numberOfPieces;
import static cg.edukids.puzzle.adapters.ImageAdapter.getSelectPicture;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cg.edukids.R;
import cg.edukids.memory.StartMemoryEasyActivity;
import cg.edukids.puzzle.adapters.ImageAdapter;
import cg.edukids.puzzle.puzzlePiece.PuzzlePiece;
import cg.edukids.puzzle.touchListener.TouchListener;

public class StartPuzzleActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView imageView;
    private RelativeLayout layout;
    ArrayList<PuzzlePiece> pieces;
    private TextView timerText;
    private Timer timer;
    private TimerTask timerTask;
    private Double time = 0.0;

    private int attention = 0, patience = 0;
    private int getAttention, getPatience;
    private Calendar calendar = Calendar.getInstance();;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");;
    private String date = dateFormat.format(calendar.getTime());

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reff = db.getReference();

    private SensorManager senSensorManager = null;
    private Sensor senAccelerometer;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;

    private int ok = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_puzzle);

        AlertDialog.Builder alertDialog =  new AlertDialog.Builder(StartPuzzleActivity.this);
        alertDialog.setTitle("Information")
                .setMessage("Pentru a aparea piesele amestecate te rog sa scuturi telefonul")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = alertDialog.create();
        dialog.show();

        layout = findViewById(R.id.layoutRelativePuzzle);
        imageView = findViewById(R.id.grid);

        //timerText = findViewById(R.id.timerPuzzle);
        timer = new Timer();
        //startTimer();

        // run image related code after the view was laid out
        // to have all dimensions calculated
        imageView.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("ResourceType")
            @Override
            public void run() {

                ImageAdapter imageAdapter = new ImageAdapter();
                System.out.println("StartPuzzleActivity selectImage: " + imageAdapter);
                imageView.setImageResource(getSelectPicture);

                pieces = splitImage();
                System.out.println("StartPuzzleActivity pieces: " + pieces + "" + pieces.get(1).getBackground());
            }
        });

        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) { //We will be using this method to detect the shake gesture
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;

                if (speed > SHAKE_THRESHOLD && ok != 1) {
                    ok = 1;
                    startTimer();
                    TouchListener touchListener = new TouchListener(StartPuzzleActivity.this);
                    // shuffle pieces order
                    Collections.shuffle(pieces);
                    for(PuzzlePiece piece : pieces) {
                        piece.setOnTouchListener(touchListener);
                        System.out.println("piece: " + piece.getBackground());
                        if(piece.getParent() != null) {
                            ((ViewGroup)piece.getParent()).removeView(piece); // <- fix
                        }

                        layout.addView(piece);
                        // randomize position, on the bottom of the screen
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) piece.getLayoutParams();
                        lParams.leftMargin = new Random().nextInt(layout.getWidth() - piece.pieceWidth);
                        lParams.topMargin = layout.getHeight() - piece.pieceHeight;
                        piece.setLayoutParams(lParams);
                    }
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    protected void onStop(){
        super.onStop();
        senSensorManager.unregisterListener(this);
    }
    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public ImageView getImageView(){
        return imageView;
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

    private ArrayList<PuzzlePiece> splitImage() {
        int piecesNumber = numberOfPieces;
        int rows = 0, cols = 0;
        System.out.println("startPuzzle piecesNumber: " + piecesNumber);

        if(piecesNumber == 12) {
             rows = 4;
             cols = 3;
        }else if(piecesNumber == 20){
             rows = 4;
             cols = 5;
        }else if(piecesNumber == 30){
             rows = 5;
             cols = 6;
        }

        //ImageView imageView = findViewById(R.id.grid);
        ArrayList<PuzzlePiece> pieces = new ArrayList<>(piecesNumber);

        // Get the bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        int[] dimensions = getBitmapPositionInsideImageView(imageView);
        int scaledBitmapLeft = dimensions[0];
        int scaledBitmapTop = dimensions[1];
        int scaledBitmapWidth = dimensions[2];
        int scaledBitmapHeight = dimensions[3];

        int croppedImageWidth = scaledBitmapWidth - 2 * abs(scaledBitmapLeft);
        int croppedImageHeight = scaledBitmapHeight - 2 * abs(scaledBitmapTop);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledBitmapWidth, scaledBitmapHeight, true);
        Bitmap croppedBitmap = Bitmap.createBitmap(scaledBitmap, abs(scaledBitmapLeft), abs(scaledBitmapTop), croppedImageWidth, croppedImageHeight);

        // Calculate the with and height of the pieces
        int pieceWidth = croppedImageWidth/cols;
        int pieceHeight = croppedImageHeight/rows;

        // Create each bitmap piece and add it to the resulting array
        int yCoord = 0;
        for (int row = 0; row < rows; row++) {
            int xCoord = 0;
            for (int col = 0; col < cols; col++) {
                // calculate offset for each piece
                int offsetX = 0;
                int offsetY = 0;
                if (col > 0) {
                    offsetX = pieceWidth / 3;
                }
                if (row > 0) {
                    offsetY = pieceHeight / 3;
                }
                //pieces.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, pieceWidth, pieceHeight));
                // apply the offset to each piece
                Bitmap pieceBitmap = Bitmap.createBitmap(croppedBitmap, xCoord - offsetX, yCoord - offsetY, pieceWidth + offsetX, pieceHeight + offsetY);
                PuzzlePiece piece = new PuzzlePiece(getApplicationContext());
                piece.setImageBitmap(pieceBitmap);
                piece.xCoord = xCoord - offsetX + imageView.getLeft();
                piece.yCoord = yCoord - offsetY + imageView.getTop();
                piece.pieceWidth = pieceWidth + offsetX;
                piece.pieceHeight = pieceHeight + offsetY;
                //pieces.add(piece);

                // this bitmap will hold our final puzzle piece image
                Bitmap puzzlePiece = Bitmap.createBitmap(pieceWidth + offsetX, pieceHeight + offsetY, Bitmap.Config.ARGB_8888);
                System.out.println("puzzlePiece: " + puzzlePiece);

                // draw path
                int bumpSize = pieceHeight / 4;
                Canvas canvas = new Canvas(puzzlePiece);
                Path path = new Path();
                path.moveTo(offsetX, offsetY);
                if (row == 0) {
                    // top side piece
                    path.lineTo(pieceBitmap.getWidth(), offsetY);
                } else {
                    // top bump
                    path.lineTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 3, offsetY);
                    path.cubicTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 6, offsetY - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 6 * 5, offsetY - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 3 * 2, offsetY);
                    path.lineTo(pieceBitmap.getWidth(), offsetY);
                }

                if (col == cols - 1) {
                    // right side piece
                    path.lineTo(pieceBitmap.getWidth(), pieceBitmap.getHeight());
                } else {
                    // right bump
                    path.lineTo(pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 3);
                    path.cubicTo(pieceBitmap.getWidth() - bumpSize,offsetY + (pieceBitmap.getHeight() - offsetY) / 6, pieceBitmap.getWidth() - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6 * 5, pieceBitmap.getWidth(), offsetY + (pieceBitmap.getHeight() - offsetY) / 3 * 2);
                    path.lineTo(pieceBitmap.getWidth(), pieceBitmap.getHeight());
                }

                if (row == rows - 1) {
                    // bottom side piece
                    path.lineTo(offsetX, pieceBitmap.getHeight());
                } else {
                    // bottom bump
                    path.lineTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 3 * 2, pieceBitmap.getHeight());
                    path.cubicTo(offsetX + (pieceBitmap.getWidth() - offsetX) / 6 * 5,pieceBitmap.getHeight() - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 6, pieceBitmap.getHeight() - bumpSize, offsetX + (pieceBitmap.getWidth() - offsetX) / 3, pieceBitmap.getHeight());
                    path.lineTo(offsetX, pieceBitmap.getHeight());
                }

                if (col == 0) {
                    // left side piece
                    path.close();
                } else {
                    // left bump
                    path.lineTo(offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 3 * 2);
                    path.cubicTo(offsetX - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6 * 5, offsetX - bumpSize, offsetY + (pieceBitmap.getHeight() - offsetY) / 6, offsetX, offsetY + (pieceBitmap.getHeight() - offsetY) / 3);
                    path.close();
                }

                // mask the piece
                Paint paint = new Paint();
                paint.setColor(0XFF000000);
                paint.setStyle(Paint.Style.FILL);

                canvas.drawPath(path, paint);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(pieceBitmap, 0, 0, paint);

                // draw a white border
                Paint border = new Paint();
                border.setColor(0X80FFFFFF);
                border.setStyle(Paint.Style.STROKE);
                border.setStrokeWidth(8.0f);
                canvas.drawPath(path, border);

                // draw a black border
                border = new Paint();
                border.setColor(0X80000000);
                border.setStyle(Paint.Style.STROKE);
                border.setStrokeWidth(3.0f);
                canvas.drawPath(path, border);

                // set the resulting bitmap to the piece
                piece.setImageBitmap(puzzlePiece);

                pieces.add(piece);
                xCoord += pieceWidth;
            }
            yCoord += pieceHeight;
        }

        return pieces;
    }

    private int[] getBitmapPositionInsideImageView(ImageView imageView) {
        int[] ret = new int[4];

        if (imageView == null || imageView.getDrawable() == null)
            return ret;

        // Get image dimensions
        // Get image matrix values and place them in an array
        float[] f = new float[9];
        imageView.getImageMatrix().getValues(f);

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        final Drawable d = imageView.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Calculate the actual dimensions
        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);

        ret[2] = actW;
        ret[3] = actH;

        // Get image position
        // We assume that the image is centered into ImageView
        int imgViewW = imageView.getWidth();
        int imgViewH = imageView.getHeight();

        int top = (int) (imgViewH - actH)/2;
        int left = (int) (imgViewW - actW)/2;

        ret[0] = left;
        ret[1] = top;

        return ret;
    }

    public void checkGameOver() {
        if (isGameOver()) {
            //finish();

            String getTimer = (String) timerText.getText();
            System.out.println(getTimer);
            String seconds = getTimer.substring(10,12);
            System.out.println(seconds);
            int sec = Integer.parseInt(seconds);
            System.out.println(sec);

            String minute = getTimer.substring(5,7);
            int min = Integer.parseInt(minute);

            if( sec <= 30){
                attention = 5;
                patience = 5;
            }else if(sec > 30 && sec <= 59){
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


            AlertDialog.Builder alertDialog =  new AlertDialog.Builder(StartPuzzleActivity.this);
            alertDialog.setTitle("Congratulations, great job!")
                    .setMessage("Attention: " + attention +
                            "\nPatience: " + patience)
                    .setCancelable(false)
                    .setPositiveButton("Start new game", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), StartPuzzleActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), PuzzleListActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

            AlertDialog dialog = alertDialog.create();
            dialog.show();

        }
    }

    private boolean isGameOver() {
        for (PuzzlePiece piece : pieces) {
            if (piece.canMove) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timer,menu);

        MenuItem timerItem = menu.findItem(R.id.timerPuzzle);
        timerText = (TextView) MenuItemCompat.getActionView(timerItem);
        //startTimer();

        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.close)
            startActivity(new Intent(getApplicationContext(), PuzzleListActivity.class));

        return super.onOptionsItemSelected(item);
    }

}