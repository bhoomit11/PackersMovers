package com.example.pack.packersmovers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;

public class checkActivity extends AppCompatActivity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        session = new SessionManager(getApplicationContext());

        session.checkLogin();

        if(session.isLoggedIn()) {
            HashMap<String, String> user = session.getUserDetails();

            String type = user.get(SessionManager.KEY_TYPE);
            if (type.equals("Packers")) {
                Intent i = new Intent(checkActivity.this, packerHome.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            } else if (type.equals("Movers")) {
                Intent i = new Intent(checkActivity.this, moverHome.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        }
    }
}
