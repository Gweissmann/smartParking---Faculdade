package com.example.phiin.smartparking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Gu on 14/10/2016.
 */

public class Vagas extends AppCompatActivity {

    private Timer autoUpdate;

    boolean conectado;

    private Integer sensor1;
    private Integer sensor2;
    private Integer sensor3;

    private String valorList = new String();
    private String valorFinal;

    private ImageView car_verde;
    private ImageView car_vermelho;
    private ImageView car_amarelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verificaConexao();

        setContentView(R.layout.activity_main_vaga);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        car_verde = (ImageView) findViewById(R.id.car_verde);
        car_amarelo = (ImageView) findViewById(R.id.car_amarelo);
        car_vermelho = (ImageView) findViewById(R.id.car_vermelho);

        car_verde.setVisibility(View.INVISIBLE);
        car_amarelo.setVisibility(View.INVISIBLE);
        car_vermelho.setVisibility(View.INVISIBLE);

    }

    //Classe que atualiza activity automaticamente a cada 5 segundos; ******************************
    @Override
    public void onResume() {
        super.onResume();

        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                            new JSONTask().execute("http://192.168.0.150");
                    }
                });
            }
        }, 0, 3000); // updates each 40 secs

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            new JSONTask().cancel(true);
        }
        return super.onOptionsItemSelected(item);
    }


    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                int codigoResposta;
                InputStream stream;
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                List<Object> jsonLine = new ArrayList<>();

                while ((line = reader.readLine()) != null) {
                    jsonLine.add(line);
                }

                Object jsonList = "";

                for (Object o : jsonLine) {
                    jsonList += o + "\t";
                }

                String finalJson = jsonList.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("sensores");


                valorList = "";

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    String valor1 = new String();
                    String valor2 = new String();
                    String valor3 = new String();

                    valor1 = finalObject.getString("sensor1");
                    //Log.d("valor1", valor1);
                    valor2 = finalObject.getString("sensor2");
                    // Log.d("valor2", valor2);
                    valor3 = finalObject.getString("sensor3");
                    // Log.d("valor3", valor3);

                    valorList = valor1 + valor2 + valor3;
                    // Log.d("valorList", valorList);

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return valorList;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            valorFinal = result;

            sensor1 = Integer.parseInt(valorFinal.substring(0, 1));
            sensor2 = Integer.parseInt(valorFinal.substring(1, 2));
            sensor3 = Integer.parseInt(valorFinal.substring(2));

            verificaVagaEstacionada();
        }
    }

    public boolean verificaConexao() {
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    public void verificaVagaEstacionada() {

        if (sensor1 == 0 && sensor2 == 0 && sensor3 == 0) {
            car_verde.setVisibility(View.INVISIBLE);
            car_amarelo.setVisibility(View.INVISIBLE);
            car_vermelho.setVisibility(View.INVISIBLE);

        }

        if (sensor1 == 1 && sensor2 == 0 && sensor3 == 0) {
            car_verde.setVisibility(View.VISIBLE);
            car_amarelo.setVisibility(View.INVISIBLE);
            car_vermelho.setVisibility(View.INVISIBLE);

        }

        if (sensor1 == 0 && sensor2 == 1 && sensor3 == 0) {
            car_amarelo.setVisibility(View.VISIBLE);
            car_verde.setVisibility(View.INVISIBLE);
            car_vermelho.setVisibility(View.INVISIBLE);

        }

        if (sensor1 == 0 && sensor2 == 0 && sensor3 == 1) {
            car_vermelho.setVisibility(View.VISIBLE);
            car_verde.setVisibility(View.INVISIBLE);
            car_amarelo.setVisibility(View.INVISIBLE);

        }

        if (sensor1 == 1 && sensor2 == 1 && sensor3 == 0) {
            car_verde.setVisibility(View.VISIBLE);
            car_amarelo.setVisibility(View.VISIBLE);
            car_vermelho.setVisibility(View.INVISIBLE);

        }

        if (sensor1 == 1 && sensor2 == 0 && sensor3 == 1) {
            car_verde.setVisibility(View.VISIBLE);
            car_vermelho.setVisibility(View.VISIBLE);
            car_amarelo.setVisibility(View.INVISIBLE);

        }
        if (sensor1 == 0 && sensor2 == 1 && sensor3 == 1) {
            car_vermelho.setVisibility(View.VISIBLE);
            car_amarelo.setVisibility(View.VISIBLE);
            car_verde.setVisibility(View.INVISIBLE);

        }
        if (sensor1 == 1 && sensor2 == 1 && sensor3 == 1) {
            car_verde.setVisibility(View.VISIBLE);
            car_vermelho.setVisibility(View.VISIBLE);
            car_amarelo.setVisibility(View.VISIBLE);

        }
    }

}




