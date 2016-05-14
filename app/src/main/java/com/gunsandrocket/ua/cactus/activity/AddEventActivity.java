package com.gunsandrocket.ua.cactus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.FileHelper;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.messages.model.QBEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import ua.gunsandrocket.kpievents.R;
import ua.gunsandrocket.kpievents.dao.QbDao;
import ua.gunsandrocket.kpievents.model.Event;
import ua.gunsandrocket.kpievents.notifications.QBPusher;

/**
 * Created by dmytro on 18.04.16.
 */
public class AddEventActivity extends Activity {

    private int PICK_PHOTO = 1;


    @Bind(R.id.btn_add_event)
    Button btnAddEvent;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_descr)
    EditText etDescr;
    @Bind(R.id.et_start_date)
    EditText etStartDate;
    @Bind(R.id.et_vk_link)
    EditText etVkLink;
    @Bind(R.id.et_place)
    EditText etPlace;
    @Bind(R.id.et_tag)
    EditText etTag;
    @Bind(R.id.et_org)
    EditText etOrg;
    @Bind(R.id.btn_pick)
    Button btn_pick;

    InputStream inputPhotoStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);

        btn_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_PHOTO);
            }
        });

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputPhotoStream == null){
                    Toast.makeText(AddEventActivity.this, "Chose icon", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = etName.getText().toString();
                String desc = etDescr.getText().toString();
                String link = etVkLink.getText().toString();
                String place = etPlace.getText().toString();
                String tag = etTag.getText().toString();
                String org = etOrg.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");



                File file = FileHelper.getFileInputStream(inputPhotoStream, "image.jpg", "myFile");

                QBContent.uploadFileTask(file, true, null, new QBEntityCallback<QBFile>() {
                    @Override
                    public void onSuccess(QBFile qbFile, Bundle bundle) {
                        Log.d("TAG", "File send success public url: " + qbFile.getPublicUrl());
                        Log.d("TAG", "File send success private url: " + qbFile.getPrivateUrl());
                        String imageUrl = qbFile.getPublicUrl();
                        Date date = null;
                        try {
                            date = formatter.parse(etStartDate.getText().toString());
                        } catch (ParseException e) {
                            Log.d("TAG", "Error: bag date");
                        }
                        Event event = new Event(tag,org,name,desc, date,link,place,0,null, imageUrl);
                        QbDao dao = new QbDao(AddEventActivity.this);
                        dao.addEvent(event, new QBEntityCallback<QBCustomObject>() {
                            @Override
                            public void onSuccess(QBCustomObject qbCustomObject, Bundle bundle) {
                                Toast.makeText(AddEventActivity.this, "Event added", Toast.LENGTH_SHORT).show();
                                QBPusher pusher = new QBPusher(AddEventActivity.this);
                                pusher.pushEventByTag(event, new QBEntityCallback<QBEvent>() {
                                    @Override
                                    public void onSuccess(QBEvent qbEvent, Bundle bundle) {
                                        Log.d("TAG", "Push successful");

                                    }

                                    @Override
                                    public void onError(QBResponseException e) {
                                        Log.d("TAG", "Error push: " + e.toString());
                                    }
                                });
                                startActivity(new Intent(AddEventActivity.this, MainActivity.class));
                                finish();

                            }

                            @Override
                            public void onError(QBResponseException e) {
                                Toast.makeText(AddEventActivity.this, "Event wasnt added", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Log.d("TAG", "File send error: " + e.toString());
                    }
                });




            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                Log.d("TAG", "Error: Not picked");
                return;
            }
            try {
                inputPhotoStream = AddEventActivity.this.getContentResolver().openInputStream(data.getData());

            } catch (FileNotFoundException e) {
                Log.d("TAG", "Data error");
                e.printStackTrace();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }
}

