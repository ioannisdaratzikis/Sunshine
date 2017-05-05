package com.tappgames.sunshine.Rest;


import android.util.Log;
import com.tappgames.sunshine.Utilities.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by johndaratzikis on 04/05/2017.
 */

public abstract class RestCallback<T> implements Callback<T> {

    public RestCallback() {

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        handleExeption(t);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Log.i(Constants.LOG_REST_TAG, "Generic success");
        T responseContent = (T) response.body();
        if (response.isSuccessful()){
            handleSuccess(responseContent);
        }else {
            handleFailure(response.code(), response.message());
        }
    }

    public abstract void handleExeption(Throwable exception);

    public abstract void handleSuccess(T response);

    public abstract void handleFailure(int statusCode,String statusMessage);
}
