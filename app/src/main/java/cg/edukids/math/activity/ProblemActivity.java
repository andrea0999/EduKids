package cg.edukids.math.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.vision.digitalink.DigitalInkRecognition;
import com.google.mlkit.vision.digitalink.DigitalInkRecognizer;
import com.google.mlkit.vision.digitalink.DigitalInkRecognizerOptions;
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModel;
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModelIdentifier;
import com.google.mlkit.vision.digitalink.Ink;
import com.google.mlkit.vision.digitalink.RecognitionCandidate;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cg.edukids.R;
import cg.edukids.math.MathActivity;
import cg.edukids.math.utils.CustomDrawingSurface;
import cg.edukids.math.utils.DatabaseManager;
import cg.edukids.math.utils.ProblemGenerator;

public class ProblemActivity extends AppCompatActivity {
    private ProblemGenerator problemGenerator;
    private String currentProblem;
    private String userId;
    private int Mathscore = 0;
    private String correctAnswer;
    private CustomDrawingSurface canvasView;
    private TextView problemText;
    private Button checkAnswerButton;
    private DigitalInkRecognizer recognizer;
    private final RemoteModelManager remoteModelManager = RemoteModelManager.getInstance();
    private DigitalInkRecognitionModel model;
    private TextView mathAnswer;
    private char forcedOperator = '\0';
    private String todayDateKey;

    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) return;
        userId = currentUser.getUid();

        String date = new SimpleDateFormat("MMddyyyy").format(Calendar.getInstance().getTime());
        todayDateKey = date;

        databaseManager = new DatabaseManager(userId);

        problemText = findViewById(R.id.problemText);
        checkAnswerButton = findViewById(R.id.checkAnswerButton);
        canvasView = findViewById(R.id.problemCanvas);
        mathAnswer = findViewById(R.id.mathAnswer);

        String operator = getIntent().getStringExtra("operator");
        if (operator != null && operator.length() == 1) {
            forcedOperator = operator.charAt(0);
        }

        try {
            initializeRecognition();
        } catch (MlKitException e) {
            throw new RuntimeException(e);
        }

        databaseManager.getMathscoreReference(todayDateKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Mathscore = snapshot.exists() ? snapshot.getValue(Integer.class) : 0;
                problemGenerator = new ProblemGenerator();
                if (forcedOperator != '\0') {
                    problemGenerator.setForcedOperator(forcedOperator);
                }
                generateNewProblem();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProblemActivity.this, "Failed to load score.", Toast.LENGTH_SHORT).show();
            }
        });

        checkAnswerButton.setOnClickListener(v -> processCanvasInput());
    }

    private void initializeRecognition() throws MlKitException {
        DigitalInkRecognitionModelIdentifier modelIdentifier = DigitalInkRecognitionModelIdentifier.fromLanguageTag("en-US");
        if (modelIdentifier != null) {
            model = DigitalInkRecognitionModel.builder(modelIdentifier).build();
            remoteModelManager.download(model, new DownloadConditions.Builder().build())
                    .addOnSuccessListener(unused -> checkAnswerButton.setEnabled(true))
                    .addOnFailureListener(e -> Toast.makeText(this, "Model download failed.", Toast.LENGTH_SHORT).show());
        }
    }

    private void generateNewProblem() {
        currentProblem = problemGenerator.generateProblem();
        correctAnswer = String.valueOf(problemGenerator.getCorrectAnswer(currentProblem));
        problemText.setText(currentProblem);
    }

    private void processCanvasInput() {
        mathAnswer.setText("");
        Ink ink = canvasView.getInk();

        if (ink.getStrokes().isEmpty()) {
            Toast.makeText(this, "Write something first!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (model == null) {
            Toast.makeText(this, "Model not loaded yet!", Toast.LENGTH_SHORT).show();
            return;
        }

        recognizer = DigitalInkRecognition.getClient(DigitalInkRecognizerOptions.builder(model).build());

        recognizer.recognize(ink)
                .addOnSuccessListener(result -> {
                    if (result.getCandidates().isEmpty()) {
                        Toast.makeText(this, "No result found!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String recognizedText = result.getCandidates().get(0).getText().replaceAll("[^0-9]", "");
                    if (!recognizedText.isEmpty()) {
                        mathAnswer.setText(recognizedText);
                        validateAnswer(recognizedText);
                    }

                    clearCanvas();
                    canvasView.setInk(Ink.builder().build());
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Recognition failed.", Toast.LENGTH_SHORT).show());
    }

    private void validateAnswer(String recognizedText) {
        if (recognizedText.equals(correctAnswer)) {
            Mathscore += 10;
            Toast.makeText(this, "Correct! Score: " + Mathscore, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect! Try again.", Toast.LENGTH_SHORT).show();
        }

        databaseManager.updateMathscore(Mathscore, todayDateKey);
        generateNewProblem();
    }

    private void clearCanvas() {
        canvasView.clearCanvas();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.close) {
            startActivity(new Intent(getApplicationContext(), MathActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
