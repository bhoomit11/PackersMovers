package com.example.pack.packersmovers;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pack.packersmovers.model.packerpost;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
import java.util.HashMap;

import com.example.pack.packersmovers.model.vehiclelist;

public class vehiclemanage extends AppCompatActivity {
    ListView vlist;
    Button addnew;
    Button sub,can;
    Dialog dialog;
    ArrayAdapter<String> vadapter;
    String[] vtype={"Choose Vehicle Type","Car & Light Trucks","Trailers","CarVans","Heavy Trucks"};

    Spinner type;
    EditText vname,vnum;

    static InputStream is;
    static String json;

    static InputStream is1;
    static String json1;

    String vehicle_name,vehicle_type;
    String username;

    SessionManager session;

    adapVehicleList vla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiclemanage);

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();
        username = user.get(SessionManager.KEY_NAME);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        vlist=(ListView)findViewById(R.id.vehiclelist);
        addnew=(Button)findViewById(R.id.addvehicle);
        dialog=new Dialog(vehiclemanage.this);

        vadapter=new ArrayAdapter<String>(vehiclemanage.this,android.R.layout.simple_spinner_dropdown_item,vtype){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                {
                    return false;
                }
                else {
                    return super.isEnabled(position);
                }
            }
        };

        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setContentView(R.layout.vehicledialog);
                dialog.setTitle("Add Vehical:");

                vname=(EditText)dialog.findViewById(R.id.vname);
                type=(Spinner)dialog.findViewById(R.id.vtype);
                sub=(Button)dialog.findViewById(R.id.submit);
                can=(Button)dialog.findViewById(R.id.cancel);

                type.setAdapter(vadapter);

                sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vehicle_name=vname.getText().toString();
                        vehicle_type=type.getSelectedItem().toString();
                        AsyncJson aj=new AsyncJson();
                        aj.execute();
                    }
                });
                can.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        asyncvehicle av=new asyncvehicle();
        av.execute();
    }
    private class AsyncJson extends AsyncTask<String, Void, String> {

        String JsonUrl = "http://192.168.1.175/packermover/vehicle_insert.php";

        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> arrayList = new ArrayList<NameValuePair>();

            arrayList.add(new BasicNameValuePair("user",username));
            arrayList.add(new BasicNameValuePair("vname",vehicle_name));
            arrayList.add(new BasicNameValuePair("vtype",vehicle_type));

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
            vehiclemanage.this.recreate();
        }
    }
    public class asyncvehicle extends AsyncTask<String, Void, String>
    {
        String Jsonurl="http://192.168.1.175/packermover/vehicle_list.php";
        private Dialog loadingDialog;
        @Override
        protected String doInBackground(String... params) {

            ArrayList<vehiclelist> al=new ArrayList<vehiclelist>();

            ArrayList<NameValuePair> tempal=new ArrayList<NameValuePair>();

            tempal.add(new BasicNameValuePair("uname",username));

            DefaultHttpClient httpClient=new DefaultHttpClient();

            HttpPost httpPost=new HttpPost(Jsonurl);

            try{
                httpPost.setEntity(new UrlEncodedFormEntity(tempal));
            }
            catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }

            try
            {
                HttpResponse httpResponse=httpClient.execute(httpPost);

                HttpEntity httpEntity=httpResponse.getEntity();

                is1=httpEntity.getContent();

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try
            {
                BufferedReader reader=new BufferedReader(new InputStreamReader(is1,"iso-8859-1"),8);
                StringBuilder sb=new StringBuilder();
                String line="";
                while((line=reader.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                is1.close();
                json1=sb.toString();
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                JSONObject object=new JSONObject(json1);
                JSONArray array=object.getJSONArray("vehicle");
                for(int i=0;i<array.length();i++)
                {
                    vehiclelist vl=new vehiclelist();
                    JSONObject c=array.getJSONObject(i);

                    String vname=c.getString("vname");
                    String vtype=c.getString("vtype");

                    vl.setVname(vname);
                    vl.setVtype(vtype);

                    al.add(vl);
                }
                vla = new adapVehicleList(getApplicationContext(),al,vehiclemanage.this);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            if(json1.trim().equals("norecord"))
            {

            }
            return json1;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(vehiclemanage.this, "Please wait", "Loading...");
        }

        @Override
        protected void onPostExecute(String o) {
            super.onPostExecute(o);
            vlist.setAdapter(vla);
            loadingDialog.dismiss();
        }
    }
}
