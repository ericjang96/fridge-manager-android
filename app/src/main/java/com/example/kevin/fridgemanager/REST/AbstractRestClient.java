package com.example.kevin.fridgemanager.REST;

import com.example.kevin.fridgemanager.Singletons.GlobalVariables;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public abstract class AbstractRestClient {
    protected static String BASE_URL = GlobalVariables.connectionURL;
    protected static AsyncHttpClient client = new AsyncHttpClient();

    protected static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    protected static void get(String url, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    protected static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    protected static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
