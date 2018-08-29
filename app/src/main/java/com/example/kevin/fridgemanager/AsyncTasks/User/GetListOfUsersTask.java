package com.example.kevin.fridgemanager.AsyncTasks.User;
/**
 * Created by kevin on Aug 27, 2018
 **/


import android.app.Activity;
import android.os.AsyncTask;

import com.example.kevin.fridgemanager.Activities.SignupActivity;
import com.example.kevin.fridgemanager.Singletons.GlobalVariables;
import com.example.kevin.fridgemanager.Translators.UserJSONArrayToListOfUsernames;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;

public class GetListOfUsersTask extends AsyncTask<Void, Void, List<String>> {
    private WeakReference<Activity> activity;

    public GetListOfUsersTask(Activity activity) {
        this.activity = new WeakReference<>(activity);
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        String url = GlobalVariables.connectionURL + GlobalVariables.userNamesURL;

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);

            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);
                JSONArray jsonArray = new JSONArray(data);
                return UserJSONArrayToListOfUsernames.translate(jsonArray);
            }

            // TODO: alert dialog with error message?
            else{
                return new ArrayList<>();
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<String> usernames) {
        SignupActivity signupActivity = (SignupActivity) activity.get();
        signupActivity.setUsernames(usernames);
    }
}
