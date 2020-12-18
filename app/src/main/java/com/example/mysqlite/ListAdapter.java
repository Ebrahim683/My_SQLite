package com.example.mysqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    //Get Reference for set data
    ArrayList<DataBaseModel> arrayList;
    Context context;

    //Constructor
    public ListAdapter(Context context,ArrayList<DataBaseModel> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //set ta sample XML
        if (convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_sample,parent,false);
        }


      /*  LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.list_sample,parent,false);
       */

        //Set data in sample XML
        TextView nameSample,ageSample,professionSample;
        nameSample = convertView.findViewById(R.id.nameSample);
        ageSample = convertView.findViewById(R.id.ageSample);
        professionSample = convertView.findViewById(R.id.professionSample);

        nameSample.setText(arrayList.get(position).getName());
        ageSample.setText(String.valueOf(arrayList.get(position).getAge()));
        professionSample.setText(arrayList.get(position).getProfession());

        return convertView;
    }
}
