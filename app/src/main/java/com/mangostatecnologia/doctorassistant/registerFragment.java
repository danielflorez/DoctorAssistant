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

import java.util.concurrent.ExecutionException;

/**
 * Created by Daniel on 10/8/14.
 */
public class registerFragment extends Activity {
    private static final String TAG = "DoctorAssistant";
    private Button mRegisterButton;
    private EditText mNameText;
    private EditText mLastNameText;
    private EditText mCedulaText;
    private EditText mPhoneText;
    private EditText mAdressText;
    private EditText mPasswordText;
    private EditText mConfirmPassText;
    private EditText mEmailText;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_fragment);
        mRegisterButton = (Button)findViewById(R.id.registerButton);
        mNameText = (EditText)findViewById(R.id.Nombre);
        mLastNameText = (EditText)findViewById(R.id.Apellido);
        mCedulaText = (EditText)findViewById(R.id.Cedula);
        mPhoneText = (EditText)findViewById(R.id.Telefono);
        mAdressText = (EditText)findViewById(R.id.Direcion);
        mPasswordText = (EditText)findViewById(R.id.clave);
        mConfirmPassText = (EditText)findViewById(R.id.confirmarClave);
        mEmailText = (EditText)findViewById(R.id.Email);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = mNameText.getText().toString();
                String apellido = mLastNameText.getText().toString();
                String cedula = mCedulaText.getText().toString();
                String telefono = mPhoneText.getText().toString();
                String direccion = mAdressText.getText().toString();
                String clave = mPasswordText.getText().toString();
                String conClave = mConfirmPassText.getText().toString();
                String eMail = mEmailText.getText().toString();

                boolean valido = true;

                if (nombre.equals(""))
                {
                    valido = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(registerFragment.this);
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
                else if (apellido.equals(""))
                {
                    valido = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(registerFragment.this);
                    alertDialogBuilder.setTitle("Alerta")
                            .setMessage("El campo apellido no puede estar vacio.")
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
                else if (cedula.equals(""))
                {
                    valido = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(registerFragment.this);
                    alertDialogBuilder.setTitle("Alerta")
                            .setMessage("El campo cedula no puede estar vacio.")
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
                else if (eMail.equals(""))
                {
                    valido = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(registerFragment.this);
                    alertDialogBuilder.setTitle("Alerta")
                            .setMessage("El campo email no puede estar vacio.")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else if (clave.equals(""))
                {
                    valido = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(registerFragment.this);
                    alertDialogBuilder.setTitle("Alerta")
                            .setMessage("El campo clave no puede estar vacio.")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }else if (!(clave.equals(conClave)))
                {
                    valido = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(registerFragment.this);
                    alertDialogBuilder.setTitle("Alerta")
                            .setMessage("La clave y la confirmacion de la clave no son iguales.")
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

                String result = null;

                if (valido) {
                    try {
                        result = new RegisterIntent().execute(nombre, apellido, cedula, telefono, direccion, clave, eMail).get();
                    } catch (InterruptedException ioe) {
                        Log.e(TAG, "Failed to login", ioe);
                    } catch (ExecutionException ioe) {
                        Log.e(TAG, "Failed to login", ioe);
                    }
                }
            }
        });
    }

    private class RegisterIntent extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params){
            return new urlManager().registerTried(params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
        }
    }
}
