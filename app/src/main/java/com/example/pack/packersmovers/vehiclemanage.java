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
    Button sub,can;
    Dialog dialog;
    ArrayAdapter<String> vadapter,cadapter;
    String[] vtype={"Choose Vehicle Type","Car & Light Trucks","Trailers","CarVans","Heavy Trucks"};
    String[] cond={"Vehicle Condition!","Very Good","Good","Average","Below Average"};

    Spinner type,cndtn;
    EditText vname,vnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiclemanage);

        addnew=(Button)findViewById(R.id.addvehicle);

        dialog=new Dialog(vehiclemanage.this);
        vadapter=new ArrayAdapter<String>(vehiclemanage.this,android.R.layout.simple_spinner_dropdown_item,vtype){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                {
                    return false;
                }
                else {
                    return super.isEnabled(position);
                }
            }
        };

        cadapter=new ArrayAdapter<String>(vehiclemanage.this,android.R.layout.simple_spinner_dropdown_item,cond){
            @Override
            public boolean isEnabled(int position) {
                if(position==0)
                {
                    return false;
                }
                else {
                    return super.isEnabled(position);
                }
            }
        };

        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setContentView(R.layout.vehiclelist);
                dialog.setTitle("Add Vehical:");

                vname=(EditText)dialog.findViewById(R.id.vname);
                vnum=(EditText)dialog.findViewById(R.id.vnum);
                type=(Spinner)dialog.findViewById(R.id.vtype);
                cndtn=(Spinner)dialog.findViewById(R.id.cond);
                sub=(Button)dialog.findViewById(R.id.submit);
                can=(Button)dialog.findViewById(R.id.cancel);

                type.setAdapter(vadapter);
                cndtn.setAdapter(cadapter);

                can.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
}
