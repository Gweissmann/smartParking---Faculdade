package com.example.phiin.smartparking;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

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


    private SharedPreferences save;
    private SharedPreferences.Editor editor;

    public void verificaPin() {

        pin1 = (ImageView) findViewById(R.id.marker_pin1);
        pin2 = (ImageView) findViewById(R.id.marker_pin2);
        pin3 = (ImageView) findViewById(R.id.marker_pin3);


        save = getSharedPreferences("var",
                Context.MODE_PRIVATE);
        pegaVaga = save.getString("var", null);

        Log.d("pegavaga",pegaVaga);

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
        verificaPin();
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificaPin();
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

