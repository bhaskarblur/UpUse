package com.classified.upuse.Repos;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.classified.upuse.APIWork.ApiWork;
import com.classified.upuse.Constants.api_baseurl;
import com.classified.upuse.Models.AdsModel;
import com.classified.upuse.Models.homeResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdsRepo {

    public AdsRepo instance;
    public List<homeResponse.adsResult> adslist = new ArrayList<>();
    public MutableLiveData<List<homeResponse.adsResult>> adsdata = new MutableLiveData<>();
    public List<homeResponse.citiesResp> citylist = new ArrayList<>();
    public MutableLiveData<AdsModel.adsResult> dataModel=new MutableLiveData<>();
    public api_baseurl baseurl = new api_baseurl();
    public AdsRepo getInstance() {
        if(instance==null) {
            instance=new AdsRepo();
        }
        return instance;
    }

    public MutableLiveData<AdsModel.adsResult> returndataModel(String ad_id,String userid) {
        getdatafromServer(ad_id,userid);
        return dataModel;
    }

    public MutableLiveData<List<homeResponse.adsResult>> returnadadata(String ad_id,String userid) {
        getadsfromserver(ad_id,userid);
        if (adslist == null) {
            adsdata.setValue(null);
        }
        adsdata.setValue(adslist);
        return adsdata;
    }


    private void getadsfromserver(String ad_id,String userid) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl.apibaseurl.toString())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        ApiWork apiWork = retrofit.create(ApiWork.class);

        Call<homeResponse.ListadsResp> call3=apiWork.get_recommendedads(ad_id,userid);

        call3.enqueue(new Callback<homeResponse.ListadsResp>() {
            @Override
            public void onResponse(Call<homeResponse.ListadsResp> call, Response<homeResponse.ListadsResp> response) {
                if(!response.isSuccessful()){
                    Log.d("Error code11",String.valueOf(response.code()));
                    return;
                }

                homeResponse.ListadsResp resp=response.body();

                if(resp.getResult()!=null) {
                    for(int i=0;i<resp.getResult().size();i++) {
                        adslist.add(resp.getResult().get(i));
                        Log.d("stat",resp.getResult().get(i).getProduct_name());
                    }

                    adsdata.setValue(adslist);
                }
            }

            @Override
            public void onFailure(Call<homeResponse.ListadsResp> call, Throwable t) {
                Log.d("Failure",t.getMessage());
            }
        });
    }
    private void getdatafromServer(String ad_id,String userid) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl.apibaseurl.toString())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        ApiWork apiWork = retrofit.create(ApiWork.class);

        Call<AdsModel.adsResp> call=apiWork.single_product(ad_id,userid);
        Log.d("ids", ad_id+","+userid);
        call.enqueue(new Callback<AdsModel.adsResp>() {
            @Override
            public void onResponse(Call<AdsModel.adsResp> call, Response<AdsModel.adsResp> response) {
                if(!response.isSuccessful()) {
                    Log.d("error code22",String.valueOf(response.raw()));
                    return;
                }
                Log.d("datadelivered","yep");
                if(response.body().getResult()!=null) {
                    dataModel.setValue(response.body().getResult());
                }
            }

            @Override
            public void onFailure(Call<AdsModel.adsResp> call, Throwable t) {
                Log.d("Failureads",t.getMessage());
            }
        });

    }

}
