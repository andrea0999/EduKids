package cg.edukids.learn.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.vision.digitalink.DigitalInkRecognition;
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModel;
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModelIdentifier;
import com.google.mlkit.vision.digitalink.DigitalInkRecognizer;
import com.google.mlkit.vision.digitalink.DigitalInkRecognizerOptions;
import com.google.mlkit.vision.digitalink.Ink;
import com.google.mlkit.vision.digitalink.RecognitionCandidate;

import cg.edukids.R;
import cg.edukids.learn.fragments.AlphabetFragment;
import cg.edukids.learn.utils.CustomDrawingSurfaceAlphabet;

public class AlphabetActivity extends AppCompatActivity {

    private CustomDrawingSurfaceAlphabet canvasView;
    private Button checkAlphabetButton;
    private DigitalInkRecognizer recognizer;
    private final RemoteModelManager remoteModelManager = RemoteModelManager.getInstance();
    private DigitalInkRecognitionModel model;
    private String expectedLetter = "A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        canvasView = findViewById(R.id.alphabetCanvas);
        checkAlphabetButton = findViewById(R.id.checkAlphabetButton);

        String name = getIntent().getStringExtra("name").toLowerCase();
        expectedLetter = name.substring(0, 1);

        ImageView alphabetImage = findViewById(R.id.alphabetImage);
        int imgRes = getResources().getIdentifier(name, "drawable", getPackageName());
        alphabetImage.setImageResource(imgRes);

        int mediaRes = getResources().getIdentifier(name, "raw", getPackageName());
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), mediaRes);
        mp.start();

        try {
            initializeRecognition();
        } catch (MlKitException e) {
            throw new RuntimeException(e);
        }

        checkAlphabetButton.setOnClickListener(v -> processCanvasInput());
    }

    private void initializeRecognition() throws MlKitException {
        DigitalInkRecognitionModelIdentifier modelIdentifier = DigitalInkRecognitionModelIdentifier.fromLanguageTag("en-US");

        if (modelIdentifier != null) {
            model = DigitalInkRecognitionModel.builder(modelIdentifier).build();
            remoteModelManager.download(model, new DownloadConditions.Builder().build())
                    .addOnSuccessListener(unused -> {
                        Log.i("AlphabetInk", "Model downloaded successfully.");
                        checkAlphabetButton.setEnabled(true);
                    })
                    .addOnFailureListener(e -> {
                        Log.e("AlphabetInk", "Model download failed: " + e);
                        Toast.makeText(this, "Model download failed.", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void processCanvasInput() {
        Ink thisInk = canvasView.getInk();

        if (thisInk.getStrokes().isEmpty()) {
            Toast.makeText(this, "Please write a letter first!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (model == null) {
            Toast.makeText(this, "Model not loaded yet.", Toast.LENGTH_SHORT).show();
            return;
        }

        recognizer = DigitalInkRecognition.getClient(
                DigitalInkRecognizerOptions.builder(model).build()
        );

        recognizer.recognize(thisInk)
                .addOnSuccessListener(result -> {
                    if (result.getCandidates().isEmpty()) {
                        Toast.makeText(this, "No recognition result.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    RecognitionCandidate bestCandidate = result.getCandidates().get(0);
                    String recognizedText = bestCandidate.getText().replaceAll("[^A-Za-z]", "");

                    if (!recognizedText.isEmpty()) {
                        recognizedText = recognizedText.substring(0, 1);
                    }

                    Log.i("AlphabetInk", "Recognized (filtered): " + recognizedText);

                    if (!recognizedText.isEmpty() && recognizedText.equalsIgnoreCase(expectedLetter)) {
                        showSuccessDialog();
                    } else {
                        Toast.makeText(this, "Try again! You wrote: " + recognizedText, Toast.LENGTH_SHORT).show();
                    }

                    canvasView.clearCanvas();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Recognition failed.", Toast.LENGTH_SHORT).show();
                    Log.e("AlphabetInk", "Error: " + e.getMessage());
                });
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Congrats!")
                .setMessage("You wrote the correct letter!")
                .setPositiveButton("OK", (dialog, which) -> {
                    startActivity(new Intent(this, AlphabetFragment.class));
                })
                .setCancelable(false)
                .show();
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
            startActivity(new Intent(getApplicationContext(), StartLearnActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
