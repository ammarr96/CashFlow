package com.greenback.cashflow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.greenback.cashflow.R;

import java.util.ArrayList;
import java.util.List;


public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private List<String> list;

    public CustomSpinnerAdapter(Context context, List<String> list) {
        this.activity = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View myView = layoutInflater.inflate(R.layout.custom_spinner_item, null);
        TextView textView = myView.findViewById(R.id.item);
        textView.setText(list.get(position));
        return myView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View myView = layoutInflater.inflate(R.layout.custom_spinner_item, null);
        TextView textView = myView.findViewById(R.id.item);
        textView.setText(list.get(position));

        return myView;
    }

    public void setData(List<String> data) {
        this.list = data;
        notifyDataSetChanged();
    }
}
