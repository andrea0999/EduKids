package cg.edukids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cg.edukids.drawing.StartDrawingActivity;
import cg.edukids.labyrinth.MazeGameActivity;
import cg.edukids.profile.ProfileActivity;

public class HomeActivity extends AppCompatActivity {

    private Button drawingNextBtn, drawingBtn, checkProgressBtn;
    private int attention = 0, memory = 0, patience = 0, Mathscore = 0;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
    private String dateKey = dateFormat.format(calendar.getTime());

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reff = db.getReference();
    private FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawingNextBtn = findViewById(R.id.drawingNextBtn);
        drawingNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MazeGameActivity.class));
            }
        });

        drawingBtn = findViewById(R.id.drawingBtn);
        drawingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StartDrawingActivity.class));
            }
        });

        checkProgressBtn = findViewById(R.id.checkProgressBtn);
        checkProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        DatabaseReference scorRef = reff.child(currentFirebaseUser.getUid()).child("Scor");

        scorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(dateKey)) {
                    // Este o nouă zi – inițializăm valorile la 0
                    DatabaseReference todayRef = scorRef.child(dateKey);
                    todayRef.child("attention").setValue(attention);
                    todayRef.child("memory").setValue(memory);
                    todayRef.child("patience").setValue(patience);
                    todayRef.child("Mathscore").setValue(Mathscore);
                }
                // Dacă ziua există deja, nu facem nimic — păstrăm scorurile existente
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
