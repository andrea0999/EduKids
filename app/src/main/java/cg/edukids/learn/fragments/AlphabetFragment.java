package cg.edukids.learn.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import cg.edukids.R;
import cg.edukids.learn.activity.AlphabetActivity;
import cg.edukids.learn.utils.localization.LocaleHelper;

public class AlphabetFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public AlphabetFragment() {
        // Required empty public constructor
    }

    public static AlphabetFragment newInstance(String param1, String param2) {
        AlphabetFragment fragment = new AlphabetFragment();
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
        View view = inflater.inflate(R.layout.fragment_alphabet, container, false);

        String[] alphabet = getResources().getStringArray(R.array.alphabet_array);

        GridView alphabetList = view.findViewById(R.id.alphabetList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_expandable_list_item_1, alphabet);
        alphabetList.setAdapter(adapter);
        alphabetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Deschide activitatea și trimite 'name'
                Intent i1 = new Intent(getContext(), AlphabetActivity.class);
                i1.putExtra("name", alphabet[position]);
                startActivity(i1);
            }
        });


        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String lang = prefs.getString("selected_lang", "en");
        super.onAttach(LocaleHelper.setLocale(context, lang));
    }

}