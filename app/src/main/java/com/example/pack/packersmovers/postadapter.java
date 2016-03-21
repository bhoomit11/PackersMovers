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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

/**
 * Created by root on 24/2/16.
 */
public class postadapter extends BaseAdapter {
    private Context context;
    ArrayList<packerpost> arrayList;
    String head;
    Activity parentactivity;

    static InputStream is;
    static String json;
    static JSONObject jobj;

    int id;
    AlertDialog alertDialog;

    public postadapter(Context context, ArrayList<packerpost> arrayList, Activity parentactivity) {
        this.context = context;
        this.arrayList = arrayList;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        final packerpost post=arrayList.get(position);
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.postlist, null);
        }
        TextView uname,type,qty,src,dst,msg,sdate,cdate,noti;
        Button edit,delete;

        edit=(Button) convertView.findViewById(R.id.edtbtn);
        delete=(Button) convertView.findViewById(R.id.delbtn);
        uname=(TextView) convertView.findViewById(R.id.user);
        type=(TextView) convertView.findViewById(R.id.type);
        qty=(TextView) convertView.findViewById(R.id.qty);
        src=(TextView) convertView.findViewById(R.id.src);
        dst=(TextView) convertView.findViewById(R.id.dst);
        msg=(TextView) convertView.findViewById(R.id.msgtxt);
        sdate=(TextView) convertView.findViewById(R.id.date);
        cdate=(TextView) convertView.findViewById(R.id.datetime);
        noti=(TextView) convertView.findViewById(R.id.notify);

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
                Intent i1 = new Intent(context, packerHome.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                i1.putExtra("ID", post.getId());
                i1.putExtra("act", "post");
                i1.putExtra("type", post.getType());
                i1.putExtra("qty", String.valueOf(post.getQty()));
                i1.putExtra("src", post.getSrc());
                i1.putExtra("dst", post.getDst());
                i1.putExtra("msg", post.getMsg());
                i1.putExtra("sdate", post.getSdate());

                v.getContext().startActivity(i1);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id=post.getId();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parentactivity);

                alertDialogBuilder.setMessage("Are you sure you want to delete this post?");

                alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        AsyncJson aj = new AsyncJson();
                        aj.execute();
                        Toast.makeText(parentactivity, "Post Deleted!", Toast.LENGTH_LONG).show();
                        parentactivity.recreate();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        return convertView;
    }
    private class AsyncJson extends AsyncTask {

        String JsonUrl = "http://192.168.1.162/packermover/deletepost.php";

        @Override
        protected Object doInBackground(Object[] params) {

            ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();

            arrayList.add(new BasicNameValuePair("id",String.valueOf(id)));

            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(JsonUrl);

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(arrayList));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();

                is = entity.getContent();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");
                }

                is.close();

                json = sb.toString();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                jobj = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jobj;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

        }
    }
}
