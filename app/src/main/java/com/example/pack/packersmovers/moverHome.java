package com.example.pack.packersmovers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class moverHome extends AppCompatActivity {
    TextView head;
    Button mngvehicle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mover_home);
        String h=getIntent().getExtras().getString("user");
        head=(TextView)findViewById(R.id.mhomehead);
        head.setText("Welcome "+h);
        mngvehicle=(Button)findViewById(R.id.managevehicle);

        mngvehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(moverHome.this,vehiclemanage.class);
                startActivity(i);
            }
        });
    }
}
