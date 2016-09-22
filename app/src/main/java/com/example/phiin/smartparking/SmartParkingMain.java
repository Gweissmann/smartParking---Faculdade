package com.example.phiin.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

import static com.example.phiin.smartparking.R.id.btnAjuda;
import static com.example.phiin.smartparking.R.id.btnProcurarVagas;
import static com.example.phiin.smartparking.R.id.btnVagas;

public class SmartParkingMain extends AppCompatActivity implements View.OnClickListener {

    private GoogleApiClient client;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.smart_parking);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar();

            Button btnVaga = (Button) findViewById(R.id.btnVagas);
            Button Ajuda = (Button) findViewById(R.id.btnAjuda);
            TextView txtNumeroVagas = (TextView) findViewById(R.id.txtvNvagas);


        }

        public void onClick(View v) {
            switch (v.getId()) {
                case btnProcurarVagas :
                    Intent procurarvaga = new Intent(SmartParkingMain.this,ProcurarVagas.class);
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
}