package com.mangostatecnologia.doctorassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Daniel on 11/12/14.
 */
public class procedimientoBaseAdapter extends BaseAdapter
{
    private ArrayList<procedimiento> mProcedimientoList = new ArrayList<procedimiento>();

    private final Context context;

    private static class ViewHolder
    {
        private TextView itemView;
    }

    public procedimientoBaseAdapter(Context context)
    {
        this.context = context;
    }

    public void updateProcedimiento(ArrayList<procedimiento> procedimientoList)
    {
        this.mProcedimientoList = new ArrayList<procedimiento>();
        this.mProcedimientoList = procedimientoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mProcedimientoList.size();
    }

    // getItem(int) in Adapter returns Object but we can override
    // it to BananaPhone thanks to Java return type covariance
    @Override
    public procedimiento getItem(int position)
    {
        return mProcedimientoList.get(position);
    }

    // getItemId() is often useless, I think this should be the default
    // implementation in BaseAdapter
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.procedimiento_list_item, parent, false);

            viewHolder.itemView = (TextView) convertView.findViewById(R.id.procedimientoNombre);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        procedimiento item = mProcedimientoList.get(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.itemView.setText(item.getNombre());
        }

        return convertView;
    }
}