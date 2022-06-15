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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cg.edukids.drawing.StartDrawingActivity;
import cg.edukids.ludo.LudoGameActivity;
import cg.edukids.profile.ProfileActivity;

public class HomeActivity extends AppCompatActivity {

    private Button drawingNextBtn, drawingBtn, checkProgressBtn;
    private int attention = 0, memory = 0, patience = 0;
    private Calendar calendar = Calendar.getInstance();;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");;
    private String date = dateFormat.format(calendar.getTime());;

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
                startActivity(new Intent(getApplicationContext(), LudoGameActivity.class));
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

        String x = date.substring(0,2);
        String y = date.substring(3,5);
        String z = date.substring(6,10);
        System.out.println(x + " " + y + " " + z);
        String total = x + y + z;
        System.out.println(total);

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (!snapshot.hasChild("attention")) {
                    System.out.println("test child");
                    reff.child(currentFirebaseUser.getUid()).child("Scor").child(total).child("attention").setValue(attention);
                }
                if (!snapshot.hasChild("memory")) {
                    reff.child(currentFirebaseUser.getUid()).child("Scor").child(total).child("memory").setValue(memory);
                }
                if (!snapshot.hasChild("patience")) {
                    reff.child(currentFirebaseUser.getUid()).child("Scor").child(total).child("patience").setValue(patience);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}