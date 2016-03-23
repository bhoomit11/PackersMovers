package com.example.pack.packersmovers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.pack.packersmovers.model.vehiclelist;
/**
 * Created by root on 22/3/16.
 */
public class adapVehicleList extends BaseAdapter{
    private Context context;
    ArrayList<vehiclelist> arrayList;

    Activity parentactivity;
    public adapVehicleList(Context context,ArrayList<vehiclelist> arrayList, Activity parentactivity) {
        this.context = context;
        this.arrayList=arrayList;
        this.parentactivity=parentactivity;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final vehiclelist vl=arrayList.get(position);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.cust_vehiclelist, null);
        }

        TextView vname,vtype;

        vname=(TextView) convertView.findViewById(R.id.vname);
        vtype=(TextView) convertView.findViewById(R.id.vtypes);

        vname.setText(vl.getVname());
        vtype.setText(vl.getVtype());

        return convertView;
    }
}
