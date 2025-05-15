package cg.edukids.labyrinth.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import cg.edukids.R;
import cg.edukids.labyrinth.MazeActivity;
import cg.edukids.labyrinth.utils.LevelManager;
import cg.edukids.labyrinth.utils.Maze;
import cg.edukids.labyrinth.utils.Player;


public class GameView extends View {

    private Player player;
    private Maze maze;
    private LevelManager levelManager;
    private Bitmap wallBitmap, playerBitmap, flagBitmap;
    private MazeActivity activity;
    private int cellSize;
    private boolean initialized = false;
    private int offsetX = 0, offsetY = 0;
    private boolean gamePaused = false;

    public GameView(Context context, LevelManager levelManager) {
        super(context);
        this.activity = (MazeActivity) context;
        this.levelManager = levelManager;
        this.maze = levelManager.loadCurrentLevel();
        this.player = new Player(maze.getStartX(), maze.getStartY(), maze);
        this.initialized = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int mazeCols = maze.getCols();
        int mazeRows = maze.getRows();
        cellSize = Math.min(w / mazeCols, h / mazeRows);

        playerBitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.player),
                cellSize, cellSize, true
        );

        wallBitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.wall),
                cellSize, cellSize, true
        );

        flagBitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.flag),
                cellSize, cellSize, true
        );

        maze.setFlagBitmap(flagBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        Drawable bg = ContextCompat.getDrawable(getContext(), R.drawable.background2);
        if (bg != null) {
            bg.setBounds(0, 0, getWidth(), getHeight());
            bg.draw(canvas);
        }

        if (!initialized) return;

        int mazeCols = maze.getCols();
        int mazeRows = maze.getRows();
        cellSize = Math.min(canvas.getWidth() / mazeCols, canvas.getHeight() / mazeRows);

        offsetX = (canvas.getWidth() - (cellSize * mazeCols)) / 2;
        offsetY = (canvas.getHeight() - (cellSize * mazeRows)) / 2;

        maze.draw(canvas, wallBitmap, cellSize, offsetX, offsetY);
        player.draw(canvas, playerBitmap, cellSize, offsetX, offsetY);

        if (maze.isAtEnd(player.getX(), player.getY())) {
            activity.showVictoryDialog();
        }

        if (!gamePaused) {
            invalidate();
        }
    }

    public void setGamePaused(boolean paused) {
        this.gamePaused = paused;
    }

    public Player getPlayer() {
        return player;
    }

    public void loadNextLevel() {
        maze = levelManager.loadNextLevel();
        player = new Player(maze.getStartX(), maze.getStartY(), maze);

        if (flagBitmap != null) {
            maze.setFlagBitmap(flagBitmap);
        }
    }
}
