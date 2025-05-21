package cg.edukids.learn.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import cg.edukids.R;
import cg.edukids.learn.activity.AlphabetActivity;
import cg.edukids.learn.activity.FruitActivity;
import cg.edukids.learn.utils.localization.LocaleHelper;

public class FruitFragment extends Fragment {
    private static String getEnglishName(String displayName) {
        switch (displayName.toLowerCase()) {
            case "măr":
                return "apple";
            case "banană":
                return "banana";
            case "struguri":
                return "grapes";
            case "portocală":
                return "orange";
            case "ananas":
                return "pineapple";
            case "căpșună":
                return "strawberry";
            default:
                return displayName.toLowerCase();
        }
    }
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FruitFragment() {}

    public static FruitFragment newInstance(String param1, String param2) {
        FruitFragment fragment = new FruitFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fruit, container, false);

        String[] fruit = getResources().getStringArray(R.array.fruit_array);
        ListView fruitList = v.findViewById(R.id.fruitsList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_expandable_list_item_1, fruit);
        fruitList.setAdapter(adapter);
        fruitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i1 = new Intent(getContext(), FruitActivity.class);
                i1.putExtra("name", getEnglishName(fruit[position]));
                startActivity(i1);
            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String lang = prefs.getString("selected_lang", "en");
        super.onAttach(LocaleHelper.setLocale(context, lang));
    }
}
