package cg.edukids.learn.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import cg.edukids.R;
import cg.edukids.learn.LearnActivity;
import cg.edukids.learn.adapter.MyAdapter;
import cg.edukids.learn.fragments.AlphabetFragment;
import cg.edukids.math.MathActivity;

public class StartLearnActivity extends AppCompatActivity {

    private String tabTitle[] = {"Alphabet", "Fruits", "Animals"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_learn);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), getLifecycle()));
        viewPager.setOffscreenPageLimit(3);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, true,
                (tab, position) -> {
                    tab.setText(tabTitle[position]);
                });
        tabLayoutMediator.attach();


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
