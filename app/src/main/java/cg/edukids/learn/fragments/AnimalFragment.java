package cg.edukids.learn.fragments;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import cg.edukids.R;
import cg.edukids.learn.adapter.AnimalAdapter;

public class AnimalFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    int animal[] = {R.drawable.flamingo, R.drawable.fox, R.drawable.dolphin, R.drawable.dog, R.drawable.cat, R.drawable.bat, R.drawable.bear};
    private MediaPlayer mp = new MediaPlayer();

    private static final int REQ_CODE = 100;
    private TextView ed1;
    private static final int REQUEST_AUDIO_PERMISSION = 200;
    private int score = 0; // Scorul total de potriviri
    private HashMap<String, Integer> animalSounds = new HashMap<>();
    private String mParam1;
    private String mParam2;

    public AnimalFragment() { }
    public static AnimalFragment newInstance(String param1, String param2) {
        AnimalFragment fragment = new AnimalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_animal, container, false);
        ImageView animalImage = view.findViewById(R.id.animalImage);
        animalImage.setImageResource(animal[0]);

        RecyclerView recyclerView = view.findViewById(R.id.animalList);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        AnimalAdapter adapter = new AnimalAdapter(getContext(), animal, position -> {
            animalImage.setImageResource(animal[position]);

            //int mediaRes = getResources().getIdentifier(name, "raw", getPackageName());
            //if(mp.isPlaying())
            //    mp.stop();

            //mp = MediaPlayer.create(getContext(), mediaRes);
            //mp.start();
        });
        recyclerView.setAdapter(adapter);

        ed1 = view.findViewById(R.id.ed1);
        ImageView speak = view.findViewById(R.id.speaker);
        initializeAnimalSounds();

        speak.setOnClickListener(v -> checkAudioPermission());

        return view;
    }

    private void checkAudioPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{android.Manifest.permission.RECORD_AUDIO}, REQUEST_AUDIO_PERMISSION);
        } else {
            startSpeechRecognition();
        }
    }

    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need To Speak");

        try {
            startActivityForResult(intent, REQ_CODE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "Sorry, your device is not supported", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSpeechRecognition();
            } else {
                Toast.makeText(getContext(), "Microphone permission is required for speech recognition", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String recognizedText = result.get(0).toLowerCase().trim(); // Normalizează textul
                ed1.setText(recognizedText);

                if (animalSounds.containsKey(recognizedText)) {
                    score++; // Crește scorul dacă există o potrivire
                    showSuccessDialog(); // Afișează mesajul de succes
                }
            }
        }
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Congrats!")
                .setMessage("You matched an animal sound!\nScore: " + score)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void initializeAnimalSounds() {
        //animalSounds.put("flamingo", R.raw.flamingo);
        //animalSounds.put("fox", R.raw.fox);
        //animalSounds.put("dolphin", R.raw.dolphin);
        //animalSounds.put("dog", R.raw.dog);
        //animalSounds.put("cat", R.raw.cat);
        //animalSounds.put("bat", R.raw.bat);
        //animalSounds.put("bear", R.raw.bear);
        animalSounds.put("test", R.raw.test);
        animalSounds.put("bear", R.raw.b);
    }

   /* class ImageAdapter extends BaseAdapter{
        Context context;
        public ImageAdapter(Context context){
            this.context = context;
        }


        @Override
        public int getCount() {
            return animal.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView animalImage = new ImageView(context);
            animalImage.setImageResource(animal[position]);
            animalImage.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            return animalImage;
        }
    }*/
}