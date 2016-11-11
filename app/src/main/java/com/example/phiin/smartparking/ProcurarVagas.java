package com.example.phiin.smartparking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

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

import static com.example.phiin.smartparking.R.id.btnBack;
import static com.example.phiin.smartparking.R.id.btnCancelar;
import static com.example.phiin.smartparking.R.id.btnCheck;

public class ProcurarVagas extends AppCompatActivity {

    private ImageView pin1;
    private ImageView pin2;
    private ImageView pin3;

    private String pegaVaga;
    private String compVaga = "semVaga";

    private String vaga1 = "vaga1";
    private String vaga2 = "vaga2";
    private String vaga3 = "vaga3";

    private Timer autoUpdatee;

    private Integer sensor1;
    private Integer sensor2;
    private Integer sensor3;

    private String valorListi = new String();
    private String valorFinale;

    private ImageView carro_verde;
    private ImageView carro_vermelho;
    private ImageView carro_amarelo;


    private SharedPreferences save;
    private SharedPreferences.Editor editor;

    public void verificaPin() {

        pin1 = (ImageView) findViewById(R.id.marker_pin1);
        pin2 = (ImageView) findViewById(R.id.marker_pin2);
        pin3 = (ImageView) findViewById(R.id.marker_pin3);

        save = getSharedPreferences("var",
                Context.MODE_PRIVATE);
        pegaVaga = save.getString("var", null);

        if (pegaVaga.equals(compVaga)) {
            pin1.setVisibility(View.INVISIBLE);
            pin2.setVisibility(View.INVISIBLE);
            pin3.setVisibility(View.INVISIBLE);

        } else {

            if (pegaVaga.equals(vaga1)) {
                pin1.setVisibility(View.VISIBLE);
                pin2.setVisibility(View.INVISIBLE);
                pin3.setVisibility(View.INVISIBLE);
            }
            if (pegaVaga.equals(vaga2)) {
                pin1.setVisibility(View.INVISIBLE);
                pin2.setVisibility(View.VISIBLE);
                pin3.setVisibility(View.INVISIBLE);
            }
            if (pegaVaga.equals(vaga3)) {
                pin1.setVisibility(View.INVISIBLE);
                pin2.setVisibility(View.INVISIBLE);
                pin3.setVisibility(View.VISIBLE);
            }
        }
    }

    public void verificaVagaEstacionada() {

        if(sensor1 == 0 && sensor2 == 0 && sensor3 == 0){
            carro_verde.setVisibility(View.INVISIBLE);
            carro_amarelo.setVisibility(View.INVISIBLE);
            carro_vermelho.setVisibility(View.INVISIBLE);
        }


        if (sensor1 == 1 && sensor2 == 0 && sensor3 == 0) {
            carro_verde.setVisibility(View.VISIBLE);
            carro_amarelo.setVisibility(View.INVISIBLE);
            carro_vermelho.setVisibility(View.INVISIBLE);
        }

        if (sensor1 == 0 && sensor2 == 1 && sensor3 == 0) {
            carro_amarelo.setVisibility(View.VISIBLE);
            carro_verde.setVisibility(View.INVISIBLE);
            carro_vermelho.setVisibility(View.INVISIBLE);
        }

        if (sensor1 == 0 && sensor2 == 0 && sensor3 == 1) {
            carro_vermelho.setVisibility(View.VISIBLE);
            carro_verde.setVisibility(View.INVISIBLE);
            carro_amarelo.setVisibility(View.INVISIBLE);
        }

        if (sensor1 == 1 && sensor2 == 1 && sensor3 == 0) {
            carro_verde.setVisibility(View.VISIBLE);
            carro_amarelo.setVisibility(View.VISIBLE);
            carro_vermelho.setVisibility(View.INVISIBLE);
        }

        if (sensor1 == 1 && sensor2 == 0 && sensor3 == 1) {
            carro_verde.setVisibility(View.VISIBLE);
            carro_vermelho.setVisibility(View.VISIBLE);
            carro_amarelo.setVisibility(View.INVISIBLE);

        }
        if (sensor1 == 0 && sensor2 == 1 && sensor3 == 1) {
            carro_vermelho.setVisibility(View.VISIBLE);
            carro_amarelo.setVisibility(View.VISIBLE);
            carro_verde.setVisibility(View.INVISIBLE);

        }
        if (sensor1 == 1 && sensor2 == 1 && sensor3 == 1) {
            carro_verde.setVisibility(View.VISIBLE);
            carro_vermelho.setVisibility(View.VISIBLE);
            carro_amarelo.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocurar_vaga);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //adiciona seta de voltar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        verificaPin();

        carro_verde = (ImageView) findViewById(R.id.carro_verde);
        carro_amarelo = (ImageView) findViewById(R.id.carro_amarelo);
        carro_vermelho = (ImageView) findViewById(R.id.carro_vermelho);

        carro_verde.setVisibility(View.INVISIBLE);
        carro_amarelo.setVisibility(View.INVISIBLE);
        carro_vermelho.setVisibility(View.INVISIBLE);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case btnBack:
                Intent check = new Intent(this, SmartParkingMain.class);
                startActivity(check);
                finish();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        autoUpdatee = new Timer();
        autoUpdatee.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        new JSONTask().execute("http://192.168.0.150");
                    }
                });
            }
        }, 0, 2000); // 2 segundos

        verificaPin();
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

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


                valorListi = "";

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    String valor1 = new String();
                    String valor2 = new String();
                    String valor3 = new String();

                    valor1 = finalObject.getString("sensor1");
                    valor2 = finalObject.getString("sensor2");
                    valor3 = finalObject.getString("sensor3");

                    valorListi = valor1 + valor2 + valor3;
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

                return valorListi;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            valorFinale = result;
            Log.d("valorFinale",valorFinale);

            sensor1 = Integer.parseInt(valorFinale.substring(0, 1));
            sensor2 = Integer.parseInt(valorFinale.substring(1, 2));
            sensor3 = Integer.parseInt(valorFinale.substring(2));

            verificaVagaEstacionada();
        }
    }
    //**********************************************************************************************


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        //Adicionar seta de voltar
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

