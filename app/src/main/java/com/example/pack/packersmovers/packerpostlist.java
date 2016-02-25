package com.example.pack.packersmovers;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.example.pack.packersmovers.model.packerpost;

public class packerpostlist extends AppCompatActivity {
    String json="";
    static InputStream is= null;
    ListView ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packerpostlist);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ls=(ListView)findViewById(R.id.list1);
        asyncDemo ad=new asyncDemo();
        ad.execute();
    }

    public class asyncDemo extends AsyncTask
    {
        String Jsonurl="http://192.168.1.134/packermover/postlist.php";
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
                JSONArray array=object.getJSONArray("user_info");
                for(int i=0;i<array.length();i++)
                {
                    packerpost ppost=new packerpost();
                    JSONObject c=array.getJSONObject(i);

                    String type=c.getString("itemtype");
                    int qty=c.getInt("itemqty");
                    String src=c.getString("Source");
                    String dst=c.getString("destination");
                    String msg=c.getString("Message");
                    String sdate=c.getString("Shifting Date");
                    String cdate=c.getString("currentdate");

                    ppost.setType(type);
                    ppost.setQty(qty);
                    ppost.setSrc(src);
                    ppost.setDst(dst);
                    ppost.setMsg(msg);
                    ppost.setSdate(sdate);
                    ppost.setCdate(cdate);

                    al.add(ppost);
                }

                postadapter pa = new postadapter(getApplicationContext(),al);
                ls.setAdapter(pa);

            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(o);

        }
    }
}
