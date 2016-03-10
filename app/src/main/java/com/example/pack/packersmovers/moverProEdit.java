package com.example.pack.packersmovers;

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
    TextView usr,email,name,no,add,city,zip;
    EditText oldpass,newpass,cpass;
    String json="";
    static InputStream is= null;
    ListView ls;
    String h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mover_pro_edit);
        usr=(TextView)findViewById(R.id.useredt);
        email=(TextView)findViewById(R.id.emailedt);
        name=(TextView)findViewById(R.id.cnameedt);
        no=(TextView)findViewById(R.id.cnoet);
        add=(TextView)findViewById(R.id.caddedt);
        city=(TextView)findViewById(R.id.ctedt);
        zip=(TextView)findViewById(R.id.zipedt);
    }
}
