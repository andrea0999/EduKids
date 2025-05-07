package cg.edukids.math.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseManager {
    private final DatabaseReference userRootRef;

    public DatabaseManager(String userId) {
        this.userRootRef = FirebaseDatabase.getInstance().getReference().child(userId);
    }

    public DatabaseReference getMathscoreReference(String dateKey) {
        return userRootRef.child("Scor").child(dateKey).child("Mathscore");
    }

    public void updateMathscore(int value, String dateKey) {
        userRootRef.child("Scor").child(dateKey).child("Mathscore").setValue(value);
    }
}
