package com.example.phiin.smartparking;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

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

import static com.example.phiin.smartparking.R.id.btnAjuda;
import static com.example.phiin.smartparking.R.id.btnEstacionar;
import static com.example.phiin.smartparking.R.id.btnProcurarVagas;
import static com.example.phiin.smartparking.R.id.btnVagas;
import static com.example.phiin.smartparking.R.id.txtNvagas;
import static com.example.phiin.smartparking.R.id.txtvNumeroVagas;

public class SmartParkingMain extends AppCompatActivity implements View.OnClickListener {

    private String valorList = new String();
    private String valorFinal;


    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_parking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();

        Button btnVaga = (Button) findViewById(R.id.btnVagas);
        Button Ajuda = (Button) findViewById(R.id.btnAjuda);


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case btnProcurarVagas:
                Intent procurarvaga = new Intent(SmartParkingMain.this, ProcurarVagas.class);
                startActivity(procurarvaga);

                break;
            case btnVagas:
                Intent vagas = new Intent(SmartParkingMain.this, Vagas.class);
                startActivity(vagas);

                break;

            case btnEstacionar:
                Intent estacionar = new Intent(SmartParkingMain.this, Estacionar.class);
                startActivity(estacionar);

                break;

            case btnAjuda:
                Intent ajudatec = new Intent(SmartParkingMain.this, AjudaTecnica.class);
                startActivity(ajudatec);

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            InputStream stream = null;

            try {
                int codigoResposta;

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
                if(stream != null){
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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

            char letra = '0';
            char[] valorFinal = "valorFinal".toCharArray();
            int cont = 0;
            for (int i = 0; i < valorFinal.length; i++) {
                if (valorFinal[i] == letra) {
                    cont++;
                }
            }

            if (cont == 1) {
                TextView txtNumeroVagas = (TextView) findViewById(R.id.txtNvagas);
                txtNumeroVagas.setText("1");
            }
            if (cont == 2) {
                TextView txtNumeroVagas = (TextView) findViewById(R.id.txtNvagas);
                txtNumeroVagas.setText("2");
            }
            if (cont == 3) {
                TextView txtNumeroVagas = (TextView) findViewById(R.id.txtNvagas);
                txtNumeroVagas.setText("3");
            }
        }
    }
}