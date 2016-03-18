package com.example.pack.packersmovers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class moverHome extends AppCompatActivity {
    TextView head;
    Button mngvehicle,bidlist,profile,logout;
    String h;

    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mover_home);
//        h=getIntent().getExtras().getString("user");

        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = session.getUserDetails();
        h = user.get(SessionManager.KEY_NAME);

        head=(TextView)findViewById(R.id.mhomehead);
        head.setText("Welcome "+h);
        mngvehicle=(Button)findViewById(R.id.managevehicle);
        bidlist=(Button)findViewById(R.id.startbid);
        profile=(Button)findViewById(R.id.mngprofile);
        logout=(Button)findViewById(R.id.logout);

        mngvehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(moverHome.this,vehiclemanage.class);
                startActivity(i);
            }
        });
        bidlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(moverHome.this,movers_bidpost.class);
                startActivity(i);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(moverHome.this,moverProEdit.class);
                i.putExtra("user",h);
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
}
