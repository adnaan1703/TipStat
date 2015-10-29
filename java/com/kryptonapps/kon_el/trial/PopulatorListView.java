package com.kryptonapps.kon_el.trial;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.kryptonapps.kon_el.trial.api.Member;

import io.realm.Realm;
import io.realm.RealmResults;

public class PopulatorListView {

    public final static String PROFILE_INDEX = "profile_index";
    public final static int MAX_ROW = 10;

    public static void populateAll(final Context context, AppCompatActivity activity) {
        searchAndPopulateAll(context, activity, "");
    }

    public static void populateEthnicityAll(final Context context, AppCompatActivity activity, String ethnicity) {

        if(ethnicity.equalsIgnoreCase("All"))
            populateAll(context, activity);

        Realm realm = Realm.getInstance(context);
        RealmResults<Member> results = realm.where(Member.class)
                                            .equalTo("ethnicity", ethnicity, false)
                                            .findAll();

        String[] id = new String[results.size()];
        for(int i=0; i<results.size(); i++)
            id[i] = String.valueOf(results.get(i).getId());


        ListAdapter listAdapter = new CustomAdapter(context, id);
        ListView listView = (ListView) activity.findViewById (R.id.listView);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stat = String.valueOf(parent.getItemAtPosition(position));
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(PROFILE_INDEX, stat);
                context.startActivity(intent);
            }
        });

    }

    public static void sortAndPopulateAll(final Context context, final AppCompatActivity activity, boolean isWeight, boolean isAsc) {

        Realm realm = Realm.getInstance(context);
        RealmResults<Member> results = realm.where(Member.class)
                                            .findAll();

        if(isWeight) {
            if(isAsc)
                results.sort("weight");
            else
                results.sort("weight", RealmResults.SORT_ORDER_DESCENDING);
        }
        else {
            if(isAsc)
                results.sort("height");
            else
                results.sort("height", RealmResults.SORT_ORDER_DESCENDING);
        }

        final String[] id = new String[results.size()];
        for(int i=0; i<results.size(); i++)
            id[i] = String.valueOf(results.get(i).getId());

        ListAdapter listAdapter = new CustomAdapter(context, id);
        ListView listView = (ListView) activity.findViewById (R.id.listView);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stat = String.valueOf(parent.getItemAtPosition(position));
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(PROFILE_INDEX, stat);
                context.startActivity(intent);
            }
        });

    }

    public static void searchAndPopulateAll(final Context context, final AppCompatActivity activity, final String search) {

        Realm realm = Realm.getInstance(context);
        RealmResults<Member> results = realm.where(Member.class)
                .contains("status", search, false)
                .findAll();

        final String[] id = new String[results.size()];
        for(int i=0; i<results.size(); i++) {
            id[i] = String.valueOf(results.get(i).getId());
        }

        ListAdapter listAdapter = new CustomAdapter(context, id);
        ListView listView = (ListView) activity.findViewById (R.id.listView);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stat = String.valueOf(parent.getItemAtPosition(position));
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(PROFILE_INDEX, stat);
                context.startActivity(intent);
            }
        });

    }

    public static void populateFavourite(final Context context, AppCompatActivity activity) {

        Realm realm = Realm.getInstance(context);
        RealmResults<Member> results = realm.where(Member.class)
                                            .equalTo("isFav", true)
                                            .findAll();

        String[] id = new String[results.size()];

        for(int i=0; i<results.size(); i++) {
            id[i] = String.valueOf((results.get(i).getId()));
        }

        ListAdapter listAdapter = new CustomAdapter(context, id);
        ListView listView = (ListView) activity.findViewById(R.id.listView);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stat = String.valueOf(parent.getItemAtPosition(position));
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(PROFILE_INDEX, stat);
                context.startActivity(intent);
            }
        });
    }
}
