package com.bernardo.agendasql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    EditText eNombre, eCorreo, eTelefono;
    String nombre, correo, telefono;
    ContentValues dataBD;

    //Button bGuardar, bBuscar, bEliminar, bActualiza;*/


    ContactosSQLiteHelper contactosSQLiteHelper;
    SQLiteDatabase dbContactos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactosSQLiteHelper = new ContactosSQLiteHelper(this, "ContactosDB", null,1);
        dbContactos = contactosSQLiteHelper.getWritableDatabase();

        eNombre = (EditText) findViewById(R.id.eNombre);
        eCorreo = (EditText) findViewById(R.id.eCorreo);
        eTelefono = (EditText) findViewById(R.id.eTelefono);
        /*bGuardar = (Button) findViewById(R.id.bGuardar);
        bBuscar = (Button) findViewById(R.id.bBuscar);
        bGuardar = (Button) findViewById(R.id.bGuardar);
        bGuardar = (Button) findViewById(R.id.bGuardar);*/


    }

    public void onClick(View view) {
        int id=view.getId();

        nombre=eNombre.getText().toString();
        correo=eCorreo.getText().toString();
        telefono=eTelefono.getText().toString();


        dataBD = new ContentValues();


        switch (id){
            case R.id.bGuardar:
                dataBD.put("nombre",nombre);
                dataBD.put("correo",correo);
                dataBD.put("telefono",telefono);
                dbContactos.insert("Contactos",null,dataBD);

                dbContactos.execSQL("INSERT INTO Contactos VALUES (null, '"+nombre+"', '"+telefono+"', '"+correo+"')"); //sintaxis sql

                break;
            case R.id.bBuscar:
                Cursor cursor= dbContactos.rawQuery("SELECT * FROM Contactos WHERE nombre='"+nombre+"'",null);

                if (cursor.moveToFirst()){
                    eTelefono.setText(cursor.getString(2));
                    eCorreo.setText(cursor.getString(3));
                }

                break;
            case R.id.bModificar:
                dataBD.put("correo",correo);
                dataBD.put("telefono",telefono);
                dbContactos.update("Contactos",dataBD,"nombre'"+nombre+"'",null);

                dbContactos.execSQL("UPDATE contactos SET telefono='"+telefono+"', correo='"+correo+"' WHERE nombre='"+nombre+"'"); //sinTaxis sql

                break;
            case R.id.bEliminar:
                dbContactos.delete("Contactos","nombre'"+nombre+"'",null);

                dbContactos.execSQL("DELETE FROM Contactos WHERE nombre='"+nombre+"'"); // sintaxis sqlite

                break;
        }
    }
}
