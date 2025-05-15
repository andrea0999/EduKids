package cg.edukids.labyrinth.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import cg.edukids.R;

public class Player {
    private float x, y;
    private Maze maze;

    public Player(float x, float y, Maze maze) {
        this.x = x;
        this.y = y;
        this.maze = maze;
    }

    public void move(float dx, float dy) {
        float nextX = x + dx;
        float nextY = y + dy;

        if (!maze.isWall((int)(nextX + 0.45f), (int)y) &&
                !maze.isWall((int)(nextX - 0.45f), (int)y)) {
            x = nextX;
        }

        if (!maze.isWall((int)x, (int)(nextY + 0.45f)) &&
                !maze.isWall((int)x, (int)(nextY - 0.45f))) {
            y = nextY;
        }


        Log.d("Player", "updated x=" + x + " y=" + y);
    }

    public void draw(Canvas canvas, Bitmap bitmap, int cellSize, int offsetX, int offsetY) {
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setAntiAlias(true);

        float centerX = offsetX + (x + 0.5f) * cellSize;
        float centerY = offsetY + (y + 0.5f) * cellSize;
        float radius = cellSize * 0.45f;

        canvas.drawCircle(centerX, centerY, radius, paint);
    }



    public float getX() { return x; }
    public float getY() { return y; }
    public void setPosition(float x, float y) { this.x = x; this.y = y; }
}
