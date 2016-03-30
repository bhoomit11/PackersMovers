package com.example.pack.packersmovers;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.pack.packersmovers.R;
import com.example.pack.packersmovers.model.bidList;
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

public class packersBidsList extends AppCompatActivity {

    ListView lv;

    static InputStream is=null;
    static String json="";

    int postid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postid=getIntent().getExtras().getInt("postid");
        setContentView(R.layout.activity_packers_bids_list);
        lv=(ListView)findViewById(R.id.bidlist);
    }
    public class asyncresult extends AsyncTask
    {
        String Jsonurl="http://192.168.1.185/packermover/searchmover.php";
        ArrayList<bidList> al=new ArrayList<bidList>();
        @Override
        protected Object doInBackground(Object[] params) {

            ArrayList<NameValuePair> tempal=new ArrayList<NameValuePair>();

            tempal.add(new BasicNameValuePair("postid",String.valueOf(postid)));

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
                Log.e("Object: ", json);
                JSONArray array=object.getJSONArray("moverprofile");
                for(int i=0;i<array.length();i++)
                {
                    bidList pro=new bidList();
                    JSONObject c=array.getJSONObject(i);


                    al.add(pro);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {

            adapBidList ps = new adapBidList(getApplicationContext(),al);
            lv.setAdapter(ps);

            if(json.trim().equals("norecord"))
            {

            }
            super.onPostExecute(o);
        }
    }
}
