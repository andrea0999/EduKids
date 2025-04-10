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
import com.google.firebase.database.DatabaseReference;
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

import cg.edukids.R;
import cg.edukids.math.MathActivity;
import cg.edukids.math.model.UserProgress;
import cg.edukids.math.utils.CustomDrawingSurface;
import cg.edukids.math.utils.DatabaseManager;
import cg.edukids.math.utils.ProblemGenerator;

public class ProblemActivity extends AppCompatActivity {
    private ProblemGenerator problemGenerator;
    private String currentProblem;
    private DatabaseManager databaseManager;
    private String userId;
    private int Mathscore = 0;
    private int difficultyLevel;
    private String correctAnswer;
    private CustomDrawingSurface canvasView;
    private TextView problemText;
    private Button checkAnswerButton;
    private DigitalInkRecognizer recognizer;
    private final RemoteModelManager remoteModelManager = RemoteModelManager.getInstance();
    private DigitalInkRecognitionModel model;
    private TextView mathAnswer;
    private char forcedOperator = '\0';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseManager = new DatabaseManager(userId);
        DatabaseReference userProgressRef = databaseManager.getUserProgressReference();

        problemText = findViewById(R.id.problemText);
        checkAnswerButton = findViewById(R.id.checkAnswerButton);
        canvasView = findViewById(R.id.problemCanvas);
        mathAnswer = findViewById(R.id.mathAnswer);

        // Preluare operator din intent
        String operator = getIntent().getStringExtra("operator");
        if (operator != null && operator.length() == 1) {
            forcedOperator = operator.charAt(0);
        }

        try {
            initializeRecognition();
        } catch (MlKitException e) {
            throw new RuntimeException(e);
        }

        userProgressRef.get().addOnSuccessListener(dataSnapshot -> {
            if (dataSnapshot.exists()) {
                UserProgress progress = dataSnapshot.getValue(UserProgress.class);
                if (progress != null) {
                    Mathscore = progress.getScore();
                    difficultyLevel = progress.getDifficultyLevel();
                }
            } else {
                Mathscore = 0;
                difficultyLevel = 1;
            }
            problemGenerator = new ProblemGenerator(difficultyLevel);
            if (forcedOperator != '\0') {
                problemGenerator.setForcedOperator(forcedOperator);
            }
            generateNewProblem();
        });

        checkAnswerButton.setOnClickListener(v -> {
            Log.d("ButtonClick", "Check Answer button clicked");
            processCanvasInput();
        });
    }

    private void initializeRecognition() throws MlKitException {
        DigitalInkRecognitionModelIdentifier modelIdentifier = DigitalInkRecognitionModelIdentifier.fromLanguageTag("en-US");

        if (modelIdentifier != null) {
            model = DigitalInkRecognitionModel.builder(modelIdentifier).build();
            remoteModelManager.download(model, new DownloadConditions.Builder().build())
                    .addOnSuccessListener(unused -> {
                        Log.i("InkSample", "Model Downloaded Successfully.");
                        checkAnswerButton.setEnabled(true);
                    })
                    .addOnFailureListener(e -> {
                        Log.e("InkSample", "Model download failed: " + e);
                        Toast.makeText(this, "Failed to download model, please try later!", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Log.e("InkSample", "Model identifier is null.");
        }
    }

    private void generateNewProblem() {
        currentProblem = problemGenerator.generateProblem();
        correctAnswer = String.valueOf(problemGenerator.getCorrectAnswer(currentProblem));
        problemText.setText(currentProblem);
    }

    private void processCanvasInput() {
        mathAnswer.setText("");

        Ink thisInk = canvasView.getInk();

        if (thisInk.getStrokes().isEmpty()) {
            Log.e("Digital Ink Test", "No ink strokes detected, cannot recognize.");
            Toast.makeText(this, "Please write something before checking!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (model == null) {
            Log.e("Digital Ink Test", "Model has not been loaded yet.");
            Toast.makeText(this, "Please wait for the model to load and try again!", Toast.LENGTH_SHORT).show();
            return;
        }

        recognizer = DigitalInkRecognition.getClient(
                DigitalInkRecognizerOptions.builder(model).build()
        );

        recognizer.recognize(thisInk)
                .addOnSuccessListener(result -> {
                    if (result.getCandidates().isEmpty()) {
                        Log.e("Digital Ink Test", "No recognition candidates found.");
                        Toast.makeText(this, "No recognition found. Please try again!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    RecognitionCandidate bestCandidate = result.getCandidates().get(0);
                    String recognizedText = bestCandidate.getText().replaceAll("[^0-9]", "");

                    if (recognizedText.isEmpty()) {
                        Toast.makeText(this, "Please write numbers only!", Toast.LENGTH_SHORT).show();
                    } else {
                        mathAnswer.setText(recognizedText);
                        validateAnswer(recognizedText);
                    }

                    clearCanvas();
                    canvasView.setInk(Ink.builder().build());
                })
                .addOnFailureListener(e -> {
                    Log.e("Digital Ink Test", "Error during recognition: " + e.getMessage());
                    Toast.makeText(this, "Recognition failed, please try again!", Toast.LENGTH_SHORT).show();
                });
    }

    private void validateAnswer(String recognizedText) {
        if (recognizedText.equals(correctAnswer)) {
            Mathscore += 10;
            difficultyLevel += 1;
            Toast.makeText(this, "Correct! Your score is now: " + Mathscore, Toast.LENGTH_SHORT).show();

            UserProgress progress = new UserProgress(Mathscore, difficultyLevel);
            databaseManager.saveUserProgress(progress);
            generateNewProblem();
        } else {
            difficultyLevel = Math.max(1, difficultyLevel - 1);
            Toast.makeText(this, "Incorrect! Try again.", Toast.LENGTH_SHORT).show();

            UserProgress progress = new UserProgress(Mathscore, difficultyLevel);
            databaseManager.saveUserProgress(progress);
            // Păstrăm aceeași problemă
        }
    }

    private void clearCanvas() {
        canvasView.clearCanvas();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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


