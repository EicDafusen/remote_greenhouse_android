package com.eic.sera;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.eic.sera.eventbus.UpdateEvent;
import com.eic.sera.data.ApiCalls;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;



public class MainActivity extends AppCompatActivity {


    ProgressDialog progress;
    Switch  ledSwitch;
    TextView dereceTV ;
    TextView nemTV ;
    ApiCalls apiCalls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nemTV = findViewById(R.id.nem_tv);
        dereceTV = findViewById(R.id.derece_tv);
        ledSwitch = findViewById(R.id.switch1);
        apiCalls = new ApiCalls();


       ledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean isOn) {
               if(isOn){
                   apiCalls.ledToggleCall(1);
                   Log.e("LED","LED SWITCHED ON");
               }else{
                   apiCalls.ledToggleCall(0);
                   Log.e("LED","LED SWITCHED ON");
               }
           }
       });


    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void guncelleButton(View view) {


       progress = new ProgressDialog(this);
        progress.setTitle("Güncelleniyor");
        progress.setMessage("Lütfen Bekleyiniz...");
        progress.setCancelable(false);
        progress.show();

        apiCalls.updateCall();

        Intent intent = new Intent(this,ChartActivity.class);
        startActivity(intent);

    }

    public void sicaklikButton(View view) {
        Intent intent = new Intent(this,ChartActivity.class);
        startActivity(intent);
    }


    //Subscribe update status event
    @Subscribe
    public  void onStatusUpdate(UpdateEvent.UpdateStatus event){
        dereceTV.setText(event.getTemp()+" C");
        nemTV.setText("% "+event.getHumid());
        progress.dismiss();

    }



}



