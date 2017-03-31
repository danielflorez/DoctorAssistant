package com.mangostatecnologia.doctorassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Daniel on 9/12/14.
 */
public class mainMenu extends Activity
{
    private Button mAgendaButton;
    private Button mCrearConsultorio;
    private Button mPacienteButton;
    private Button mEntidadButton;
    private Button mProcedimientoButton;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        final GlobalClass globalVariable = (GlobalClass) this.getApplicationContext();
        globalVariable.getDianosticos();
        globalVariable.getPacientes();
        globalVariable.getConsultorios();
        globalVariable.getEntidades();
        globalVariable.getProcedmientos();
        globalVariable.getPreciosXProcedmiento();
        globalVariable.getMedicamentos();
        ArrayList proc = globalVariable.getmProcedimientos();
        mAgendaButton = (Button)findViewById(R.id.agenda);
        mAgendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iR = new Intent(getBaseContext(),consultorioList.class);
                iR.putExtra("agenda", true);
                startActivity(iR);
            }
        });
        mCrearConsultorio = (Button)findViewById(R.id.crearConsultorio);
        mCrearConsultorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iR = new Intent(getBaseContext(),consultorioList.class);
                iR.putExtra("agenda", false);
                startActivity(iR);
            }
        });
        mPacienteButton = (Button)findViewById(R.id.pacientesMain);
        mPacienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iR = new Intent(getBaseContext(),pacienteList.class);
                iR.putExtra("cita",false);
                startActivity(iR);
            }
        });
        mEntidadButton = (Button)findViewById(R.id.entidadesMain);
        mEntidadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iR = new Intent(getBaseContext(),entidadList.class);
                startActivity(iR);
            }
        });
        mProcedimientoButton = (Button)findViewById(R.id.procedimientosMain);
        mProcedimientoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iR = new Intent(getBaseContext(),procedimientoList.class);
                iR.putExtra("cita",false);
                startActivity(iR);
            }
        });
    }

    @Override
    public void onBackPressed()
    {

    }
}
