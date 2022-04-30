package cg.edukids.puzzle;

import static cg.edukids.puzzle.adapters.ImageAdapter.getSelectPicture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

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

    private static ArrayList<Drawable> pieceArray = new ArrayList();
    private static Bitmap bitmap;
    private static Drawable pDrawable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start2_dactivity);

        bitmap = BitmapFactory.decodeResource(getResources(), getSelectPicture);
        System.out.println("bitmap Start2D: " + bitmap);

        init();

        scramble();

        setDimensions();

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

        for (int i = tileList.length - 1; i > 0; i--) {
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
               pieceArray.add(pDrawable);
           }
       }
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

    private static void swap(Context context, int currentPosition, int swap) {
        int newPosition = tileList[currentPosition + swap];
        tileList[currentPosition + swap] = tileList[currentPosition];
        tileList[currentPosition] = newPosition;
        display(context);

        if (isSolved() == 1) Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
    }

    public static void moveTiles(Context context, String direction, int position) {

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
        int x = 0;
        System.out.println("-------------------");
        for (int i = 0; i < tileList.length; i++) {
            System.out.println("isSolved: " + tileList[i] + " " + i);
            x ++;
            System.out.println("x: " + x);
            if (tileList[i] == i) {
                 k = 1;
            }else {
                k = 0;
                break;
            }
        }

        return k;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back,menu);
        return true;
    }
}