package com.mangostatecnologia.doctorassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Daniel on 10/10/14.
 */
public class agendaGrid extends Activity {
    private GridView grid;
    private Button mNextMonth;
    private Button mPrevMonth;
    private TextView mMonthText;
    private ArrayList<Calendar> mDays = new ArrayList<Calendar>();
    private int mMonth;
    private int mYear;
    private int mWeekday;
    private int mDaysMonth;
    private customGrid mCustomGrid;
    private String midConsultorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_grid);
        midConsultorio = getIntent().getExtras().getString("idLoc");
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        c.setTime(today);
        mMonthText = (TextView)findViewById(R.id.monthText);
        mMonth = c.get(Calendar.MONTH)+1;
        mYear = c.get(Calendar.YEAR);
        String input_date;
        if (mMonth > 9)
        {
            input_date = "01/" + mMonth + "/" + mYear;
        } else
        {
            input_date = "01/0" + mMonth + "/" + mYear;
        }
        mMonthText.setText(getMonthString());
        mCustomGrid = new customGrid(agendaGrid.this);
        fillMonth();
        grid=(GridView)findViewById(R.id.agendaGrid);
        grid.setAdapter(mCustomGrid);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                //Toast.makeText(agendaGrid.this, "You Clicked at " + days[+position], Toast.LENGTH_SHORT).show();
                Intent iR = new Intent(getBaseContext(),dayGrid.class);
                Calendar g = mDays.get(position);
                SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyy");
                fmt.setCalendar(g);
                String dateFormatted = fmt.format(g.getTime());
                iR.putExtra("SelectedDate",dateFormatted);
                iR.putExtra("idConsul",midConsultorio);
                startActivity(iR);
            }
        });
        mNextMonth = (Button)findViewById(R.id.sigButton);
        mNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMonth();
            }
        });
        mPrevMonth = (Button)findViewById(R.id.anteButton);
        mPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevMonth();
            }
        });

    }

    public void fillMonth()
    {
        mDays = new ArrayList<Calendar>();
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        c.setTime(today);
        String input_date;
        if (mMonth > 9)
        {
            input_date = "01/" + mMonth + "/" + mYear;
        } else
        {
            input_date = "01/0" + mMonth + "/" + mYear;
        }
        Date dt1 = new Date();
        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
        try {
            dt1=format1.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(dt1);
        mWeekday = c.get(Calendar.DAY_OF_WEEK);
        mDaysMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int prevMonth = mMonth-1;
        int prevYear = mYear;
        if (prevMonth < 1)
        {
            prevMonth = 12;
            prevYear = prevYear-1;
        }
        Calendar ca = Calendar.getInstance();
        String input_date1;
        if (prevMonth > 9)
        {
            input_date1 = "01/" + prevMonth + "/" + prevYear;
        } else
        {
            input_date1 = "01/0" + prevMonth + "/" + prevYear;
        }
        Date dt2 = new Date();
        SimpleDateFormat format2=new SimpleDateFormat("dd/MM/yyyy");
        try {
            dt2=format2.parse(input_date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ca.setTime(dt2);

        int prevDaysMonth = ca.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 1;j < mWeekday;j ++)
        {
            int day = prevDaysMonth-mWeekday + j+1;
            Calendar cg = Calendar.getInstance();
            String input_date2;
            if (prevMonth > 9)
            {
                if(day < 10)
                {
                    input_date2 = "0"+day+"/" + prevMonth + "/" + prevYear;
                }
                else
                {
                    input_date2 = day+"/" + prevMonth + "/" + prevYear;
                }

            } else
            {
                if (day < 10)
                {
                    input_date2 = "0"+day+"/0" + prevMonth + "/" + prevYear;
                }
                else
                {
                    input_date2 = day+"/0" + prevMonth + "/" + prevYear;
                }
            }
            Date dt3 = new Date();
            SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                dt3=format3.parse(input_date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cg.setTime(dt3);
            mDays.add(cg);
        }
        int lastDayWeekday=7;
        for(int i = 1; i <= mDaysMonth;i++)
        {
            Calendar g = Calendar.getInstance();
            String input_date3;
            if (mMonth > 9)
            {
                if (i<10)
                {
                    input_date3 = "0"+i+"/" + mMonth + "/" + mYear;
                }
                else
                {
                    input_date3 = i+"/" + mMonth + "/" + mYear;
                }

            } else
            {
                if (i<10)
                {
                    input_date3 = "0"+i+"/0" + mMonth + "/" + mYear;
                }
                else
                {
                    input_date3 = i+"/0" + mMonth + "/" + mYear;
                }

            }
            Date dt4 = new Date();
            SimpleDateFormat format4 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                dt4=format4.parse(input_date3);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            g.setTime(dt4);
            lastDayWeekday = g.get(Calendar.DAY_OF_WEEK);
            mDays.add(g);
        }
        int nextMonth = mMonth+1;
        int nextYear = mYear;
        if (nextMonth > 12)
        {
            nextMonth = 1;
            nextYear = nextYear+1;
        }
        for (int k = 1;k < 8-lastDayWeekday;k ++)
        {
            Calendar n = Calendar.getInstance();
            String input_date4;
            if (nextMonth > 9)
            {
                if (k<10)
                {
                    input_date4 = "0"+k+"/" + nextMonth + "/" + nextYear;
                }
                else
                {
                    input_date4 = k+"/" +nextMonth + "/" + nextYear;
                }

            } else
            {
                if (k<10)
                {
                    input_date4 = "0"+k+"/0" + nextMonth + "/" + nextYear;
                }
                else
                {
                    input_date4 = k+"/0" + nextMonth + "/" + nextYear;
                }

            }
            Date dt5 = new Date();
            SimpleDateFormat format5 = new SimpleDateFormat("dd/MM/yyyy");
            try {
                dt5=format5.parse(input_date4);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            n.setTime(dt5);
            mDays.add(n);
        }
        String fechaInicoCitas;
        if (prevMonth < 10)
        {
            fechaInicoCitas = String.valueOf(prevYear)+"0"+String.valueOf(prevMonth)+"23";
        }
        else
        {
            fechaInicoCitas = String.valueOf(prevYear)+String.valueOf(prevMonth)+"23";
        }
        String fechaFinCitas;
        if (nextMonth < 10)
        {
            fechaFinCitas = String.valueOf(nextYear)+"0"+String.valueOf(nextMonth)+"06";
        }
        else
        {
            fechaFinCitas = String.valueOf(nextYear)+String.valueOf(nextMonth)+"06";
        }
        final GlobalClass globalVariable = (GlobalClass) this.getApplicationContext();
        globalVariable.getCitas(midConsultorio,fechaInicoCitas,fechaFinCitas);
        mCustomGrid.updateDays(mDays);
    }

    private String getMonthString()
    {
        String m = "";
        if(mMonth == 1)
        {
            m = "Enero "+mYear;
        }
        else if (mMonth == 2)
        {
            m = "Febrero "+mYear;
        }
        else if (mMonth == 3)
        {
            m = "Marzo "+mYear;
        }
        else if (mMonth == 4)
        {
            m = "Abril "+mYear;
        }
        else if (mMonth == 5)
        {
            m = "Mayo "+mYear;
        }
        else if (mMonth == 6)
        {
            m = "Junio "+mYear;
        }
        else if (mMonth == 7)
        {
            m = "Julio "+mYear;
        }
        else if (mMonth == 8)
        {
            m = "Agosto "+mYear;
        }
        else if (mMonth == 9)
        {
            m = "Septiembre "+mYear;
        }
        else if (mMonth == 10)
        {
            m = "Octubre "+mYear;
        }
        else if (mMonth == 11)
        {
            m = "Noviembre "+mYear;
        }
        else if (mMonth == 12)
        {
            m = "Diciembre "+mYear;
        }

        return m;
    }

    public void nextMonth()
    {
        mMonth++;
        if (mMonth > 12)
        {
            mMonth = 1;
            mYear++;
        }
        fillMonth();
        mMonthText.setText(getMonthString());
//        customGrid adapter = new customGrid(agendaGrid.this, days);
//        grid.setAdapter(adapter);
    }

    public void prevMonth()
    {
        mMonth --;
        if (mMonth < 1)
        {
            mMonth = 12;
            mYear--;
        }
        fillMonth();
        mMonthText.setText(getMonthString());
//        customGrid adapter = new customGrid(agendaGrid.this, days);
//        grid.setAdapter(adapter);
    }
}
