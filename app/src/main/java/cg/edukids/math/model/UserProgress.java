package cg.edukids.math.model;

public class UserProgress {

    private int score;
    private int difficultyLevel;

    public UserProgress() {
        // Constructor gol necesar pentru Firebase
    }

    public UserProgress(int score, int difficultyLevel) {
        this.score = score;
        this.difficultyLevel = difficultyLevel;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
