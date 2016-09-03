package com.krishna.volley.example;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkNetworkAvailability(this)) {
            try {
                connectToWebService();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void connectToWebService() throws JSONException {


        JSONObject params = new JSONObject();
        params.put("username", "test");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, "http://jsonstub.com/api/login",
                params, onSuccess, onError) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                // add header here if needed

                headerMap.put("Content-Type", "application/json");
                headerMap.put("JsonStub-User-Key", "1f226e05-b153-42f9-8cd3-1b6c55f7a41c");
                headerMap.put("JsonStub-Project-Key", "4bcb2598-a50e-4204-803e-db9ce057b6f5");
                return headerMap;
            }
        };

        Volley.newRequestQueue(this).add(jsObjRequest);


    }


    private Response.Listener<JSONObject> onSuccess = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.e("Response", response.toString());
        }
    };

    private Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("error", "Error");
        }
    };

    public static boolean checkNetworkAvailability(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && (networkInfo.isConnected())) {
            return true;
        }

        return false;

    }
}
