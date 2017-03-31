package com.mangostatecnologia.doctorassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Daniel on 10/14/14.
 */
public class dayCustomGrid extends BaseAdapter
{
    private Context mContext;
    private ArrayList<citaCompleta> mHours = new ArrayList<citaCompleta>();

    private static class ViewHolder
    {
        private TextView hourText;
        private TextView descText;
        private TextView nombreText;
        private TextView telText;
    }

    public dayCustomGrid(Context c)
    {
        mContext = c;
    }

    public void updateHours(ArrayList<citaCompleta> hourList)
    {
        this.mHours = new ArrayList<citaCompleta>();
        this.mHours = hourList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mHours.size();
    }
    @Override
    public citaCompleta getItem(int position) {
        // TODO Auto-generated method stub
        return mHours.get(position);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.daysinglegrid, parent, false);

            viewHolder.hourText = (TextView) convertView.findViewById(R.id.hourText);
            viewHolder.descText = (TextView) convertView.findViewById(R.id.citaText);
            viewHolder.nombreText = (TextView) convertView.findViewById(R.id.citaNombrePaciente);
            viewHolder.telText = (TextView) convertView.findViewById(R.id.citaTelefonoPaciente);

            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        citaCompleta cita = mHours.get(position);
        if (cita != null) {
            cita c = cita.getCita();
            Calendar cal = cita.getHora();
            SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
            fmt.setCalendar(cal);
            String dateFormatted = fmt.format(cal.getTime());
            viewHolder.hourText.setText(dateFormatted);
            if (c != null)
            {
                viewHolder.descText.setText(c.getDesc());
                viewHolder.nombreText.setText(String.format("%s %s", c.getPaci().getNombre(), c.getPaci().getApellido()));
                viewHolder.telText.setText(c.getPaci().getTelefono());
            }
            else
            {
                viewHolder.descText.setText("");
                viewHolder.nombreText.setText("");
                viewHolder.telText.setText("");
            }
        }

        return convertView;
    }
}
