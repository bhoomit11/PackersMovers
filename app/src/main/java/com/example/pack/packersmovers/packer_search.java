package com.example.pack.packersmovers;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pack.packersmovers.model.mprofileMod;

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

public class packer_search extends AppCompatActivity {

    static InputStream is=null;
    static String json="";
    static JSONObject jobj=null;

    AutoCompleteTextView atv;
    ImageButton search;

    String cityname;

    ListView moverList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packer_search);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
//        LayoutInflater mInflater = LayoutInflater.from(movers_bidpost.this);

        View customView = getLayoutInflater().inflate(R.layout.cust_actionbar, null);
        mActionBar.setCustomView(customView);
        mActionBar.setDisplayShowCustomEnabled(true);
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        moverList=(ListView)findViewById(R.id.moverslist);
        atv=(AutoCompleteTextView)customView.findViewById(R.id.autoTV);
        search=(ImageButton)customView.findViewById(R.id.searchbtn);
        atv.setHint("Enter City Name");

        Asyncsearch asyncsearch=new Asyncsearch();
        asyncsearch.execute();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityname=atv.getText().toString();
                asyncresult asr=new asyncresult();
                asr.execute();
            }
        });
    }
    class Asyncsearch extends AsyncTask
    {
        String Jsonurl="http://192.168.1.185/packermover/autodata.php";
        ArrayList<String> al=new ArrayList<String>();
        @Override
        protected Object doInBackground(Object[] params) {

            DefaultHttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(Jsonurl);

            try
            {
                HttpResponse httpResponse=httpClient.execute(httpPost);

                HttpEntity httpEntity=httpResponse.getEntity();

                is=httpEntity.getContent();

            }
            catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try
            {
                BufferedReader reader=new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb=new StringBuilder();
                String line="";

                while((line=reader.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                is.close();
                json=sb.toString();
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                JSONObject object=new JSONObject(json);
                Log.e("Object: ",json);
                JSONArray array=object.getJSONArray("city");
                for(int i=0;i<array.length();i++)
                {
                    JSONObject c=array.getJSONObject(i);
                    String name=c.getString("city");
                    al.add(name);
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {

            ArrayAdapter<String> adp=new ArrayAdapter<String>(packer_search.this,android.R.layout.simple_spinner_dropdown_item,al);
            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            atv.setThreshold(1);
            atv.setAdapter(adp);

            super.onPostExecute(o);
        }
    }

    public class asyncresult extends AsyncTask
    {
        String Jsonurl="http://192.168.1.185/packermover/searchmover.php";
        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            ArrayList<mprofileMod> al=new ArrayList<mprofileMod>();

            ArrayList<NameValuePair> tempal=new ArrayList<NameValuePair>();

            tempal.add(new BasicNameValuePair("city",cityname));

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

                is=httpEntity.getContent();

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try
            {
                BufferedReader reader=new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb=new StringBuilder();
                String line="";
                while((line=reader.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                is.close();
                json=sb.toString();
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                JSONObject object=new JSONObject(json);
                JSONArray array=object.getJSONArray("moverprofile");
                for(int i=0;i<array.length();i++)
                {
                    mprofileMod pro=new mprofileMod();
                    JSONObject c=array.getJSONObject(i);

                    String cname=c.getString("comp_name");
                    String cno=c.getString("cno");
                    String email=c.getString("email");
                    String address=c.getString("address");
                    String city=c.getString("Shifting Date");
                    String zip=c.getString("currentdate");

                    pro.setCname(cname);
                    pro.setCnum(cno);
                    pro.setEmail(email);
                    pro.setAdd(address);
                    pro.setCity(city);
                    pro.setZip(zip);

                    al.add(pro);
                }

                adapPackerSearch ps = new adapPackerSearch(getApplicationContext(),al);
                moverList.setAdapter(ps);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            if(json.trim().equals("norecord"))
            {

            }
            super.onPostExecute(o);
        }
    }

}
