package com.mangostatecnologia.doctorassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Daniel on 11/11/14.
 */
public class entidadBaseAdapter  extends BaseAdapter
{
    private ArrayList<entidad> mEntidadList = new ArrayList<entidad>();

    private final Context context;

    private static class ViewHolder
    {
        private TextView itemView;
        private CheckBox itemCheck;
    }

    public entidadBaseAdapter(Context context)
    {
        this.context = context;
    }

    public void updateEntidad(ArrayList<entidad> entidadList)
    {
        this.mEntidadList = new ArrayList<entidad>();
        this.mEntidadList = entidadList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mEntidadList.size();
    }

    // getItem(int) in Adapter returns Object but we can override
    // it to BananaPhone thanks to Java return type covariance
    @Override
    public entidad getItem(int position)
    {
        return mEntidadList.get(position);
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
                    .inflate(R.layout.entidad_list_item, parent, false);

            viewHolder.itemView = (TextView) convertView.findViewById(R.id.entidadNombre);
            viewHolder.itemCheck = (CheckBox)convertView.findViewById(R.id.entidadCheck);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        entidad item = mEntidadList.get(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.itemView.setText(item.getNombre());
            viewHolder.itemCheck.setChecked(item.getCheck());
        }

        return convertView;
    }
}