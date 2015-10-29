package com.kryptonapps.kon_el.trial;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kryptonapps.kon_el.trial.api.TipstatRestClient;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    LinearLayout search_layout, count_layout;
    ImageButton search_button;
    EditText search_et;
    Context context;
    public static final String SEARCH_STRING = "search_string_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = HomeActivity.this;

        search_layout = (LinearLayout) findViewById(R.id.layout_search);
        search_button = (ImageButton) findViewById(R.id.search_button);
        search_et = (EditText) findViewById(R.id.et_search);
        count_layout = (LinearLayout) findViewById(R.id.layout_count);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(search_layout.getVisibility() == View.INVISIBLE) {
                    count_layout.setVisibility(View.INVISIBLE);
                    search_layout.setVisibility(View.VISIBLE);
                }
                else {
                    search_layout.setVisibility(View.INVISIBLE);
                    count_layout.setVisibility(View.VISIBLE);
                }
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = search_et.getText().toString().trim();
                if (text.isEmpty()) {
                    search_et.setError("Empty text");
                } else {
//                    Toast.makeText(HomeActivity.this, text, Toast.LENGTH_LONG).show();
                    count_layout.setVisibility(View.VISIBLE);
                    search_layout.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(context, SearchResultActivity.class);
                    intent.putExtra(SEARCH_STRING, text);
                    startActivity(intent);
                }
            }
        });

        TextView tv_members = (TextView) findViewById(R.id.tv_member);
        TextView tv_api_hits = (TextView) findViewById(R.id.tv_api_hits);

        SharedPreferences sharedPreferences = getSharedPreferences(TipstatRestClient.PREFERENCE_KEY, MODE_PRIVATE);
        int membersCount = sharedPreferences.getInt("members", -1);
        int apiCount = sharedPreferences.getInt("api_hits", -1);

        tv_members.setText(String.format("%d", membersCount));
        tv_api_hits.setText(String.format("%d", apiCount));

        PopulatorListView.populateAll(context, HomeActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_favourite:
                startActivity(new Intent(context, FavouriteActivity.class));
                return true;

            case R.id.action_sort_weight_asc:
                PopulatorListView.sortAndPopulateAll(context, HomeActivity.this, true, true);
                return true;

            case R.id.action_sort_weight_dsc:
                PopulatorListView.sortAndPopulateAll(context, HomeActivity.this, true, false);
                return true;

            case R.id.action_sort_height_asc:
                PopulatorListView.sortAndPopulateAll(context, HomeActivity.this, false, true);
                return true;

            case R.id.action_sort_height_dsc:
                PopulatorListView.sortAndPopulateAll(context, HomeActivity.this, false, false);
                return true;

            case R.id.action_asian:
                PopulatorListView.populateEthnicityAll(context, HomeActivity.this, "Asian");
                return true;

            case R.id.action_indian:
                PopulatorListView.populateEthnicityAll(context, HomeActivity.this, "Indian");
                return true;

            case R.id.action_african_american:
                PopulatorListView.populateEthnicityAll(context, HomeActivity.this, "African American");
                return true;

            case R.id.action_asian_american:
                PopulatorListView.populateEthnicityAll(context, HomeActivity.this, "Asian American");
                return true;

            case R.id.action_european:
                PopulatorListView.populateEthnicityAll(context, HomeActivity.this, "European");
                return true;

            case R.id.action_british:
                PopulatorListView.populateEthnicityAll(context, HomeActivity.this, "British");
                return true;

            case R.id.action_jewish:
                PopulatorListView.populateEthnicityAll(context, HomeActivity.this, "Jewish");
                return true;

            case R.id.action_latino:
                PopulatorListView.populateEthnicityAll(context, HomeActivity.this, "Latino");
                return true;

            case R.id.action_native_american:
                PopulatorListView.populateEthnicityAll(context, HomeActivity.this, "Native American");
                return true;

            case R.id.action_arabic:
                PopulatorListView.populateEthnicityAll(context, HomeActivity.this, "Arabic");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
