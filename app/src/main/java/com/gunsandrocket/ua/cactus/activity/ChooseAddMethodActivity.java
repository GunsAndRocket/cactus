package com.gunsandrocket.ua.cactus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gunsandrocket.ua.cactus.R;


/**
 * Created by dmytro on 18.04.16.
 */
public class ChooseAddMethodActivity extends Activity {


    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_method);
        btnAdd = (Button)findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseAddMethodActivity.this, AddEventActivity.class));
                finish();
            }
        });
    }
}

