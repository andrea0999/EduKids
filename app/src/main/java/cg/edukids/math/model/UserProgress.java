package cg.edukids.math.model;

public class UserProgress {
    private int Mathscore;

    public UserProgress() {
        // Constructor gol necesar pentru Firebase
    }

    public UserProgress(int Mathscore) {
        this.Mathscore = Mathscore;
    }

    public int getMathscore() {
        return Mathscore;
    }

    public void setMathscore(int Mathscore) {
        this.Mathscore = Mathscore;
    }
}
