package com.example.pack.packersmovers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.pack.packersmovers.model.mprofileMod;

/**
 * Created by root on 29/3/16.
 */
public class adapPackerSearch extends BaseAdapter {
    Context context;
    ArrayList<mprofileMod> arrayList;

    public adapPackerSearch(Context context,ArrayList<mprofileMod> arrayList)
    {
        this.context=context;
        this.arrayList = arrayList;
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

        final mprofileMod mod=arrayList.get(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.cust_searchresult, null);
        }
        TextView cname,cno,email,address,city,zipcode;
        Button viewProfile;

        cname=(TextView)convertView.findViewById(R.id.comp_name);
        cno=(TextView)convertView.findViewById(R.id.comp_no);
        email=(TextView)convertView.findViewById(R.id.email);
        address=(TextView)convertView.findViewById(R.id.address);
        city=(TextView)convertView.findViewById(R.id.city);
        zipcode=(TextView)convertView.findViewById(R.id.zip);

        viewProfile=(Button)convertView.findViewById(R.id.viewProfile);

        cname.setText(mod.getCname());
        cno.setText(mod.getCnum());
        email.setText(mod.getEmail());
        address.setText(mod.getAdd());
        city.setText(mod.getCity());
        zipcode.setText(mod.getZip());

        return convertView;
    }
}
