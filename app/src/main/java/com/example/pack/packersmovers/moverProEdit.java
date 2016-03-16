package com.example.pack.packersmovers;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

public class moverProEdit extends AppCompatActivity {
    TextView usr,email,name,no,address,city,zip;
    EditText oldpass,newpass,cpass;

    String username;

    static InputStream is=null;
    static String json="";
    static JSONObject jobj=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mover_pro_edit);

        username=getIntent().getExtras().getString("user");

        usr=(TextView)findViewById(R.id.useredt);
        email=(TextView)findViewById(R.id.emailedt);
        name=(TextView)findViewById(R.id.cnameedt);
        no=(TextView)findViewById(R.id.cnoet);
        address=(TextView)findViewById(R.id.caddedt);
        city=(TextView)findViewById(R.id.ctedt);
        zip=(TextView)findViewById(R.id.zipedt);
    }

    class Asynclogin extends AsyncTask<String, Void, String>
    {

        String Jsonurl="http://192.168.1.186/packermover/showprofile.php";
        private Dialog loadingDialog;
        String unm,em,cname,pwd,cno,add,ct,post;
        int id;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(moverProEdit.this, "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(String... params) {


            ArrayList<NameValuePair> al=new ArrayList<NameValuePair>();

            String result=null;

            al.add(new BasicNameValuePair("usr",username));

            DefaultHttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(Jsonurl);

            try{
                httpPost.setEntity(new UrlEncodedFormEntity(al));
            }
            catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }

            try {
                HttpResponse response = httpClient.execute(httpPost);

                HttpEntity entity = response.getEntity();

                is = entity.getContent();
            }

            catch (IOException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line=reader.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                result=sb.toString();
                is.close();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                jobj = new JSONObject(result);
                JSONArray array = jobj.getJSONArray("profile");
//
//               for(int i=0;i<array.length();i++)
//                {
                JSONObject c = array.getJSONObject(0);

                unm = c.getString("uname");
                em = c.getString("email");
                pwd = c.getString("pass");
                cname = c.getString("comp_name");
                cno = c.getString("cno");
                add = c.getString("address");
                ct= c.getString("city");
                post = c.getString("zip");
//            }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result.trim();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            usr.setText(unm);
            email.setText(em);
            name.setText(cname);
            no.setText(cno);
            address.setText(add);
            city.setText(ct);
            zip.setText(post);

            loadingDialog.dismiss();
            String s = result;
        }
    }
}
