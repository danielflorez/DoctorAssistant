package com.mangostatecnologia.doctorassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Daniel on 2/13/15.
 */
public class updatePacienteResulCita extends Fragment implements AdapterView.OnItemSelectedListener{
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.crear_paciente, container, false);
        final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
        mNombre = (EditText) rootView.findViewById(R.id.cPacienteNombre);
        mApellido = (EditText)rootView.findViewById(R.id.cPacienteApellido);
        mCedula = (EditText)rootView.findViewById(R.id.cPacienteCedula);
        mDireccion = (EditText)rootView.findViewById(R.id.cPacienteDireccion);
        mTelefono = (EditText)rootView.findViewById(R.id.cPacienteTelefono);
        mEmail = (EditText) rootView.findViewById(R.id.cPacienteEmail);
        mDia = (EditText)rootView.findViewById(R.id.cPacienteDia);
        mMes = (EditText)rootView.findViewById(R.id.cPacienteMes);
        mAnio = (EditText)rootView.findViewById(R.id.cPacienteAnio);
        mEnts = globalVariable.getmEntidades();
        mEntSpinner = (Spinner) rootView.findViewById(R.id.cPacienteEntSpinner);
        final Bundle b = getActivity().getIntent().getExtras();
        mNombre.setText(b.getString("nombre"));
        mApellido.setText(b.getString("apellido"));
        mCedula.setText(b.getString("cedula"));
        mDireccion.setText(b.getString("direccion"));
        mTelefono.setText(b.getString("telefono"));
        mEmail.setText(b.getString("email"));
        String fech = b.getString("fechaNacimiento");
        String idEntidad = b.getString("idEntidad");
        if (fech.length()>2)
        {
            mAnio.setText(fech.substring(0, 4));
            mMes.setText(fech.substring(5, 7));
            mDia.setText(fech.substring(8, 10));
        }
        mCrear = (Button)rootView.findViewById(R.id.cPacienteBoton);
        mCrear.setText("Grabar");
        String[] spinnerArray = new String[mEnts.size()+1];
        spinnerArray[0] = "Seleccione la entidad";
        int k = 0;
        for (int i = 1;i<spinnerArray.length;i++)
        {
            entidad en = mEnts.get(i-1);
            spinnerArray[i] = en.getNombre();
            if (idEntidad.equals(en.getIdEnt()))
            {
                k=i;
            }

        }
        if (k>0)
        {
            mIdEntSeleccionada = mEnts.get(k-1).getIdEnt();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEntSpinner.setAdapter(adapter);
        mEntSpinner.setOnItemSelectedListener(this);
        mEntSpinner.setSelection(k);
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
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
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
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
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
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
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
                    final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
                    String memberID = globalVariable.getMid();

                    String result = "";
                    try {
                        result = new updatePacienteTask().execute(cedula,email,fechaNac,nombre,apellido,telefono,direccion,memberID,mIdEntSeleccionada).get();
                    }catch (InterruptedException ioe){
                        Log.e(TAG, "Failed to login", ioe);
                    }catch (ExecutionException ioe){
                        Log.e(TAG,"Failed to login",ioe);
                    }
                    if (result.equals("OK")) {
                        paciente p = new paciente();
                        p.setNombre(nombre);
                        p.setApellido(apellido);
                        p.setCedula(cedula);
                        p.setDireccion(direccion);
                        p.setTelefono(telefono);
                        p.setEmail(email);
                        p.setFechaNacimiento(fechaNac);
                        p.setIdEntidad(mIdEntSeleccionada);
                        int pos = b.getInt("pos");
                        globalVariable.getmPacientes().set(pos, p);
                        ArrayList<paciente> pac = globalVariable.getmPacientes();
                        globalVariable.getMpAdap().updatePacientes(pac);
                        getActivity().onBackPressed();
                    }
                }
            }
        });
        return rootView;
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

    private class updatePacienteTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params){
            return new urlManager().updatePaciente(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7],params[8]);
        }
    }
}
