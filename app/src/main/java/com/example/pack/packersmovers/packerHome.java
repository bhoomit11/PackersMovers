package com.example.pack.packersmovers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class packerHome extends AppCompatActivity {
    SessionManager session;

    TextView head;
    int mYear, mMonth, mDay;
    TextView datepick,notification;
    Spinner typeselector;
    Button edit,newpost,actvpost,search,dealdone,inc,dec,logout;
    String Jsonurl="";

    Spinner itype;
    TextView qty,shiftdate;
    EditText src,dst,msgtxt;
    Button cancel,submit;

    int tempID=0;

    Dialog dialog;
    String h;

    static InputStream is=null;
    static String json="";
    static JSONObject jobj=null;

    String[] itemtype={"Furniture","Electronics","Cartons","Kitchen","Fitness Gear","Vehicle","Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packer_home);

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();
        h = user.get(SessionManager.KEY_NAME);

        notification=(TextView)findViewById(R.id.textNoti);
        edit=(Button)findViewById(R.id.edtbtn);
        newpost=(Button)findViewById(R.id.postbtn);
        actvpost=(Button)findViewById(R.id.actvbtn);
        search=(Button)findViewById(R.id.srchbtn);
        dealdone=(Button)findViewById(R.id.fdbckbtn);
        logout=(Button)findViewById(R.id.logout);
        head = (TextView) findViewById(R.id.phomehead);
        head.setText("Welcome " + h);
        notification.setVisibility(View.GONE);

        final ArrayAdapter<String> adapter;
        adapter=new ArrayAdapter<String>(packerHome.this,android.R.layout.simple_spinner_dropdown_item,itemtype);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(packerHome.this,packerProEdit.class);
                startActivity(i);
            }
        });

        dialog = new Dialog(packerHome.this);
        dialog.setContentView(R.layout.custdialog);
        dialog.setTitle("Shifting Info: ");
        datepick = (TextView) dialog.findViewById(R.id.datepick);
        typeselector=(Spinner) dialog.findViewById(R.id.itemtype);
        typeselector.setAdapter(adapter);

        //Bindind of widgets of dialog custam view
        cancel = (Button) dialog.findViewById(R.id.disbtn);
        submit=(Button) dialog.findViewById(R.id.submit);
        itype=(Spinner)dialog.findViewById(R.id.itemtype);
        qty=(TextView)dialog.findViewById(R.id.integer_number);
        src=(EditText)dialog.findViewById(R.id.srcet);
        dst=(EditText)dialog.findViewById(R.id.dstet);
        msgtxt=(EditText)dialog.findViewById(R.id.msget);
        shiftdate=(TextView)dialog.findViewById(R.id.datepick);
        inc=(Button)dialog.findViewById(R.id.inc);
        dec=(Button)dialog.findViewById(R.id.dec);

        if(getIntent().getExtras()!=null)
        {
            if(getIntent().getExtras().getString("act").equals("post")) {
                dialog.show();
                int spinnerPosition = adapter.getPosition(getIntent().getStringExtra("type"));
                itype.setSelection(spinnerPosition);
                qty.setText(getIntent().getStringExtra("qty"));
                src.setText(getIntent().getStringExtra("src"));
                dst.setText(getIntent().getStringExtra("dst"));
                msgtxt.setText(getIntent().getStringExtra("msg"));
                shiftdate.setText(getIntent().getStringExtra("sdate"));
                tempID=getIntent().getExtras().getInt("ID");

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Jsonurl = "http://192.168.1.175/packermover/updatepost.php";
                        AsyncDemo ad = new AsyncDemo();
                        ad.execute();
                    }
                });
            }
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=Integer.parseInt(qty.getText().toString());
                i++;
                qty.setText(String.valueOf(i));
            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=Integer.parseInt(qty.getText().toString());
                if(i>1) {
                    i--;
                }
                qty.setText(String.valueOf(i));
            }
        });

        datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(packerHome.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                datepick.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        newpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itype.setSelection(0);
                qty.setText("1");
                src.setText("");
                dst.setText("");
                msgtxt.setText("");
                shiftdate.setText("Pick A Date");

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Jsonurl = "http://192.168.1.175/packermover/insertpost.php";
                        AsyncDemo ad = new AsyncDemo();
                        ad.execute();
                    }
                });
                dialog.show();
            }
        });

        actvpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(packerHome.this,packerpostlist.class);
                startActivity(i);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(packerHome.this,packer_search.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });
    }

    class AsyncDemo extends AsyncTask<String, Void, String>
    {
        private Dialog loadingDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(packerHome.this, "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> al=new ArrayList<NameValuePair>();

            Calendar c = Calendar.getInstance();
            String cdate = c.get(Calendar.DATE) +"-"+ c.get(Calendar.MONTH) +"-"+ c.get(Calendar.YEAR) +" "+ c.get(Calendar.HOUR) +":"+ c.get(Calendar.MINUTE);

            al.add(new BasicNameValuePair("uname",h));
            al.add(new BasicNameValuePair("ID",String.valueOf(tempID).trim()));
            al.add(new BasicNameValuePair("itype",itype.getSelectedItem().toString()));
            al.add(new BasicNameValuePair("qty",qty.getText().toString()));
            al.add(new BasicNameValuePair("src",src.getText().toString()));
            al.add(new BasicNameValuePair("dst",dst.getText().toString()));
            al.add(new BasicNameValuePair("msg",msgtxt.getText().toString()));
            al.add(new BasicNameValuePair("date",datepick.getText().toString()));
            al.add(new BasicNameValuePair("cdate",cdate));

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
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loadingDialog.dismiss();
            String s = result.trim();
            if (s.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(),"Details Posted Successfully!",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
            else if (s.equalsIgnoreCase("failure"));
            {
                Toast.makeText(getApplicationContext(),"Oops Something Wrong! Try Again!",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }
    }
}
