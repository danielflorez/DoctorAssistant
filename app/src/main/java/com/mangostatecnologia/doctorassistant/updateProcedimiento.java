package com.mangostatecnologia.doctorassistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Daniel on 11/12/14.
 */
public class updateProcedimiento extends Activity
{
    private static final String TAG = "DoctorAssistant";
    private Button mCrear;
    private EditText mNombre;
    private Button mPreciosButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_procedimiento);
        mNombre = (EditText)findViewById(R.id.nombreProcedimiento);
        mCrear = (Button)findViewById(R.id.crearProcedimentoBoton);
        mCrear.setText("Grabar");
        final Bundle b = getIntent().getExtras();
        final String desc = b.getString("nombre");
        final String idProc = b.getString("idProc");
        mNombre.setText(desc);
        mNombre.setGravity(Gravity.LEFT);
        mCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomb = mNombre.getText().toString();
                Boolean valido = true;
                if (nomb.equals(""))
                {
                    valido = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(updateProcedimiento.this);
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
                    String result = "";
                    try {
                        result = new updateProcedimientoTask().execute(idProc,nomb).get();
                    }catch (InterruptedException ioe){
                        Log.e(TAG, "Failed to login", ioe);
                    }catch (ExecutionException ioe){
                        Log.e(TAG,"Failed to login",ioe);
                    }
                    if (result.equals("OK"))
                    {
                        procedimiento c = new procedimiento();
                        c.setNombre(nomb);
                        int p = b.getInt("pos");
                        globalVariable.getmProcedimientos().set(p, c);
                        ArrayList<procedimiento> procs = globalVariable.getmProcedimientos();
                        globalVariable.getMprAdap().updateProcedimiento(procs);
                        onBackPressed();
                    }
                }
            }
        });
        mPreciosButton = (Button)findViewById(R.id.selecPreciosProcedimientos);
        mPreciosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getBaseContext(),precioProcList.class);
                int p = b.getInt("pos");
                i.putExtra("pos",p);
                startActivity(i);
            }
        });
    }

    private class updateProcedimientoTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params){
            return new urlManager().updateProcedimento(params[0], params[1]);
        }
    }
}
