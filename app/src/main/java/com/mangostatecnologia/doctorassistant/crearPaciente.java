package com.mangostatecnologia.doctorassistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Daniel on 10/28/14.
 */
public class crearPaciente extends Activity implements AdapterView.OnItemSelectedListener
{
    private static final String TAG = "DoctorAssistant";
    private Button mCrear;
    private EditText mNombre;
    private EditText mApellido;
    private EditText mCedula;
    private EditText mDireccion;
    private EditText mTelefono;
    private EditText mEmail;
    private EditText mDia;
    private EditText mMes;
    private EditText mAnio;
    private String mIdEntSeleccionada;
    private Spinner mEntSpinner;
    private ArrayList<entidad> mEnts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_paciente);
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        mNombre = (EditText) findViewById(R.id.cPacienteNombre);
        mApellido = (EditText)findViewById(R.id.cPacienteApellido);
        mCedula = (EditText)findViewById(R.id.cPacienteCedula);
        mDireccion = (EditText)findViewById(R.id.cPacienteDireccion);
        mTelefono = (EditText)findViewById(R.id.cPacienteTelefono);
        mEmail = (EditText) findViewById(R.id.cPacienteEmail);
        mDia = (EditText)findViewById(R.id.cPacienteDia);
        mMes = (EditText)findViewById(R.id.cPacienteMes);
        mAnio = (EditText)findViewById(R.id.cPacienteAnio);
        mEnts = globalVariable.getmEntidades();
        mEntSpinner = (Spinner) findViewById(R.id.cPacienteEntSpinner);
        mCrear = (Button)findViewById(R.id.cPacienteBoton);
        String[] spinnerArray = new String[mEnts.size()+1];
        spinnerArray[0] = "Seleccione la entidad";
        for (int i = 1;i<spinnerArray.length;i++)
        {
            entidad en = mEnts.get(i-1);
            spinnerArray[i] = en.getNombre();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEntSpinner.setAdapter(adapter);
        mEntSpinner.setOnItemSelectedListener(this);
        mCrear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String nombre =  mNombre.getText().toString();
                String apellido = mApellido.getText().toString();
                String cedula = mCedula.getText().toString();
                String direccion = mDireccion.getText().toString();
                String telefono = mTelefono.getText().toString();
                String email = mEmail.getText().toString();
                String fechaNac = null;
                String dia = "";
                String mes = "";
                String anio = "";
                dia = mDia.getText().toString();
                mes = mMes.getText().toString();
                anio = mAnio.getText().toString();
                if (!dia.equals("") & !mes.equals("") & !anio.equals(""))
                {
                    fechaNac = anio+mes+dia;
                }
                Boolean valido = true;
                if (nombre.equals(""))
                {
                    valido = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(crearPaciente.this);
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
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(crearPaciente.this);
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
                }else if (cedula.equals(""))
                {
                    valido = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(crearPaciente.this);
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
                if (valido)
                {
                    final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
                    String idMedico = globalVariable.getMid();
                    String result = "";
                    try
                    {
                        result = new CrearPacienteTask().execute(cedula,email,fechaNac,nombre,apellido,telefono,direccion,idMedico,mIdEntSeleccionada).get();
                    }catch (InterruptedException ioe){
                        Log.e(TAG, "Failed to login", ioe);
                    }catch (ExecutionException ioe){
                        Log.e(TAG,"Failed to login",ioe);
                    }
                    if (result.equals("OK"))
                    {
                        globalVariable.getPacientes();
                        ArrayList<paciente> pac = globalVariable.getmPacientes();
                        globalVariable.getMpAdap().updatePacientes(pac);
                        onBackPressed();
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {

        switch (position)
        {
            case 0:
                mIdEntSeleccionada = null;
                break;
            default:
                mIdEntSeleccionada = mEnts.get(position-1).getIdEnt();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    private class CrearPacienteTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params){
            return new urlManager().crearPaciente(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7],params[8]);
        }
    }
}
