package com.example.balldontlie.ui.resultado;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
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

public class ResultadoFragment extends Fragment {
    private ListView listViewResultado;
    private ArrayAdapter adapterResultado;
    private ArrayList<String> listaDadosResultado;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_resultado, container, false);
        listViewResultado = root.findViewById(R.id.listViewResultado);
        preencherLista();
        preencherAdapter();

        return root;
    }

    private void preencherLista() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dados", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dados_resultado", "");
        listaDadosResultado = gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());

    }

    private void preencherAdapter() {
        adapterResultado = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, listaDadosResultado);
        listViewResultado.setAdapter(adapterResultado);
    }
}
