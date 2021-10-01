package com.example.balldontlie.ui.descricao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.balldontlie.R;

public class DescricaoFragment extends Fragment {
    private ListView listViewDescricao;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_descricao, container, false);
        root.findViewById(R.id.listViewDescricao);
        listViewDescricao = root.findViewById(R.id.listViewDescricao);

//        preencherLista();
//        preencherAdapter();

        return root;
    }
}
