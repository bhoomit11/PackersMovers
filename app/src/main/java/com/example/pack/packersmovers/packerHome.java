package com.example.pack.packersmovers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class packerHome extends AppCompatActivity {

    TextView head;
    int mYear, mMonth, mDay;
    TextView datepick;
    Spinner typeselector;
    Button edit,newpost,actvpost,search,dealdone;
    String[] itemtype={"Furniture","Electronics","Cartons","Kitchen","Fitness Gear","Vehicle","Other"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packer_home);
        edit=(Button)findViewById(R.id.edtbtn);
        newpost=(Button)findViewById(R.id.postbtn);
        actvpost=(Button)findViewById(R.id.actvbtn);
        search=(Button)findViewById(R.id.srchbtn);
        dealdone=(Button)findViewById(R.id.fdbckbtn);
        String h=getIntent().getExtras().getString("user");
        head=(TextView)findViewById(R.id.phomehead);
        head.setText("Welcome " + h);
        final ArrayAdapter<String> adapter;
        adapter=new ArrayAdapter<String>(packerHome.this,android.R.layout.simple_spinner_item,itemtype);
        newpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(packerHome.this);


                dialog.setContentView(R.layout.custdialog);
                dialog.setTitle("Shifting Info: ");

                datepick = (TextView) dialog.findViewById(R.id.datepick);
                typeselector=(Spinner) dialog.findViewById(R.id.itemtype);
                typeselector.setAdapter(adapter);

                Button cancel = (Button) dialog.findViewById(R.id.disbtn);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
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
                dialog.show();
            }
        });

    }
}
