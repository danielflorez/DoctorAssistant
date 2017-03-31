package com.mangostatecnologia.doctorassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Daniel on 10/20/14.
 */
public class consultorioBaseAdapter extends BaseAdapter
{
    private ArrayList<consultorio> mConsultorioList = new ArrayList<consultorio>();

    private final Context context;

    private static class ViewHolder
    {
        private TextView itemView;
    }

    public consultorioBaseAdapter(Context context)
    {
        this.context = context;
    }

    public void updateConsultorios(ArrayList<consultorio> consultoriosList)
    {
        this.mConsultorioList = new ArrayList<consultorio>();
        this.mConsultorioList = consultoriosList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mConsultorioList.size();
    }

    // getItem(int) in Adapter returns Object but we can override
    // it to BananaPhone thanks to Java return type covariance
    @Override
    public consultorio getItem(int position)
    {
        return mConsultorioList.get(position);
    }

    // getItemId() is often useless, I think this should be the default
    // implementation in BaseAdapter
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.consul_list_item, parent, false);

            viewHolder.itemView = (TextView) convertView.findViewById(R.id.labelDescConsul);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        consultorio item = mConsultorioList.get(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.itemView.setText(String.format("%s %s", item.getDireccion(), item.getTelefono()));
        }

        return convertView;
    }
}
