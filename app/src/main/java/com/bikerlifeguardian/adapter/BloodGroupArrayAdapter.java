package com.bikerlifeguardian.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.Arrays;
import java.util.List;


public class BloodGroupArrayAdapter extends ArrayAdapter<String> {

    private List<String> bloodGroups;

    public BloodGroupArrayAdapter(Context context) {
        super(context, android.R.layout.simple_spinner_dropdown_item);

        String[] groups = { "O positif", "O negatif", "A positif", "A negatif", "B positif", "B negatif", "A B positif", "A B negatif" };
        bloodGroups = Arrays.asList(groups);
        addAll(bloodGroups);
    }

    @Override
    public String getItem(int position) {
        return bloodGroups.get(position);
    }

    @Override
    public int getPosition(String item) {
        return bloodGroups.indexOf(item);
    }
}
