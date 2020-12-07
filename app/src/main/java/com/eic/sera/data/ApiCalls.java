package com.eic.sera.data;

import android.content.Context;
import android.util.Log;

import com.eic.sera.eventbus.UpdateEvent;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCalls {

    ApiService apiService = RetroClient.getApiService();


    public void updateCall (){

        Call<Status> statusUpdateCall = apiService.getUpdatedStatusCall();


        statusUpdateCall.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if(response.code() == 200 && response.isSuccessful()) {

            
                    EventBus.getDefault().post(new UpdateEvent.UpdateStatus(response.body().getTemperature(),
                            response.body().getHumidity()));
                }else{
                    Log.e("ERROR","update call  status didnt return 200 status:" + response.code());

                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.e("ERROR","Update Call Failed");
            }
        });


    }

    public void ledToggleCall(int status){

        JsonObject gsonObject = new JsonObject();
        JSONObject newBody = new JSONObject();
        try {
            newBody.put("value",status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
       

        Call<ResponseBody> ledSwitchCall = apiService.switchLEDCall(status);



        ledSwitchCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200 && response.isSuccessful()) {

                    Log.e("LED","LED SWITCHED ON SERVER SIDE");
                }else{
                    Log.e("ERROR","led switch call status didnt return 200 status:" + response.code() + response.toString());

                }
            }




            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR","LED Switch Call Failed ");
            }
        });




    }

   public String gunlukDurumCall(final Context context) {

        final String[] result = new String[1];

        Call<ResponseBody> gunlukDurumCall = apiService.getGunlukDurum();

        gunlukDurumCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200 && response.isSuccessful()) {
                    //Arayuze aktaırılacak
                    
                }else{
                    Log.e("ERROR","gunluk durum call  status does not return 200 status:" + response.code());

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR","Gunluk Durum Call Failed");
            }
        });

        return result[0];


    }

}
