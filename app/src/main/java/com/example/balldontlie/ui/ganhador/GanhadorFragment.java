package com.example.balldontlie.ui.ganhador;

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

public class GanhadorFragment extends Fragment {
    private ListView listViewGanhador;
    private ArrayAdapter adapterGanhador;
    private ArrayList<String> listaDadosGanhador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ganhador, container, false);
        listViewGanhador = root.findViewById(R.id.listViewGanhador);
        preencherLista();
        preencherAdapter();

        return root;
    }

    private void preencherLista() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dados", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dados_ganhador", "");
        listaDadosGanhador = gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());

    }

    private void preencherAdapter() {
        adapterGanhador = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, listaDadosGanhador);
        listViewGanhador.setAdapter(adapterGanhador);
    }
}
