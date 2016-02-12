package com.example.pack.packersmovers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import com.example.pack.packersmovers.model.packerInfo;

public class packersReg extends AppCompatActivity {
    ActionBar actionBar;
    String chkuname;
    static InputStream is=null;
    static String json="";
    static JSONObject jobj=null;
    EditText uname,email,pass,cno,cpass,address;
    Button reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packers_reg);
        uname=(EditText)findViewById(R.id.useret);
        email=(EditText)findViewById(R.id.emailet);
        pass=(EditText)findViewById(R.id.passet);
        cpass=(EditText)findViewById(R.id.cpasset);
        cno=(EditText)findViewById(R.id.cnoet);
        address=(EditText)findViewById(R.id.addet);
        reg=(Button)findViewById(R.id.packbtn);
        actionBar=getSupportActionBar();

        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp;
                temp = cpass.getText().toString();
                if (temp.equals(pass.getText().toString())) {
                    AsyncDemo asyncDemo = new AsyncDemo();
                    asyncDemo.execute();
                    Toast.makeText(getBaseContext(),"Profile Created!",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Password does not match!", Toast.LENGTH_LONG).show();
                    pass.setText("");
                    cpass.setText("");
                    pass.requestFocus();
                }
            }
        });
    }
    class AsyncDemo extends AsyncTask
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                ArrayList<packerInfo> al1=new ArrayList<>();
                JSONObject object=new JSONObject(json);
                JSONArray array=object.getJSONArray("user_info");
                for(int i=0;i<array.length();i++)
                {
                    packerInfo idata=new packerInfo();
                    JSONObject c=array.getJSONObject(i);
                    chkuname=c.getString("uname");
                    idata.setUname(chkuname);
                    al1.add(idata);
                }
                if(al1.contains(uname.getText().toString()))
                {
                    Toast.makeText(getBaseContext(),"username available",Toast.LENGTH_LONG).show();

                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String Jsonurl="http://192.168.1.159/packermover/insert.php";

        @Override
        protected Object doInBackground(Object[] params) {

            ArrayList<NameValuePair> al=new ArrayList<NameValuePair>();

            al.add(new BasicNameValuePair("uname",uname.getText().toString()));
            al.add(new BasicNameValuePair("email",email.getText().toString()));
            al.add(new BasicNameValuePair("pass",pass.getText().toString()));
            al.add(new BasicNameValuePair("cno",cno.getText().toString()));
            al.add(new BasicNameValuePair("address",address.getText().toString()));

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

                is.close();

                json = sb.toString();

                jobj = new JSONObject(json);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jobj;
        }
    }
}
