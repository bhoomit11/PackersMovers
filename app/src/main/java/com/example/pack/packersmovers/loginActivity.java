package com.example.pack.packersmovers;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pack.packersmovers.model.NavDrawerItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;

public class loginActivity extends AppCompatActivity
{
    static InputStream is=null;
    static String json="";
    static JSONObject jobj=null;

    EditText uname,pass;
    String usr,pwd;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    // nav drawer title
    private CharSequence mDrawerTitle;
    // used to store app title
    private CharSequence mTitle;
    // slide menu items
    private String[] navMenuTitles;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavigationDrawerFragment adapter;

    Button reg,login;
    TextView title;
    public String h1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Window window=getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.parseColor("#00897b"));

        uname=(EditText)findViewById(R.id.etuser);
        pass=(EditText)findViewById(R.id.etpass);

        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        navDrawerItems = new ArrayList<NavDrawerItem>();

        reg=(Button)findViewById(R.id.regbtn);
        login=(Button)findViewById(R.id.loginbtn);
        title=(TextView)findViewById(R.id.loginhead);

        h1 = getIntent().getExtras().getString("user");
        title.setText(h1 + " Login");

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (h1.equals("Packers")) {
                    Intent i = new Intent(loginActivity.this, packersReg.class);
                    startActivity(i);
                }
                if (h1.equals("Movers")) {
                    Intent i = new Intent(loginActivity.this, MoversReg.class);
                    startActivity(i);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usr = uname.getText().toString();
                pwd = pass.getText().toString();
                Asynclogin asynclogin = new Asynclogin();
                asynclogin.execute();
            }
        });

        // adding nav drawer items to array
        navDrawerItems.add(new NavDrawerItem("GO Home"));
        navDrawerItems.add(new NavDrawerItem("Exit"));


        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        // setting the nav drawer list adapter
        adapter = new NavigationDrawerFragment(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff009688")));

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,//nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
//        android.app.Fragment fragment = null;
        switch (position) {
            case 0:
                Intent i=new Intent(loginActivity.this,MainActivity.class);
                startActivity(i);
                break;
            case 1:
                break;
            default:
                break;
        }

}

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    class Asynclogin extends AsyncTask<String, Void, String>
    {

        String Jsonurl;
        private Dialog loadingDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(loginActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(String... params) {

            if (h1.equals("Packers")) {
                Jsonurl = "http://192.168.1.186/packermover/pack_login.php";
            }
            else if(h1.equals("Movers")){
                Jsonurl = "http://192.168.1.186/packermover/move_login.php";
            }

            ArrayList<NameValuePair> al=new ArrayList<NameValuePair>();
            String result=null;


            al.add(new BasicNameValuePair("usr",usr));
            al.add(new BasicNameValuePair("pwd",pwd));

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
//                json=sb.toString();
//                jobj=new JSONObject(json);
                is.close();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result.trim();
        }

        @Override
        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
            loadingDialog.dismiss();
            String s = result.trim();
            if(s.equalsIgnoreCase("success")){
                if (h1.equals("Packers")) {
                    Intent i = new Intent(loginActivity.this, packerHome.class);
                    i.putExtra("act","log");
                    i.putExtra("user",uname.getText().toString());
                    startActivity(i);
                    Toast.makeText(getApplicationContext(),"Successfully Logged In!",Toast.LENGTH_LONG).show();

                }
                if (h1.equals("Movers")) {
                    Intent i = new Intent(loginActivity.this, moverHome.class);
                    i.putExtra("user",uname.getText().toString());
                    startActivity(i);
                    Toast.makeText(getApplicationContext(),"Successfully Logged In!",Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
            }
        }
    }
}
