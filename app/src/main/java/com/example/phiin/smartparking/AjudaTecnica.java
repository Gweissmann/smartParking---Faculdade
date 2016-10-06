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

import com.controle.fadeInFadeOut;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.example.phiin.smartparking.R.id.btnCancelar;
import static com.example.phiin.smartparking.R.id.btnCheck;

/**
 * Created by phiin on 21/09/2016.
 */

public class AjudaTecnica extends AppCompatActivity {


    private GoogleApiClient client;
    private ImageView checked;
    private ImageView cancelar;
    private TextView obrigado;
    private TextView resposta2;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda_tecnica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        final ImageView demoImage = (ImageView) findViewById(R.id.lampada);
        ImageView aceso = (ImageView)findViewById(R.id.aceso);
        int imagesToShow[] = {R.drawable.aceso};

        fadeInFadeOut.animate(demoImage, imagesToShow, 0, true);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }
    //terminar de ver a conexao extra entre activities

    public void onClick(View v) {
        checked = (ImageView)findViewById(R.id.Checked);
        cancelar = (ImageView)findViewById(R.id.Canceled);
        switch (v.getId()) {
            case btnCheck :
                checked.setVisibility(View.VISIBLE);
                cancelar.setVisibility(View.INVISIBLE);
                Intent check = new Intent(this,AjudaTecnicaResposta.class);
                break;
            case btnCancelar:
                checked.setVisibility(View.INVISIBLE);
                cancelar .setVisibility(View.VISIBLE);
                Intent cancelaract = new Intent(this,AjudaTecnicaResposta.class);
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}


