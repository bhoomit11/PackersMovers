package com.example.pack.packersmovers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button pbtn,mbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pbtn=(Button)findViewById(R.id.packbtn);
        mbtn=(Button)findViewById(R.id.movebtn);
        pbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,loginActivity.class);
                i.putExtra("user","Packers");
                startActivity(i);
            }
        });
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,loginActivity.class);
                i.putExtra("user","Movers");
                startActivity(i);
            }
        });
    }
}
