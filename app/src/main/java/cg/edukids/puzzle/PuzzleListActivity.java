package cg.edukids.puzzle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

import cg.edukids.R;
import cg.edukids.puzzle.adapters.ImageAdapter;

public class PuzzleListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageAdapter adapter;
    public static int numberOfPieces = 0;
    public static String choose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_list);

        AlertDialog.Builder alertDialog =  new AlertDialog.Builder(PuzzleListActivity.this);
        View view = getLayoutInflater().inflate(R.layout.spinner_dialog, null);
        alertDialog.setTitle("Select puzzle mode");

        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PuzzleListActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.puzzleOptions));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        final Spinner spinner1 = (Spinner) view.findViewById(R.id.spinner1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 2) {
                    System.out.println("Choose 2D");
                    spinner1.setVisibility(View.GONE);
                    //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PuzzleListActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.puzzleOptions));
                    //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //spinner.setAdapter(arrayAdapter);
                }else{ spinner1.setVisibility(View.GONE);}

                if(position == 1){
                    spinner1.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(PuzzleListActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.numberOfPieces));
                    arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner1.setAdapter(arrayAdapter1);
                }
                    //spinner1.setVisibility(View.GONE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!spinner.getSelectedItem().toString().equalsIgnoreCase("Puzzle option…")){
                    //Toast.makeText(PuzzleListActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    if(spinner.getSelectedItem().toString().equalsIgnoreCase("Different: 2D")){
                        choose = spinner.getSelectedItem().toString();
                        System.out.println("choose: " + choose);
                        //Intent intent = new Intent(getApplicationContext(), Start2DActivity.class);
                        //startActivity(intent);
                    }else if(!spinner1.getSelectedItem().toString().equalsIgnoreCase("Number of Pieces")) {
                        //Toast.makeText(PuzzleListActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                        numberOfPieces = Integer.parseInt(spinner1.getSelectedItem().toString());
                        //choose = spinner1.getSelectedItem().toString();
                        System.out.println("choose: " + choose);

                    }
                }
                dialog.dismiss();
            }
        })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), PuzzleActivity.class);
                    startActivity(intent);
                }
            });
        alertDialog.setView(view);
        AlertDialog dialog = alertDialog.create();
        dialog.show();

        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycle_view_images_puzzle);
        System.out.println("puzzleList recyclerView" + recyclerView );
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImageAdapter(this);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home) {
            finish();
        }else if(id == R.id.close) {
            startActivity(new Intent(getApplicationContext(), PuzzleActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back,menu);
        return true;
    }

}


