package com.example.pack.packersmovers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONArray;
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

    Activity parentActivity;

    Dialog dialog;

    static InputStream is;
    static String json;
    static JSONObject jobj;

    String mov_uname;
    String amnt,postid;

    int id;
    AlertDialog alertDialog;

    TextView uname,type,qty,src,dst,msg,sdate,cdate,userbid;

    Button bid;

    public moverpostbidadapter(Context context, ArrayList<packerpost> arrayList,Activity parentActivity,String mov_uname) {
        this.context = context;
        this.arrayList = arrayList;
        this.parentActivity=parentActivity;
        this.mov_uname=mov_uname;
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

        bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(parentActivity);
                dialog.setContentView(R.layout.biding_dialog);
                dialog.setTitle("Biding: ");

                final EditText bidvalue;
                Button submit,cancel;

                bidvalue=(EditText)dialog.findViewById(R.id.bidvalue);
                cancel=(Button)dialog.findViewById(R.id.cancelbtn);
                submit=(Button)dialog.findViewById(R.id.postbidbtn);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        amnt=bidvalue.getText().toString();
                        postid=String.valueOf(post.getId());
                        dialog.dismiss();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parentActivity);

                        alertDialogBuilder.setMessage("sure you want to bid "+amnt+" ₹ ?");

                        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                AsyncJson aj = new AsyncJson();
                                aj.execute();
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
            }
        });

        return convertView;
    }
    private class AsyncJson extends AsyncTask<String, Void, String> {

        String JsonUrl = "http://192.168.1.185/packermover/insert_bid.php";

        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();

            arrayList.add(new BasicNameValuePair("postid",postid));
            arrayList.add(new BasicNameValuePair("mover",mov_uname));
            arrayList.add(new BasicNameValuePair("amount",amnt));

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

            return json;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            dialog.dismiss();
            Toast.makeText(parentActivity,"Bid Submitted Successfully!",Toast.LENGTH_LONG).show();
        }
    }

}
