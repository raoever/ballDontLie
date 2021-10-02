package com.example.balldontlie.ui.timeDaCasa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.balldontlie.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class TimeDaCasaFragment extends Fragment {
    private ListView listViewCasa;
    private ArrayAdapter adapterCasa;
    private ArrayList<String> listaDadosCasa;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_casa, container, false);
        listViewCasa = root.findViewById(R.id.listViewCasa);
        preencherLista();
        preencherAdapter();

        return root;
    }

    private void preencherLista() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dados", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dados_casa", "");
        listaDadosCasa = gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());

    }

    private void preencherAdapter() {
        adapterCasa = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, listaDadosCasa);
        listViewCasa.setAdapter(adapterCasa);
    }
}
