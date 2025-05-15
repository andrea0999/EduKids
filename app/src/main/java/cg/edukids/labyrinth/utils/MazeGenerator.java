package cg.edukids.labyrinth.utils;

import java.util.Random;

public class MazeGenerator {
    private final int rows, cols;
    private final int[][] maze;
    private final Random rand = new Random();

    public MazeGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maze = new int[rows][cols];
    }

    public Maze generate() {
        // Inițial toți pereții
        for (int y = 0; y < rows; y++)
            for (int x = 0; x < cols; x++)
                maze[y][x] = 1;

        dfs(1, 1);
        maze[1][1] = 0; // start
        maze[rows - 2][cols - 2] = 0; // end

        return new Maze(maze);
    }

    private void dfs(int x, int y) {
        int[] dx = { 0, 0, 2, -2 };
        int[] dy = { 2, -2, 0, 0 };
        int[] dirs = { 0, 1, 2, 3 };

        shuffle(dirs);

        for (int dir : dirs) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (isValid(nx, ny)) {
                maze[ny][nx] = 0;
                maze[y + dy[dir] / 2][x + dx[dir] / 2] = 0;
                dfs(nx, ny);
            }
        }
    }

    private boolean isValid(int x, int y) {
        return x > 0 && x < cols - 1 && y > 0 && y < rows - 1 && maze[y][x] == 1;
    }

    private void shuffle(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int r = rand.nextInt(array.length);
            int temp = array[i];
            array[i] = array[r];
            array[r] = temp;
        }
    }
}