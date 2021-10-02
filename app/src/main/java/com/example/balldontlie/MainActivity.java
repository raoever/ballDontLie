package com.example.balldontlie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.balldontlie.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private List<Integer> numeros;
    private final String URL = "https://www.balldontlie.io/api/v1/games/";
    private JSONArray jogosArray;
    private JSONObject jsonObject;
    private ArrayList<JSONObject> listaJsonSorteados;
    private ArrayList<String> listaDadosDescricao;
    private ArrayList<String> listaDadosTimeDaCasa;
    private ArrayList<String> listaDadosTimeVisitante;
    private ArrayList<String> listaDadosResultado;
    private ArrayList<String> listaDadosGanhador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        numeros = new ArrayList<>();
        jogosArray = new JSONArray();
        listaJsonSorteados = new ArrayList<>();
        listaDadosDescricao = new ArrayList<>();
        listaDadosTimeDaCasa = new ArrayList<>();
        listaDadosTimeVisitante = new ArrayList<>();
        listaDadosResultado = new ArrayList<>();
        listaDadosGanhador = new ArrayList<>();

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_descricao, R.id.nav_time_da_casa, R.id.nav_time_visitante, R.id.nav_resultado, R.id.nav_ganhador )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Random random = new Random();
        while (numeros.size() <= 20) {
            Integer num = random.nextInt(24) + 1;
            if (!numeros.contains(num)) {
                numeros.add(num);
                Log.i("gerarNumeros: ", String.valueOf(num));
            }
        }
        new obterDados().execute();
    }

    private class obterDados extends
            AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Downloading: arquivo JSON", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Conexao conexao = new Conexao();
            InputStream inputStream = conexao.obterRespostaHTTP(URL);
            Auxilia auxilia = new Auxilia();
            String textJSON = auxilia.converter(inputStream);

            if (textJSON != null) {
                try {
                    jsonObject = new JSONObject(textJSON);
                    jogosArray = jsonObject.getJSONArray("data");
//                    for (int i = 0; i < jogosArray.length(); i++) {
//                        Log.i("onJogos1: ", String.valueOf(jogosArray.getJSONObject(i).getString("id")));
//                    }
                    for (int i = 0; i <= 20; i++) { //Sorteio
                        listaJsonSorteados.add((JSONObject) jogosArray.get(numeros.get(i)));
//                        Log.i("onJogos8: ", i+" for");
//                        Log.i("onJogos9: ", numeros.get(i).toString());
                        Log.i("onJogos10: ", String.valueOf(listaJsonSorteados.get(i))+i);
                    }

                    for (int i = 0; i < listaJsonSorteados.size(); i++) {
                        listaDadosDescricao.add("id: " + listaJsonSorteados.get(i).getString("id") + "\n" +
                                "data do jogo: " + listaJsonSorteados.get(i).getString("date") + "\n" +
                                "sigla time da casa: " + listaJsonSorteados.get(i).getJSONObject("home_team").getString("abbreviation") + "\n" +
                                "sigla time visitante: " + listaJsonSorteados.get(i).getJSONObject("visitor_team").getString("abbreviation"));
                        listaDadosTimeDaCasa.add("nome time da casa: " + listaJsonSorteados.get(i).getJSONObject("home_team").getString("full_name") + "\n" +
                                "cidade: " + listaJsonSorteados.get(i).getJSONObject("home_team").getString("city"));
                        listaDadosTimeVisitante.add("nome time da visitante: " + listaJsonSorteados.get(i).getJSONObject("visitor_team").getString("full_name") + "\n" +
                                "cidade: " + listaJsonSorteados.get(i).getJSONObject("visitor_team").getString("city"));
                        listaDadosResultado.add("pontos da casa: " + listaJsonSorteados.get(i).getString("home_team_score") + "\n" +
                                "time da casa: " + listaJsonSorteados.get(i).getJSONObject("home_team").getString("abbreviation") + "\n" +
                                "pontos visitantes: " + listaJsonSorteados.get(i).getString("visitor_team_score") + "\n" +
                                "time visitante: " + listaJsonSorteados.get(i).getJSONObject("visitor_team").getString("abbreviation"));
                        listaDadosGanhador.add("nome time da casa: " + listaJsonSorteados.get(i).getJSONObject("home_team").getString("full_name") + "\n" +
                                "nome time da visitante: " + listaJsonSorteados.get(i).getJSONObject("visitor_team").getString("full_name"));
                    }

                    SharedPreferences sharedPreferences = getSharedPreferences("dados", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(listaDadosDescricao);
                    editor.putString("dados_descricao", json);
                    json = gson.toJson(listaDadosTimeDaCasa);
                    editor.putString("dados_casa", json);
                    json = gson.toJson(listaDadosTimeVisitante);
                    editor.putString("dados_visitante", json);
                    json = gson.toJson(listaDadosResultado);
                    editor.putString("dados_resultado", json);
                    json = gson.toJson(listaDadosGanhador);
                    editor.putString("dados_ganhador", json);
                    editor.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "não foi possível obter JSON pelo servidor", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(MainActivity.this, MapsReitoria.class);
        if (item.getItemId() == R.id.action_settings)
            startActivity(i);
        return super.onOptionsItemSelected(item);
    }
}