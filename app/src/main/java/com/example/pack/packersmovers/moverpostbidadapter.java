package com.example.pack.packersmovers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pack.packersmovers.model.packerpost;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by root on 18/3/16.
 */
public class moverpostbidadapter extends BaseAdapter {
    private Context context;
    ArrayList<packerpost> arrayList;
    String head;

    static InputStream is;
    static String json;
    static JSONObject jobj;

    int id;

    public moverpostbidadapter(Context context, ArrayList<packerpost> arrayList) {
        this.context = context;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        final packerpost post=arrayList.get(position);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.moverbidpast, null);
        }
        TextView uname,type,qty,src,dst,msg,sdate,cdate,userbid;
        Button bid;

        bid=(Button) convertView.findViewById(R.id.postbtn);
        userbid=(TextView) convertView.findViewById(R.id.userbid);
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

        return convertView;
    }
}
