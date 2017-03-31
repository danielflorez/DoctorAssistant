package com.mangostatecnologia.doctorassistant;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Daniel on 11/12/14.
 */
public class precioProcBaseAdapter extends BaseAdapter
{
    private ArrayList<precioProcedimiento> mPrecioList = new ArrayList<precioProcedimiento>();

    private final Context context;

    public ArrayList<precioProcedimiento> getmPrecioList()
    {
        return mPrecioList;
    }

    public void setmPrecioList(ArrayList<precioProcedimiento> mPrecioList) {
        this.mPrecioList = mPrecioList;
    }

    private static class ViewHolder
    {
        private TextView entidadText;
        private EditText valorText;
        public MutableWatcher mWatcher;
    }

    public precioProcBaseAdapter(Context context)
    {
        this.context = context;
    }

    public void updatePrecios(ArrayList<precioProcedimiento> precios)
    {
        this.mPrecioList = new ArrayList<precioProcedimiento>();
        this.mPrecioList = precios;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPrecioList.size();
    }

    // getItem(int) in Adapter returns Object but we can override
    // it to BananaPhone thanks to Java return type covariance
    @Override
    public precioProcedimiento getItem(int position)
    {
        return mPrecioList.get(position);
    }

    // getItemId() is often useless, I think this should be the default
    // implementation in BaseAdapter
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.precio_proc_list_item, parent, false);

            viewHolder.entidadText = (TextView) convertView.findViewById(R.id.precioProcEntidadText);
            viewHolder.valorText = (EditText)convertView.findViewById(R.id.precioProText);
            viewHolder.mWatcher = new MutableWatcher();
            viewHolder.valorText.addTextChangedListener(viewHolder.mWatcher);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        precioProcedimiento item = mPrecioList.get(position);
        if (item!= null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.entidadText.setText(item.getNombreEnt());
            viewHolder.valorText.setText(item.getPrecio());
        }
        viewHolder.mWatcher.setActive(false);
        viewHolder.mWatcher.setPosition(position);
        viewHolder.mWatcher.setActive(true);
        return convertView;
    }

    class MutableWatcher implements TextWatcher {

        private int mPosition;
        private boolean mActive;

        void setPosition(int position)
        {
            mPosition = position;
        }

        void setActive(boolean active) {
            mActive = active;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s)
        {
            if (mActive) {
                mPrecioList.get(mPosition).setPrecio(s.toString());
            }
        }
    }
}

