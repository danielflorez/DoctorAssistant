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
 * Created by Daniel on 10/15/14.
 */
public class crearConsultorio extends Activity
{
    private static final String TAG = "DoctorAssistant";
    private Button mCrear;
    private EditText mDescipcion;
    private EditText mDireccion;
    private EditText mTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearconsultorio);
        mDescipcion = (EditText)findViewById(R.id.descripcionConsultorio);
        mDireccion = (EditText)findViewById(R.id.direccionConsultorio);
        mTelefono = (EditText)findViewById(R.id.telefonoConsultorio);
        mCrear = (Button)findViewById(R.id.crearConsultorioBoton);
        mCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desc = mDescipcion.getText().toString();
                String direcc = mDireccion.getText().toString();
                String tel = mTelefono.getText().toString();
                Boolean valido = true;
                if (desc.equals(""))
                {
                    valido = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(crearConsultorio.this);
                    alertDialogBuilder.setTitle("Alerta")
                            .setMessage("El campo descripcion no puede estar vacio.")
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
                else if (tel.equals(""))
                {
                    valido = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(crearConsultorio.this);
                    alertDialogBuilder.setTitle("Alerta")
                            .setMessage("El campo telefono no puede estar vacio.")
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
                else if (direcc.equals(""))
                {
                    valido = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(crearConsultorio.this);
                    alertDialogBuilder.setTitle("Alerta")
                            .setMessage("El campo direccion no puede estar vacio.")
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
                    String email = globalVariable.getEmail();
                    String result = "";
                    try {
                        result = new CrearConsultorioTask().execute(email,desc,direcc,tel).get();
                    }catch (InterruptedException ioe){
                        Log.e(TAG, "Failed to login", ioe);
                    }catch (ExecutionException ioe){
                        Log.e(TAG,"Failed to login",ioe);
                    }
                    if (result.equals("OK"))
                    {
                        globalVariable.getConsultorios();
                        ArrayList<consultorio> cons = globalVariable.getmConsultorios();
                        globalVariable.getMcAdap().updateConsultorios(cons);
                        onBackPressed();
                    }
                }
            }
        });
    }

    private class CrearConsultorioTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params){
            return new urlManager().crearConsultorio(params[0],params[1],params[2],params[3]);
        }
    }
}
