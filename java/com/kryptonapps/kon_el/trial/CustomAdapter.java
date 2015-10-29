package com.kryptonapps.kon_el.trial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kryptonapps.kon_el.trial.api.Member;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class CustomAdapter extends ArrayAdapter<String> {

    public CustomAdapter(Context context, String[] status) {
        super(context, R.layout.custom_row, status);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);
        String singleItem = getItem(position);

        TextView textView = (TextView) customView.findViewById(R.id.profile_status);
        ImageView imageView = (ImageView) customView.findViewById(R.id.profile_pic);

        Realm realm = Realm.getInstance(getContext());
        Member result = realm.where(Member.class)
                                            .equalTo("id", Integer.parseInt(singleItem))
                                            .findFirst();

        textView.setText(result.getStatus());

        Picasso.with(getContext())
                .load(result.getImage())
                .placeholder(R.drawable.profile_pic_placeholder)
                .into(imageView);

        return customView;
    }
}
