package cg.edukids.labyrinth.utils;

public class LevelManager {
    private int level = 0;

    public LevelManager() {}

    public LevelManager(int level) {
        this.level = level;
    }

    public Maze loadCurrentLevel() {
        return new MazeGenerator(15, 15).generate();
    }

    public Maze loadNextLevel() {
        level++;
        return loadCurrentLevel();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

