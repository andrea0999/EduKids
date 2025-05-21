package cg.edukids.learn.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import cg.edukids.R;
import cg.edukids.drawing.StartDrawingActivity;
import cg.edukids.learn.LearnActivity;
import cg.edukids.learn.adapter.MyAdapter;
import cg.edukids.learn.fragments.AlphabetFragment;
import cg.edukids.learn.fragments.AnimalFragment;
import cg.edukids.learn.fragments.FruitFragment;
import cg.edukids.learn.utils.localization.LocaleHelper;
import cg.edukids.math.MathActivity;

public class StartLearnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_learn);

        Button btnAlphabet = findViewById(R.id.btn_alphabet);
        Button btnFruits = findViewById(R.id.btn_fruits);
        Button btnAnimals = findViewById(R.id.btn_animals);

        // Implicit, afișăm AlphabetFragment
        replaceFragment(new AlphabetFragment());

        btnAlphabet.setOnClickListener(v -> replaceFragment(new AlphabetFragment()));
        btnFruits.setOnClickListener(v -> replaceFragment(new FruitFragment()));
        btnAnimals.setOnClickListener(v -> replaceFragment(new AnimalFragment()));
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_language,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (item.getItemId() == R.id.RO) {
            editor.putString("selected_lang", "ro");
            editor.apply();
            recreate();
            return true;
        } else if (item.getItemId() == R.id.EN) {
            editor.putString("selected_lang", "en");
            editor.apply();
            recreate();
            return true;
        } else if(item.getItemId() == R.id.close) {
            startActivity(new Intent(getApplicationContext(), LearnActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences prefs = newBase.getSharedPreferences("prefs", MODE_PRIVATE);
        String lang = prefs.getString("selected_lang", "en");
        super.attachBaseContext(LocaleHelper.setLocale(newBase, lang));
    }
}
