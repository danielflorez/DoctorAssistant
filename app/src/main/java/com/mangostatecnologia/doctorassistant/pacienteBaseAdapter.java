package com.mangostatecnologia.doctorassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 10/27/14.
 */
public class pacienteBaseAdapter extends BaseAdapter implements Filterable
{
    private ArrayList<paciente> mPacienteList = new ArrayList<paciente>();
    private ArrayList<paciente> mPacienteListCopy = new ArrayList<paciente>();
    private final Context context;

    private PacienteFilter mPacienteFilter;

    @Override
    public Filter getFilter()
    {
        if (mPacienteFilter == null)
            mPacienteFilter = new PacienteFilter();

        return mPacienteFilter;
    }

    public void resetData()
    {
        mPacienteList = mPacienteListCopy;
    }

    private static class ViewHolder
    {
        private TextView nombreView;
        private TextView cedulaView;
        private TextView telefonoView;
    }

    public pacienteBaseAdapter(Context context)
    {
        this.context = context;
    }

    public void updatePacientes(ArrayList<paciente> pacienteList)
    {
        this.mPacienteList = new ArrayList<paciente>();
        this.mPacienteListCopy = new ArrayList<paciente>();
        this.mPacienteList = pacienteList;
        this.mPacienteListCopy = pacienteList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return mPacienteList.size();
    }

    // getItem(int) in Adapter returns Object but we can override
    // it to BananaPhone thanks to Java return type covariance
    @Override
    public paciente getItem(int position)
    {
        return mPacienteList.get(position);
    }

    // getItemId() is often useless, I think this should be the default
    // implementation in BaseAdapter
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.paciente_list_item, parent, false);

            viewHolder.nombreView = (TextView) convertView.findViewById(R.id.pacienteNombre);
            viewHolder.cedulaView = (TextView) convertView.findViewById(R.id.pacienteCedula);
            viewHolder.telefonoView = (TextView) convertView.findViewById(R.id.pacienteTelefono);

            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        paciente item = mPacienteList.get(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.nombreView.setText(String.format("%s %s", item.getNombre(), item.getApellido()));
            viewHolder.cedulaView.setText(item.getCedula());
            viewHolder.telefonoView.setText(item.getTelefono());
        }

        return convertView;
    }

    private class PacienteFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0)
            {
                // No filter implemented we return all the list
                results.values = mPacienteList;
                results.count = mPacienteList.size();
            }
            else {
                // We perform filtering operation
                List<paciente> nPacienteList = new ArrayList<paciente>();

                for (paciente p : mPacienteList)
                {
                    if ((p.getNombre().toUpperCase().contains(constraint.toString().toUpperCase()))
                            ||(p.getApellido().toUpperCase().contains(constraint.toString().toUpperCase()))
                            ||(p.getCedula().toUpperCase().contains(constraint.toString().toUpperCase())))

                    {
                        nPacienteList.add(p);
                    }
                }

                results.values = nPacienteList;
                results.count = nPacienteList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results)
        {
            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
            {
                notifyDataSetInvalidated();
            }
            else
            {
                mPacienteList = (ArrayList<paciente>) results.values;
                notifyDataSetChanged();
            }
        }

    }
}
