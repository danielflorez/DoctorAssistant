package com.mangostatecnologia.doctorassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Daniel on 11/12/14.
 */
public class procedimientoList extends Activity
{
    private ListView lvProcedimiento;
    private Button mAddProcedimientoButton;
    private Boolean mCita;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.procedimento_list);
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        final ArrayList procList = globalVariable.getmProcedimientos();
        mCita = getIntent().getExtras().getBoolean("cita");
        mAddProcedimientoButton = (Button)findViewById(R.id.newProcedimiento);
        mAddProcedimientoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent iR = new Intent(getBaseContext(),crearProcedimiento.class);
                startActivity(iR);
            }
        });
        lvProcedimiento = (ListView)findViewById(R.id.procedimientoListView);
        globalVariable.setMprAdap(new procedimientoBaseAdapter(getBaseContext()));
        globalVariable.getMprAdap().updateProcedimiento(globalVariable.getmProcedimientos());
        lvProcedimiento.setAdapter(globalVariable.getMprAdap());
        lvProcedimiento.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                ArrayList conList = globalVariable.getmProcedimientos();
                procedimiento pro = (procedimiento) conList.get(i);
                if (mCita)
                {
                    Intent intent = new Intent();
                    intent.putExtra("idProc",pro.getIdProc());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                {
                    Intent iR = new Intent(getBaseContext(), updateProcedimiento.class);
                    iR.putExtra("nombre", pro.getNombre());
                    iR.putExtra("idProc", pro.getIdProc());
                    iR.putExtra("pos", i);
                    startActivity(iR);
                }
            }
        });
    }
    private class grabarEntidades extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params){
            return new urlManager().grabarEntidades(params[0],params[1]);
        }
    }
}
