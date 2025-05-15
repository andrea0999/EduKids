package cg.edukids.learn.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import cg.edukids.R;
import cg.edukids.learn.LearnActivity;
import cg.edukids.learn.adapter.MyAdapter;
import cg.edukids.learn.fragments.AlphabetFragment;
import cg.edukids.learn.fragments.AnimalFragment;
import cg.edukids.learn.fragments.FruitFragment;
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
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.close) {
            startActivity(new Intent(getApplicationContext(), LearnActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
