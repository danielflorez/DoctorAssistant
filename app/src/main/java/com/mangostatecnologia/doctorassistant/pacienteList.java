package com.mangostatecnologia.doctorassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Daniel on 10/27/14.
 */
public class pacienteList extends Activity
{
    private ListView lvPaci;
    private Button mAddPacienteButton;
    private EditText mSearch;
    private Boolean mCita;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_list);
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        ArrayList conList = globalVariable.getmConsultorios();
        mCita = getIntent().getExtras().getBoolean("cita");
        mAddPacienteButton = (Button)findViewById(R.id.newPaciente);
        mSearch = (EditText)findViewById(R.id.busPaciente);
        mAddPacienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(),crearPaciente.class);
                startActivity(i);
            }
        });
        lvPaci = (ListView)findViewById(R.id.pacienteListView);
        globalVariable.setMpAdap(new pacienteBaseAdapter(getBaseContext()));
        globalVariable.getMpAdap().updatePacientes(globalVariable.getmPacientes());
        lvPaci.setAdapter(globalVariable.getMpAdap());
        lvPaci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               ArrayList pacList = globalVariable.getmPacientes();
               paciente pac = (paciente)pacList.get(i);
               if (mCita)
               {
                   Intent intent = new Intent();
                   intent.putExtra("idPac",pac.getCedula());
                   setResult(RESULT_OK, intent);
                   finish();
               }
                else
               {

                   Intent iR = new Intent(getBaseContext(), updatePaciente.class);
                   iR.putExtra("nombre", pac.getNombre());
                   iR.putExtra("apellido",pac.getApellido());
                   iR.putExtra("cedula",pac.getCedula());
                   iR.putExtra("direccion",pac.getDireccion());
                   iR.putExtra("telefono",pac.getTelefono());
                   iR.putExtra("email",pac.getEmail());
                   iR.putExtra("fechaNacimiento",pac.getFechaNacimiento());
                   iR.putExtra("idEntidad",pac.getIdEntidad());
                   iR.putExtra("pos",i);
                   startActivity(iR);
               }

            }
        });
        mSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence,int start, int before, int count)
            {
                if (count < before)
                {
                    // We're deleting char so we need to reset the adapter data
                    globalVariable.getMpAdap().resetData();
                }
                globalVariable.getMpAdap().getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }
}
