package com.example.pack.packersmovers;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class packerProEdit extends AppCompatActivity {
    String username;
//    String unm, fnm,em,pwd,cno,add;
//    int id;

    static InputStream is=null;
    static String json="";
    static JSONObject jobj=null;

    TextView uname,fname,email,cntno,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packer_pro_edit);

        username=getIntent().getExtras().getString("user");

        uname=(TextView)findViewById(R.id.useredt);
        fname=(TextView)findViewById(R.id.fnameedt);
        email=(TextView)findViewById(R.id.emailedt);
        cntno=(TextView)findViewById(R.id.cnoedt);
        address=(TextView)findViewById(R.id.addedt);

        Asynclogin asynclogin=new Asynclogin();
        asynclogin.execute();

//        Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();
//
//        Toast.makeText(getApplicationContext(), unm + "\n" + fnm + "\n" + em + "\n" + cno + "\n" + add,Toast.LENGTH_LONG).show();

//        uname.setText(unm);
//        fname.setText(fnm);
//        email.setText(em);
//        cntno.setText(cno);
//        address.setText(add);

    }

    class Asynclogin extends AsyncTask<String, Void, String>
    {

        String Jsonurl="http://192.168.1.186/packermover/showprofile.php";
        private Dialog loadingDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(packerProEdit.this, "Please wait", "Loading...");
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
                json=sb.toString();
                is.close();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                jobj=new JSONObject(json);
                JSONObject c=new JSONObject(json);
//                JSONArray array=jobj.getJSONArray("profile");
//
////                for(int i=0;i<array.length();i++)
////                {
//                    JSONObject c=array.getJSONObject(0);
//
//                    id=c.getInt("pid");
//                    unm=c.getString("uname");
//                    fnm=c.getString("fullname");
//                    em=c.getString("email");
//                    pwd=c.getString("pass");
//                    cno=c.getString("contactNo");
//                    add=c.getString("Address");
                uname.setText(c.getString("1"));
                fname.setText(c.getString("2"));
                email.setText(c.getString("3"));
                cntno.setText(c.getString("5"));
                address.setText(c.getString("6"));
//                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result.trim();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loadingDialog.dismiss();
            String s = result;
        }
    }
}
