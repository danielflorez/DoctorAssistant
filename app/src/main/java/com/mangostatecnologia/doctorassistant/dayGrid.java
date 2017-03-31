package com.mangostatecnologia.doctorassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Daniel on 10/14/14.
 */
public class dayGrid extends Activity
{
    private GridView mGrid;
    private TextView mHourText;
    private String mDateString;
    private ArrayList<citaCompleta> mHours;
    private dayCustomGrid mAdapter;
    private String mIdConsul;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String r = b.getString("SelectedDate");
        final GlobalClass globalVariable = (GlobalClass) this.getApplicationContext();
        globalVariable.setmDAdap(new dayCustomGrid(getBaseContext()));
        mIdConsul = b.getString("idConsul");
        mDateString = r;
        setContentView(R.layout.day_grid);
        mAdapter = globalVariable.getmDAdap();
        fillDay();
        mHourText = (TextView)findViewById(R.id.dayText);
        mHourText.setText(r);
        mGrid=(GridView)findViewById(R.id.dayGrid);
        mGrid.setAdapter(mAdapter);
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                //Toast.makeText(getBaseContext(), "You Clicked at " + position, Toast.LENGTH_SHORT).show();
                Intent iR = new Intent(getBaseContext(), crearCita.class);
                iR.putExtra("posicion",position);
                iR.putExtra("idConsul",mIdConsul);
                startActivity(iR);
            }
        });

    }

    public void fillDay()
    {
        mHours = new ArrayList<citaCompleta>();
        final GlobalClass globalVariable = (GlobalClass) this.getApplicationContext();
        for (int i = 6;i < 21;i ++)
        {
            citaCompleta c1 = new citaCompleta();
            citaCompleta c2 = new citaCompleta();
            Calendar c0 = Calendar.getInstance();
            Calendar c30 = Calendar.getInstance();
            String input_date0;
            String input_date30;
            String input_dateNH;
            int l = i +1;
            if (i<10)
            {
                input_date0 = mDateString+" 0"+i+":"+"00"+":"+"00";
                input_date30 = mDateString+" 0"+i+":"+"30"+":"+"00";
            }
            else
            {
                input_date0 = mDateString+" "+i+":"+"00"+":"+"00";
                input_date30 = mDateString+" "+i+":"+"30"+":"+"00";
            }
            if (l < 10)
            {
                input_dateNH = mDateString+" 0"+l+":"+"00"+":"+"00";
            }
            else
            {
                input_dateNH = mDateString+" "+l+":"+"00"+":"+"00";
            }

            Date dt0 = new Date();
            Date dt30 = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {
                dt0 = format1.parse(input_date0);
                dt30 = format2.parse(input_date30);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c0.setTime(dt0);
            c30.setTime(dt30);
            c1.setHora(c0);
            c2.setHora(c30);
            ArrayList<cita> citas = globalVariable.getmCitas();
            for (int j = 0;j < citas.size();j ++)
            {
                String c = citas.get(j).getFechaInicio();
                String m = citas.get(j).getFechaFin();
                Calendar caleInicio = Calendar.getInstance();
                Calendar caleFin = Calendar.getInstance();
                Date inicioCita = new Date();
                Date finCita = new Date();
                SimpleDateFormat formatCita = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    inicioCita = formatCita.parse(c);
                    finCita = formatCita.parse(m);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                caleInicio.setTime(inicioCita);
                caleFin.setTime(finCita);
                if (inicioCita.equals(dt0))
                {
                    c1.setCita(citas.get(j));
                }
                else if (((inicioCita.before(dt0))&&((dt0.before(finCita))&&!(finCita.equals(dt0)))))
                {
                    c1.setCita(citas.get(j));
                }

                if (inicioCita.equals(dt30))
                {
                    c2.setCita(citas.get(j));
                }
                else if (((inicioCita.before(dt30))&&((dt30.before(finCita))&&!(finCita.equals(dt30)))))
                {
                    c2.setCita(citas.get(j));
                }

            }
            mHours.add(c1);
            mHours.add(c2);
         }
        globalVariable.setmCitasCompletas(mHours);
        mAdapter.updateHours(mHours);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillDay();
    }
}
