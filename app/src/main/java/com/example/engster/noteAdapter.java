package com.example.engster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class noteAdapter extends ArrayAdapter<Notes> {

    Context context;
    int rsc;

    public noteAdapter(@NonNull Context context, int resource, @NonNull List<Notes> objects) {
        super(context, resource, objects);
        this.context=context;
        this.rsc=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView= LayoutInflater.from(context).inflate(rsc,parent,false);

        TextView txtword=convertView.findViewById(R.id.txtword);
        TextView txtexp=convertView.findViewById(R.id.txtex);


        Notes n = getItem(position);

        txtword.setText(n.getWordexample());
        txtexp.setText(n.getExpression());


    return convertView;
    }
}

