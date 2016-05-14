package com.gunsandrocket.ua.cactus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.gunsandrocket.kpievents.R;

/**
 * Created by dmytro on 18.04.16.
 */
public class ChooseAddMethodActivity extends Activity {

    @Bind(R.id.btn_add)
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_method);
        ButterKnife.bind(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseAddMethodActivity.this, AddEventActivity.class));
                finish();
            }
        });
    }
}

