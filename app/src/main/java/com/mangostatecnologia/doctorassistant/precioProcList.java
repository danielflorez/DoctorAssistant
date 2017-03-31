package com.mangostatecnologia.doctorassistant;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Daniel on 11/12/14.
 */
public class precioProcList extends Activity
{
    private ListView lvPrecio;
    private Button mGrabarBoton;
    private procedimiento mProc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.precio_proc_list);
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        ArrayList entListTemp = globalVariable.getmEntidades();
        ArrayList <precioProcedimiento> precioArrayTem = globalVariable.getmPrecioProcedmiento();
        String memberId = globalVariable.getMid();
        final Bundle b = getIntent().getExtras();
        int pos = b.getInt("pos");
        mProc = globalVariable.getmProcedimientos().get(pos);
        mGrabarBoton = (Button)findViewById(R.id.grabarPrecioProc);
        mGrabarBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ArrayList temp = globalVariable.getmPPAdap().getmPrecioList();
                JSONObject entJson = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                for (int i = 0;i < temp.size(); i ++ )
                {
                    precioProcedimiento pre = (precioProcedimiento)temp.get(i);
                    try
                    {
                        entJson = new JSONObject();
                        entJson.put(pre.getIdEnt(),pre.getPrecio());
                        jsonArray.put(entJson);
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                String id = globalVariable.getMid();
                String arrayJson = jsonArray.toString();
                String result = "";
                try {
                    result = new grabarPrecios().execute(id,mProc.getIdProc().toString(),arrayJson).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (result.equals("OK"))
                {
                    globalVariable.getPreciosXProcedmiento();
                    onBackPressed();
                }
            }
        });
        lvPrecio = (ListView)findViewById(R.id.precioProcListView);
        ArrayList<precioProcedimiento> precioArrayFinal = new ArrayList<precioProcedimiento>();
        for (int i = 0; i < entListTemp.size(); i ++)
        {
           entidad ent = (entidad) entListTemp.get(i);
           if (ent.getIdMedico().equals(memberId))
           {
               precioProcedimiento pre = new precioProcedimiento();
               pre.setIdEnt(ent.getIdEnt());
               pre.setNombreEnt(ent.getNombre());
               pre.setIdProc(mProc.getIdProc());
               for (int j = 0;j < precioArrayTem.size();j ++)
               {
                   precioProcedimiento p = precioArrayTem.get(j);
                   if (p.getIdProc().equals(mProc.getIdProc()))
                   {
                       if (pre.getIdEnt().equals(p.getIdEnt()))
                       {
                           pre.setPrecio(p.getPrecio());
                       }
                   }
               }
               precioArrayFinal.add(pre);
           }
        }
        globalVariable.setmPPAdap(new precioProcBaseAdapter(getBaseContext()));
        globalVariable.getmPPAdap().updatePrecios(precioArrayFinal);
        lvPrecio.setAdapter(globalVariable.getmPPAdap());
    }
    private class grabarPrecios extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params){
            return new urlManager().grabarPreciosXProcedimentos(params[0],params[1],params[2]);
        }
    }
}
