package com.example.engster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Define your SearchAdapter class
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private ArrayList<Notes> searchResults;

    // Set search results
    public void setSearchResults(ArrayList<Notes> searchResults) {
        this.searchResults = searchResults;
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWordexample;
        TextView tvExpression;
        TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWordexample = itemView.findViewById(R.id.tv_wordexample);
            tvExpression = itemView.findViewById(R.id.tv_expression);
            tvType = itemView.findViewById(R.id.tv_type);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.results, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notes notes = searchResults.get(position);
        holder.tvWordexample.setText(notes.getWordexample());
        holder.tvExpression.setText(notes.getExpression());
        holder.tvType.setText(notes.getType());
    }

    @Override
    public int getItemCount() {

        if (searchResults == null) {
            return 0;
        }
        return searchResults.size();
    }
}
