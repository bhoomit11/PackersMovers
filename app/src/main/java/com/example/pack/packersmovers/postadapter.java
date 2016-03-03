package com.example.pack.packersmovers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import com.example.pack.packersmovers.model.packerpost;

/**
 * Created by root on 24/2/16.
 */
public class postadapter extends BaseAdapter {
    private Context context;
    ArrayList<packerpost> arrayList;
    String head;

    public postadapter(Context context, ArrayList<packerpost> arrayList, String head) {
        this.context = context;
        this.arrayList = arrayList;
        this.head = head;
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
        final packerpost post=arrayList.get(position);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.postlist, null);
        }
        TextView uname,type,qty,src,dst,msg,sdate,cdate;
        Button edit,delete;

        edit=(Button)convertView.findViewById(R.id.edtbtn);
        delete=(Button)convertView.findViewById(R.id.delbtn);
        uname=(TextView) convertView.findViewById(R.id.user);
        type=(TextView) convertView.findViewById(R.id.type);
        qty=(TextView) convertView.findViewById(R.id.qty);
        src=(TextView) convertView.findViewById(R.id.src);
        dst=(TextView) convertView.findViewById(R.id.dst);
        msg=(TextView) convertView.findViewById(R.id.msgtxt);
        sdate=(TextView) convertView.findViewById(R.id.date);
        cdate=(TextView) convertView.findViewById(R.id.datetime);

        uname.setText(post.getUname());
        type.setText(post.getType());
        qty.setText(String.valueOf(post.getQty()));
        src.setText(post.getSrc());
        dst.setText(post.getDst());
        msg.setText(post.getMsg());
        sdate.setText(post.getSdate());
        cdate.setText(post.getCdate());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(context,packerHome.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                i1.putExtra("ID",post.getId());
                i1.putExtra("act","post");
                i1.putExtra("head", head);
                i1.putExtra("type", post.getType());
                i1.putExtra("qty", String.valueOf(post.getQty()));
                i1.putExtra("src", post.getSrc());
                i1.putExtra("dst", post.getDst());
                i1.putExtra("msg", post.getMsg());
                i1.putExtra("sdate", post.getSdate());

                v.getContext().startActivity(i1);
            }
        });

        return convertView;
    }
}
