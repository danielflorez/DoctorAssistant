package com.mangostatecnologia.doctorassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Daniel on 10/17/14.
 */
public class consultorioList extends Activity
{
    private ListView lv;
    private Button mAddConsultorioButton;
    private Boolean mAgenda;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultorio_list);
        mAgenda = getIntent().getExtras().getBoolean("agenda");
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        ArrayList conList = globalVariable.getmConsultorios();
        mAddConsultorioButton = (Button)findViewById(R.id.newConsultorio);
        mAddConsultorioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(),crearConsultorio.class);
                startActivity(i);
            }
        });
        lv = (ListView)findViewById(R.id.consulListView);
        globalVariable.setMcAdap(new consultorioBaseAdapter(getBaseContext()));
        globalVariable.getMcAdap().updateConsultorios(globalVariable.getmConsultorios());
        lv.setAdapter(globalVariable.getMcAdap());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList conList = globalVariable.getmConsultorios();
                consultorio con = (consultorio) conList.get(i);
                if (mAgenda)
                {
                    Intent iR = new Intent(getBaseContext(),agendaGrid.class);
                    iR.putExtra("idLoc", con.getIdLoc());
                    startActivity(iR);
                }
                else
                {

                    Intent iR = new Intent(getBaseContext(), updateConsultorio.class);
                    iR.putExtra("desc", con.getDescr());
                    iR.putExtra("direcc", con.getDireccion());
                    iR.putExtra("tel", con.getTelefono());
                    iR.putExtra("idLoc", con.getIdLoc());
                    iR.putExtra("pos", i);
                    startActivity(iR);
                }
            }
        });
    }
}
