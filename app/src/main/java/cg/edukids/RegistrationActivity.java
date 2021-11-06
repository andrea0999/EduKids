package cg.edukids;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText remail, rpassword, rpassword2;
    private Button registrationConfigBtn;
    FirebaseAuth firebaseAuth;
    //private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        remail = findViewById(R.id.email);
        rpassword = findViewById(R.id.password);
        rpassword2 = findViewById(R.id.password2);
        registrationConfigBtn = findViewById(R.id.registrationConfigBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        //progressBar = findViewById(R.id.progressBar);

        /*if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }*/

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

                //progressBar.setVisibility(View.VISIBLE);

                //register the user in firebase
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "User created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(RegistrationActivity.this,"Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}