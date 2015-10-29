package com.kryptonapps.kon_el.trial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchResultActivity extends AppCompatActivity {

    private Context context;
    private TextView textView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = SearchResultActivity.this;
        textView = (TextView) findViewById(R.id.et_search);
        ImageButton imageButton = (ImageButton) findViewById(R.id.search_button);
        linearLayout = (LinearLayout) findViewById(R.id.layout_search);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textView.getText().toString().trim();
                if(text.isEmpty())
                    textView.setError("Empty text");
                else {
                    textView.setText("");
                    linearLayout.setVisibility(View.GONE);
                    PopulatorListView.searchAndPopulateAll(context, SearchResultActivity.this, text);
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(linearLayout.getVisibility() == View.GONE)
                    linearLayout.setVisibility(View.VISIBLE);
                else
                    linearLayout.setVisibility(View.GONE);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String search = intent.getStringExtra(HomeActivity.SEARCH_STRING);
        PopulatorListView.searchAndPopulateAll(context, SearchResultActivity.this, search);
    }
}
