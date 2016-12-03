package com.example.phiin.smartparking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


/**
 * Created by Gu on 24/09/2016.
 */
public class Estacionar extends AppCompatActivity {

    private Button btnScan;
    private Button btnClear;
    private TextView txt_noEstacionar;
    private TextView txt_estacionar1;
    private TextView txt_estacionar2;
    private ImageView img_noEstacionar;
    private String vagaSalva;


    private SharedPreferences save;
    private SharedPreferences.Editor editor;


    // Método que verifica se já está com vaga salva************************************************

    public void verificaVaga() {

        String compVaga = "semVaga";

        img_noEstacionar = (ImageView) findViewById(R.id.img_noEstacionar);
        img_noEstacionar.setVisibility(View.INVISIBLE);

        txt_estacionar1 = (TextView) findViewById(R.id.txt_estacionar1);
        txt_estacionar1.setVisibility(View.INVISIBLE);

        txt_estacionar2 = (TextView) findViewById(R.id.txt_estacionar2);
        txt_estacionar2.setVisibility(View.INVISIBLE);

        txt_noEstacionar = (TextView) findViewById(R.id.txt_noEstacionar);
        txt_noEstacionar.setVisibility(View.INVISIBLE);

        btnScan = (Button) findViewById(R.id.btnScan);
        btnScan.setVisibility(View.INVISIBLE);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setVisibility(View.INVISIBLE);

        if (vagaSalva.equals(compVaga)) {
            img_noEstacionar = (ImageView) findViewById(R.id.img_noEstacionar);
            img_noEstacionar.setVisibility(View.VISIBLE);

            txt_noEstacionar = (TextView) findViewById(R.id.txt_noEstacionar);
            txt_noEstacionar.setVisibility(View.VISIBLE);

            btnScan = (Button) findViewById(R.id.btnScan);
            btnScan.setVisibility(View.VISIBLE);
        } else {
            txt_estacionar1 = (TextView) findViewById(R.id.txt_estacionar1);
            txt_estacionar1.setVisibility(View.VISIBLE);

            txt_estacionar2 = (TextView) findViewById(R.id.txt_estacionar2);
            txt_estacionar2.setVisibility(View.VISIBLE);

            btnClear = (Button) findViewById(R.id.btnClear);
            btnClear.setVisibility(View.VISIBLE);
        }

    }

    //***********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_estacionar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //adiciona seta de voltar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        save = getSharedPreferences("var",
                Context.MODE_PRIVATE);
        vagaSalva = save.getString("var", "semVaga");

        //Classe QrCode ****************************************************************************

        btnScan = (Button) findViewById(R.id.btnScan);
        final Activity activity = this;
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Coloque a Camera sobre o QRCode para Gravar");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor = save.edit();
                editor.putString("var", "semVaga");
                editor.commit();

                Intent intent = getIntent();
                finish();
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "Vaga Liberada!", Toast.LENGTH_LONG).show();
            }
        });

        verificaVaga();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Você cancelou o Scan", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Vaga salva com sucesso!", Toast.LENGTH_LONG).show();

                vagaSalva = result.getContents().toString();
                editor = save.edit();
                editor.putString("var", vagaSalva);
                editor.commit();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //**********************************************************************************************


    @Override
    protected void onResume() {
        super.onResume();
        save = getSharedPreferences("var",
                Context.MODE_PRIVATE);
        vagaSalva = save.getString("var", "semVaga");
        txt_estacionar2.setText(vagaSalva);
        verificaVaga();
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
            Intent aboutIntent = new Intent(this, ajuda.class);
            startActivity(aboutIntent);
        }
        //Adicionar seta de voltar
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}


