package com.example.pack.packersmovers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class moverHome extends AppCompatActivity {
    TextView head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mover_home);
        String h=getIntent().getExtras().getString("user");
        head=(TextView)findViewById(R.id.mhomehead);
        head.setText("Welcome "+h);

    }
}
