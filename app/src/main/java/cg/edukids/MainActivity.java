package cg.edukids;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText lemailLogin, lpasswordLogin;
    private Button loginBtn, registerBtn;
    private TextView resetLink;
    ImageButton facebookBtn, googleBtn;
    FirebaseAuth firebaseAuth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ScheduleExactAlarm")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        facebookBtn = findViewById(R.id.facebookBtn);
        facebookBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), FacebookActivity.class)));

        googleBtn = findViewById(R.id.googleBtn);
        googleBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), GoogleActivity.class)));

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegistrationActivity.class)));

        lemailLogin = findViewById(R.id.emailLogin);
        lpasswordLogin = findViewById(R.id.passwordLogin);

        loginBtn = findViewById(R.id.loginBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(v -> {
            String email = lemailLogin.getText().toString().trim();
            String password = lpasswordLogin.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                lemailLogin.setError("Email is Required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                lpasswordLogin.setError("Password is Required");
                return;
            }

            if (password.length() < 6) {
                lpasswordLogin.setError("Password Must be >= 6 characters");
            }

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        resetLink = findViewById(R.id.resetLink);
        resetLink.setOnClickListener(v -> {
            EditText resetPassword = new EditText(v.getContext());
            AlertDialog.Builder passwordReset = new AlertDialog.Builder(v.getContext());
            passwordReset.setTitle("Reset Password");
            passwordReset.setMessage("Enter your email address to receive the reset link");
            passwordReset.setView(resetPassword);

            passwordReset.setPositiveButton("Yes", (dialog, which) -> {
                String mail = resetPassword.getText().toString();
                firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(unused ->
                        Toast.makeText(MainActivity.this, "Reset Link sent to Your Email", Toast.LENGTH_SHORT).show()
                ).addOnFailureListener(e ->
                        Toast.makeText(MainActivity.this, "Error! Reset Link isn't sent " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            });

            passwordReset.setNegativeButton("No", (dialog, which) -> {});
            passwordReset.create().show();
        });

        createNotificationChannel();
        scheduleAlarm();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "memoChannel",
                    "EduKids memo Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void scheduleAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MemoBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // dacă ora a trecut, setează pentru ziua următoare
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), pendingIntent);
        }
    }
}
