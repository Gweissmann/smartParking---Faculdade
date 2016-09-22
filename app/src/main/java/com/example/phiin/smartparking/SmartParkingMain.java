package com.example.phiin.smartparking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import static com.example.phiin.smartparking.R.id.btnAjuda;
import static com.example.phiin.smartparking.R.id.btnProcurarVagas;
import static com.example.phiin.smartparking.R.id.btnVagas;

public class SmartParkingMain extends AppCompatActivity implements View.OnClickListener {

    private GoogleApiClient client;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.smart_parking);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Procurar Vagas");

            Button btnProcurarVaga = (Button) findViewById(btnProcurarVagas);
            Button btnVaga = (Button) findViewById(R.id.btnVagas);
            Button btnAjuda = (Button) findViewById(R.id.btnAjuda);
            TextView txtNumeroVagas = (TextView) findViewById(R.id.txtvNvagas);
    }

        public void onClick(View v) {
            // Perform action on click
            switch (v.getId()) {
                case btnProcurarVagas:
                    Intent procurarvaga = new Intent(SmartParkingMain.this, ProcurarVaga.class);
                    startActivity(procurarvaga);

                    break;
                case btnVagas:
                    Intent vagas = new Intent(SmartParkingMain.this, Vagas.class);
                    startActivity(vagas);

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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SmartParkingMain Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.phiin.smartparking/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SmartParkingMain Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.phiin.smartparking/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


}
