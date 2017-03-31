package com.mangostatecnologia.doctorassistant;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Daniel on 11/11/14.
 */
public class entidadList extends Activity
{
    private ListView lvEntidad;
    private Button mAddEntidadButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entidad_list);
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        final ArrayList entList = globalVariable.getmEntidades();
        mAddEntidadButton = (Button)findViewById(R.id.newEntidad);
        mAddEntidadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                JSONObject entJson = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                for (int i = 0;i < entList.size(); i ++ )
                {
                    entidad ent = (entidad)entList.get(i);
                    if (ent.getCheck())
                    {
                        try {
                            entJson = new JSONObject();
                            entJson.put("idEnt",ent.getIdEnt());
                            jsonArray.put(entJson);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                String id = globalVariable.getMid();
                String arrayJson = jsonArray.toString();
                String result = "";
                try {
                    result = new grabarEntidades().execute(id,arrayJson).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (result.equals("OK"))
                {
                    onBackPressed();
                }
            }
        });
        lvEntidad = (ListView)findViewById(R.id.entidadListView);
        globalVariable.setMeAdap(new entidadBaseAdapter(getBaseContext()));
        globalVariable.getMeAdap().updateEntidad(globalVariable.getmEntidades());
        lvEntidad.setAdapter(globalVariable.getMeAdap());
        lvEntidad.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                ArrayList entList = globalVariable.getmEntidades();
                entidad ent = (entidad)entList.get(i);
                ent.setCheck(!ent.getCheck());
                //globalVariable.getmEntidades().set(i,ent);
                globalVariable.getMeAdap().updateEntidad(globalVariable.getmEntidades());
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
