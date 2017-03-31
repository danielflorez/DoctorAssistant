package com.mangostatecnologia.doctorassistant;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Daniel on 10/10/14.
 */
public class customGrid extends BaseAdapter
{
    private Context mContext;
    private ArrayList<Calendar> mDays;

    public customGrid(Context c)
    {
        mContext = c;
    }

    public void updateDays(ArrayList<Calendar> dayList)
    {
        this.mDays = new ArrayList<Calendar>();
        this.mDays= dayList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount()
    {
        return mDays.size();
    }
    @Override
    public Calendar getItem(int position)
    {
        return mDays.get(position);
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Calendar g = mDays.get(position);
        SimpleDateFormat fmt = new SimpleDateFormat("dd");
        fmt.setCalendar(g);
        int weekday = g.get(Calendar.DAY_OF_WEEK);
        String dateFormatted = fmt.format(g.getTime());
        TextView textView = new TextView(mContext);
        textView.setHeight(169);
        textView.setBackgroundColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setText(dateFormatted);
        textView.setTextSize(24);

        if (weekday == 1 || weekday == 7)
        {
            textView.setTextColor(Color.RED);
        }
        return textView;
    }
}