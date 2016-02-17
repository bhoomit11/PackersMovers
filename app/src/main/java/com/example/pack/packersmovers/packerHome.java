package com.example.pack.packersmovers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class packerHome extends AppCompatActivity {

    TextView head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packer_home);
        String h=getIntent().getExtras().getString("user");
        head=(TextView)findViewById(R.id.phomehead);
        head.setText("Welcome " + h);
    }
}
