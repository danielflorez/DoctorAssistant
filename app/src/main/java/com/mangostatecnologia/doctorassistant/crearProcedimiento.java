package com.mangostatecnologia.doctorassistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Daniel on 11/12/14.
 */
public class crearProcedimiento extends Activity
{
    private static final String TAG = "DoctorAssistant";
    private Button mCrear;
    private EditText mNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_procedimiento);
        mNombre = (EditText)findViewById(R.id.nombreProcedimiento);
        mCrear = (Button)findViewById(R.id.crearProcedimentoBoton);
        mCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = mNombre.getText().toString();
                Boolean valido = true;
                if (nom.equals(""))
                {
                    valido = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(crearProcedimiento.this);
                    alertDialogBuilder.setTitle("Alerta")
                            .setMessage("El campo nombre no puede estar vacio.")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }

                if (valido)
                {
                    final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
                    String id = globalVariable.getMid();
                    String result = "";
                    try {
                        result = new CrearProcedimientoTask().execute(id,nom).get();
                    }catch (InterruptedException ioe){
                        Log.e(TAG, "Failed to login", ioe);
                    }catch (ExecutionException ioe){
                        Log.e(TAG,"Failed to login",ioe);
                    }
                    if (result.equals("OK"))
                    {
                        globalVariable.getProcedmientos();
                        ArrayList<procedimiento> proce = globalVariable.getmProcedimientos();
                        globalVariable.getMprAdap().updateProcedimiento(proce);
                        onBackPressed();
                    }
                }
            }
        });
    }

    private class CrearProcedimientoTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params){
            return new urlManager().crearProcedimento(params[0],params[1]);
        }
    }
}

