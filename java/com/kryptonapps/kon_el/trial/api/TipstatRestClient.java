package com.kryptonapps.kon_el.trial.api;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import io.realm.Realm;

public class TipstatRestClient {


    private static final String BASE_URL = "http://tipstat.0x10.info/api/tipstat";
    public static final String  API_LIST = "list_status";
    public static final String API_HITS = "api_hits";
    public static final String PREFERENCE_KEY = "kamehameha";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getHits(final Context context, final Class activity) {
        RequestParams params = new RequestParams();
        params.put("type", "json");
        params.put("query", API_HITS);
        client.get(BASE_URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    parseJsonHits(context, jsonObject);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if(activity != null)
                context.startActivity(new Intent(context, activity));
            }
        });
    }

    public static void getList(final Context context, final Class activity) {

        RequestParams params = new RequestParams();
        params.put("type", "json");
        params.put("query", API_LIST);
        client.get(BASE_URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    JSONObject jsonObject = new JSONObject(new String(responseBody));
                    parseJsonList(context, jsonObject);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if(activity != null)
                    context.startActivity(new Intent(context, activity));
            }
        });
    }


    private static void parseJsonHits(Context context, JSONObject jsonObject) {

        int number = jsonObject.optInt(API_HITS);
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(API_HITS, number);
        editor.commit();
    }

    private static void parseJsonList(Context context, JSONObject jsonObject) throws JSONException {

        JSONArray jsonArray = jsonObject.optJSONArray("members");
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("members", jsonArray.length());
        editor.commit();

        for(int i=0; i<jsonArray.length(); i++) {

            JSONObject memberJson = jsonArray.getJSONObject(i);
            Realm realm = Realm.getInstance(context);
            Member member = new Member();

            member.setId(memberJson.optInt("id"));
            member.setDob(memberJson.optString("dob"));
            member.setStatus(memberJson.optString("status"));
            member.setEthnicity(EthnicGroup.map(memberJson.optInt("ethnicity")));
            member.setWeight(memberJson.optDouble("weight") / 1000.0);
            member.setHeight(memberJson.optInt("height"));
            member.setIsVeg(EthnicGroup.boolMap(memberJson.optInt("is_veg")));
            member.setDrink(EthnicGroup.boolMap(memberJson.optInt("drink")));
            member.setImage(memberJson.optString("image"));
            member.setIsFav(false);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(member);
            realm.commitTransaction();

        }
    }
}