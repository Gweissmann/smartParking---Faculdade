package com.example.phiin.smartparking;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Gu on 14/10/2016.
 */

public  class Vagas extends AppCompatActivity {

    private TextView txtJson;
    private String filmeName = new String();
    private String ano = new String();
    private String finalMovies = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vaga);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    //Método Json **********************************************************************************

        Button btnJson = (Button)findViewById(R.id.btnJson);
        txtJson = (TextView)findViewById(R.id.txtJson);

        btnJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute("http://jsonparsing.parseapp.com/jsonData/moviesDemoList.txt");
            }
        });
    }

    public class JSONTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader( new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                List<Object> jsonLine = new ArrayList<>();

                while((line = reader.readLine()) != null){
                    jsonLine.add(line);
                }

                Object jsonList = "";

                for ( Object  o : jsonLine) {
                    jsonList += o + "\t";
                }



                String finalJson = jsonList.toString();

                Log.d("finalJson", finalJson);

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("movies");


                for(int i = 0; i< parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    filmeName = finalObject.getString("movie");
                    ano = finalObject.getString("year");

                    finalMovies += filmeName + " - " + ano + "\n";
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null){
                    connection.disconnect();
                }
                try{
                    if (reader != null) {
                        reader.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

                return finalMovies;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            txtJson.setText(result);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}


