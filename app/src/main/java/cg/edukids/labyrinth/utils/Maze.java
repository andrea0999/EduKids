package cg.edukids.labyrinth.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Maze {
    private int[][] grid;
    private Bitmap flagBitmap; // steagul de final

    public Maze(int[][] grid) {
        this.grid = grid;
    }

    public void draw(Canvas canvas, Bitmap wallBitmap, int cellSize, int offsetX, int offsetY) {
        Paint wallPaint = new Paint();
        wallPaint.setColor(Color.parseColor("#228B22"));

        int endX = grid[0].length - 2;
        int endY = grid.length - 2;

        // Desenare pereți
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 1) {
                    int left = offsetX + x * cellSize;
                    int top = offsetY + y * cellSize;
                    int right = left + cellSize;
                    int bottom = top + cellSize;
                    canvas.drawRect(left, top, right, bottom, wallPaint);
                }
            }
        }

        // Desenează un steag la final
        if (flagBitmap != null) {
            int left = offsetX + endX * cellSize;
            int top = offsetY + endY * cellSize;
            Rect destRect = new Rect(left, top, left + cellSize, top + cellSize);
            canvas.drawBitmap(flagBitmap, null, destRect, null);
        }
    }

    public void setFlagBitmap(Bitmap flagBitmap) {
        this.flagBitmap = flagBitmap;
    }

    public int getRows() {
        return grid.length;
    }

    public int getCols() {
        return grid[0].length;
    }

    public int getStartX() { return 1; }
    public int getStartY() { return 1; }

    public boolean isAtEnd(float x, float y) {
        return (int)x == grid[0].length - 2 && (int)y == grid.length - 2;
    }

    public boolean isWall(int x, int y) {
        if (y < 0 || y >= grid.length || x < 0 || x >= grid[0].length) return true;
        return grid[y][x] == 1;
    }
}
