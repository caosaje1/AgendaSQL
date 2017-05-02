package com.bernardo.agendasql;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import java.util.HashMap;
import static com.bernardo.agendasql.R.id.eCorreo;
import static com.bernardo.agendasql.R.id.eNombre;

public class MainActivity extends AppCompatActivity {

 

        EditText eNombre, eCorreo, eTelefono;
        String nombre, correo, telefono;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            eNombre = (EditText) findViewById(R.id.eNombre);
            eCorreo = (EditText) findViewById(R.id.eCorreo);
            eTelefono = (EditText) findViewById(R.id.eTelefono);

        };

    public void onClick(View view) {
        int id = view.getId();

        nombre = eNombre.getText().toString();
        correo = eCorreo.getText().toString();
        telefono = eTelefono.getText().toString();

        switch(id){
            case R.id.bGuardar:
                addContact();
                limpiar();
                break;
            case R.id.bBuscar:
                showContact();
                break;
            case R.id.bModificar:
                updateContact();
                limpiar();
                break;
            case R.id.bEliminar:
                deleteContact();
                limpiar();
                break;
        }
    }

    private void showContact() {
        class ShowContact extends AsyncTask<Void, Void, String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Show...","Wait...",false,false);
            }

            @Override
            protected String doInBackground(Void... v) {
                RequestHandler rh = new RequestHandler();
                String res = rh.sendGetRequestParam(Config.URL_GET_CONTACT, nombre);
                return res;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showData(s);
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }
        ShowContact ae = new ShowContact();
        ae.execute();
    }

    private void showData(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONObject c = result.getJSONObject(0);
            String telefonoJ = c.getString("telefono");
            String correo = c.getString("correo");
            eTelefono.setText(telefonoJ);
            eCorreo.setText(correo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void deleteContact() {
        class DeleteContact extends AsyncTask<Void, Void, String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Delete...","Wait...",false,false);
            }

            @Override
            protected String doInBackground(Void... v) {
                RequestHandler rh = new RequestHandler();
                String res = rh.sendGetRequestParam(Config.URL_DELETE, nombre);
                return res;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }
        DeleteContact ae = new DeleteContact();
        ae.execute();
    }

    private void updateContact() {
        class UpdateContact extends AsyncTask<Void, Void, String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("nombre",nombre);
                params.put("telefono",telefono);
                params.put("correo",correo);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_UPDATE, params);
                return res;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }
        UpdateContact ae = new UpdateContact();
        ae.execute();
    }

    private void limpiar() {
        eNombre.setText("");
        eCorreo.setText("");
        eTelefono.setText("");
    }

    private void addContact() {
        class AddContact extends AsyncTask<Void, Void, String>{

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Adding...","Wait...",false,false);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put("nombre",nombre);
                params.put("telefono",telefono);
                params.put("correo",correo);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_ADD, params);
                return res;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }
        AddContact ae = new AddContact();
        ae.execute();
    }

}
