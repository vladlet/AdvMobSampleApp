package com.example.advmobile.lection2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int type = intent.getIntExtra("activity_type", 0);
        if (type == 0) {
            finish();
            return;
        }
        String name = intent.getStringExtra("activity_type_name");
        getActionBar().setTitle(name);

        setContentView(type);

        Button button = (Button)findViewById(R.id.backbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               LayoutActivity.this.finish();
            }
        });
    }
}
