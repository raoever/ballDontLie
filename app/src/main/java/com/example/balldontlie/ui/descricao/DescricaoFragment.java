package com.example.balldontlie.ui.descricao;

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

public class DescricaoFragment extends Fragment {
    private ListView listViewDescricao;
    private ArrayAdapter adapterDescricao;
    private ArrayList<String> listaDadosDescricao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_descricao, container, false);
        root.findViewById(R.id.listViewDescricao);
        listViewDescricao = root.findViewById(R.id.listViewDescricao);
        preencherLista();
        preencherAdapter();

        return root;
    }

    private void preencherLista() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dados", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("dados_descricao", "");
        listaDadosDescricao = gson.fromJson(json, new TypeToken<ArrayList<String>>(){}.getType());

    }

    private void preencherAdapter() {
        adapterDescricao = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, listaDadosDescricao);
        listViewDescricao.setAdapter(adapterDescricao);
    }
}
