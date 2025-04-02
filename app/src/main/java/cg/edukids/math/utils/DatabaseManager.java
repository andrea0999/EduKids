package cg.edukids.math.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cg.edukids.math.model.UserProgress;

public class DatabaseManager {
    private DatabaseReference databaseReference;

    public DatabaseManager(String userId) {
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
    }

    public void saveUserProgress(UserProgress progress) {
        databaseReference.setValue(progress);
    }

    public DatabaseReference getUserProgressReference() {
        return databaseReference;
    }
}
