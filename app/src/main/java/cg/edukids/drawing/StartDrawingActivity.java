package cg.edukids.drawing;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cg.edukids.R;
import cg.edukids.drawing.adapters.ImageAdapter;

public class StartDrawingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_drawing);

        //initTollbar();
        
        initView();
    }

    private void initView() {

        recyclerView = findViewById(R.id.recycle_view_images);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImageAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    /*private void initTollbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int toolbar_item_color = ResourcesCompat.getColor(getResources(),R.color.blue, null);
        toolbar.setTitleTextColor(toolbar_item_color);
        toolbar.setSubtitleTextColor(toolbar_item_color);
        getSupportActionBar().setTitle("My draw");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}