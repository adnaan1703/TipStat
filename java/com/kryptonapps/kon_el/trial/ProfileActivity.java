package com.kryptonapps.kon_el.trial;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kryptonapps.kon_el.trial.api.Member;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class ProfileActivity extends AppCompatActivity {

    private Context context;
    private FloatingActionButton fab;
    private View.OnClickListener snackbarOnClickListener;
    private Member member = null;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = ProfileActivity.this;
        realm = Realm.getInstance(context);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (member == null)
                    Toast.makeText(context, "Profile haven't been fetched!!", Toast.LENGTH_SHORT).show();
                else {
                    if (member.isFav()) {

                        realm.beginTransaction();
                        member.setIsFav(false);
                        realm.commitTransaction();

                        fab.setImageResource(R.drawable.ic_star_outline);
                        Snackbar.make(view, "Removed from the favourite list", Snackbar.LENGTH_LONG)
                                .setAction("UNDO", snackbarOnClickListener).show();
                    } else {

                        realm.beginTransaction();
                        member.setIsFav(true);
                        realm.commitTransaction();

                        fab.setImageResource(R.drawable.ic_star);
                        Snackbar.make(view, "Added to the favourite list", Snackbar.LENGTH_LONG)
                                .setAction("UNDO", snackbarOnClickListener).show();
                    }
                }
            }
        });

        snackbarOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm.beginTransaction();
                member.setIsFav(!member.isFav());
                realm.commitTransaction();

                if(member.isFav())
                    fab.setImageResource(R.drawable.ic_star);
                else
                    fab.setImageResource(R.drawable.ic_star_outline);
            }
        };

        Intent intent = getIntent();
        String id = intent.getStringExtra(PopulatorListView.PROFILE_INDEX);
        initializeElements(id);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeElements(String id) {


        ImageView imageView = (ImageView) findViewById(R.id.profile_image_header);
        member = realm.where(Member.class)
                                .equalTo("id", Integer.parseInt(id))
                                .findFirst();

        Picasso.with(context)
                .load(member.getImage())
                .placeholder(R.drawable.profile_pic_placeholder)
                .into(imageView);

        TextView status, ethnicity, birthday, weight, height, veg, drinks;

        status      = (TextView) findViewById(R.id.profile_status_textview);
        ethnicity   = (TextView) findViewById(R.id.profile_ethnicity_textview);
        birthday    = (TextView) findViewById(R.id.profile_birthday_textview);
        weight      = (TextView) findViewById(R.id.profile_weight_textview);
        height      = (TextView) findViewById(R.id.profile_height_textview);
        veg         = (TextView) findViewById(R.id.profile_vegetarian_textview);
        drinks      = (TextView) findViewById(R.id.profile_drinks_textview);

        status.setText(member.getStatus());
        ethnicity.setText(member.getEthnicity());
        birthday.setText(member.getDob());
        weight.setText(String.format("%f %s", member.getWeight(), "KG"));
        height.setText(String.format("%d %s", member.getHeight(), "cm"));

        if(member.isVeg()) {
            veg.setText(R.string.yes);
            veg.setTextColor(Color.GREEN);
        }
        else {
            veg.setText(R.string.no);
            veg.setTextColor(Color.RED);
        }

        if(member.isDrink()) {
            drinks.setText(R.string.yes);
            drinks.setTextColor(Color.GREEN);
        }
        else {
            drinks.setText(R.string.no);
            drinks.setTextColor(Color.RED);
        }

        if(member.isFav())
            fab.setImageResource(R.drawable.ic_star);
        else
            fab.setImageResource(R.drawable.ic_star_outline);

    }
}
