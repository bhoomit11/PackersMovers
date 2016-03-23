package com.example.pack.packersmovers;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

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

public class movers_bidpost extends AppCompatActivity {
    String json="";
    static InputStream is= null;

    String h;
    ListView ls;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movers_bidpost);

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();
        h = user.get(SessionManager.KEY_NAME);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ls=(ListView)findViewById(R.id.ppostlist);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
//        LayoutInflater mInflater = LayoutInflater.from(movers_bidpost.this);

        View customView = getLayoutInflater().inflate(R.layout.cust_actionbar, null);
        mActionBar.setCustomView(customView);
        mActionBar.setDisplayShowCustomEnabled(true);
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        asyncDemo as=new asyncDemo();
        as.execute();
    }

    public class asyncDemo extends AsyncTask
    {
        String Jsonurl="http://192.168.1.175/packermover/moverbidpost.php";
        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            ArrayList<packerpost> al=new ArrayList<packerpost>();

            DefaultHttpClient httpClient=new DefaultHttpClient();

            HttpPost httpPost=new HttpPost(Jsonurl);

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
                JSONArray array=object.getJSONArray("packerspost");
                for(int i=0;i<array.length();i++)
                {
                    packerpost ppost=new packerpost();
                    JSONObject c=array.getJSONObject(i);

                    int id=c.getInt("postID");
                    String uname=c.getString("uname");
                    String type=c.getString("itemtype");
                    int qty=c.getInt("itemqty");
                    String src=c.getString("Source");
                    String dst=c.getString("destination");
                    String msg=c.getString("Message");
                    String sdate=c.getString("Shifting Date");
                    String cdate=c.getString("currentdate");

                    ppost.setId(id);
                    ppost.setUname(uname);
                    ppost.setType(type);
                    ppost.setQty(qty);
                    ppost.setSrc(src);
                    ppost.setDst(dst);
                    ppost.setMsg(msg);
                    ppost.setSdate(sdate);
                    ppost.setCdate(cdate);

                    al.add(ppost);
                }

                moverpostbidadapter ma = new moverpostbidadapter(getApplicationContext(),al,movers_bidpost.this,h);
                ls.setAdapter(ma);

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
