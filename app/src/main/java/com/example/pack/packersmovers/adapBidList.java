package com.example.pack.packersmovers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pack.packersmovers.model.bidList;

import java.util.ArrayList;

/**
 * Created by root on 30/3/16.
 */
public class adapBidList extends BaseAdapter {
    private Context context;
    ArrayList<bidList> arrayList;

    public adapBidList(Context context, ArrayList<bidList> arrayList) {
        this.context = context;
        this.arrayList=arrayList;
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
        final bidList bl=arrayList.get(position);

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.cust_packer_bid_list, null);
        }
        TextView cname,amount;

        cname=(TextView)convertView.findViewById(R.id.compName);
        amount=(TextView)convertView.findViewById(R.id.bidValue);

        cname.setText(bl.getCname());
        amount.setText(bl.getAmount());

        return convertView;
    }
}
