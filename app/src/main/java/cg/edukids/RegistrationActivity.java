package cg.edukids;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cg.edukids.drawing.StartDrawingActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText remail, rpassword, rpassword2;
    private Button registrationConfigBtn;
    FirebaseAuth firebaseAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reff = db.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        System.out.println("db: "+db + "reff: "+reff);

        remail = findViewById(R.id.email);
        String email = remail.getText().toString();
        rpassword = findViewById(R.id.password);
        rpassword2 = findViewById(R.id.password2);
        registrationConfigBtn = findViewById(R.id.registrationConfigBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        registrationConfigBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = remail.getText().toString().trim();
                String password = rpassword.getText().toString().trim();
                String password2 = rpassword2.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    remail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    rpassword.setError("Password is Required");
                    return;
                }

                if(TextUtils.isEmpty(password2)){
                    rpassword2.setError("Re-Entry Password is Required");
                    return;
                }
                if(password.length() < 6){
                    rpassword.setError("Password Must by >= 6 characters");
                }

                //register the user in firebase
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "User created", Toast.LENGTH_SHORT).show();
                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            System.out.println("curentUser: "+ currentFirebaseUser);
                            reff.child(currentFirebaseUser.getUid()).child("Email").setValue(email);
                            reff.child(currentFirebaseUser.getUid()).child("ImageURL").setValue("default");
                            reff.child(currentFirebaseUser.getUid()).child("Password").setValue(password);

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(RegistrationActivity.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.close){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}