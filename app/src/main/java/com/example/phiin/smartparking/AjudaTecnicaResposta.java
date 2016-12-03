package com.example.phiin.smartparking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

public class AjudaTecnicaResposta extends AppCompatActivity {

    private ImageView imgchecked;
    private ImageView imgcancelar;
    private TextView txtPrimeiraRes;
    private TextView txtSegundoRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda_tecnica_resposta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtPrimeiraRes = (TextView)findViewById(R.id.txtresposta1);
        txtSegundoRes = (TextView)findViewById(R.id.txtresposta2);
        imgchecked = (ImageView)findViewById(R.id.Checked);
        imgcancelar = (ImageView)findViewById(R.id.Canceled);

        if(getIntent().getExtras().getBoolean("Visibility")){
          imgchecked.setVisibility(View.VISIBLE);
            imgcancelar.setVisibility(View.INVISIBLE);

        }
        else {
            imgchecked.setVisibility(View.INVISIBLE);
            imgcancelar.setVisibility(View.VISIBLE);
            txtPrimeiraRes.setVisibility(View.INVISIBLE);
            txtSegundoRes.setText("Pedido de ajuda cancelado");
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent aboutIntent = new Intent(this, ajuda.class);
            startActivity(aboutIntent);
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AjudaTecnica Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

}
