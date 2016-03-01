package com.example.pack.packersmovers;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class vehiclemanage extends AppCompatActivity {
    ListView vehiclelist;
    Button addnew;
    Dialog dialog;
    ArrayAdapter<String> vadapter,cadapter;
    String[] vtype={"Car & Light Trucks","Trailers","CarVans","Heavy Trucks"};
    String[] cond={"Below Average","Average","Good","Very Good"};

    Spinner type,cndtn;
    EditText vname,vnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiclemanage);
        dialog=new Dialog(vehiclemanage.this);
        vadapter=new ArrayAdapter<String>(vehiclemanage.this,android.R.layout.simple_spinner_dropdown_item,vtype);
        cadapter=new ArrayAdapter<String>(vehiclemanage.this,android.R.layout.simple_spinner_dropdown_item,cond);

        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setContentView(R.layout.vehiclelist);
                dialog.setTitle("Add Vehical:");

                vname=(EditText)dialog.findViewById(R.id.vname);
                vnum=(EditText)dialog.findViewById(R.id.vnum);
                type=(Spinner)dialog.findViewById(R.id.vtype);
                cndtn=(Spinner)dialog.findViewById(R.id.cond);

                type.setAdapter(vadapter);
                cndtn.setAdapter(cadapter);
            }
        });
    }
}
