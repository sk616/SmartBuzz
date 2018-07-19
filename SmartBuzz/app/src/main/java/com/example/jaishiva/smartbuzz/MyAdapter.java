package com.example.jaishiva.smartbuzz;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public ArrayList<String> mDataset;
    private String s1,s2;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.dummyText);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_text, parent, false));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void getTime(int selectedHour, int selectedMinute,String AM_PM)
    {
        if(selectedHour<10)
            s1="0"+selectedHour;
        if(selectedMinute==0)
            s2="0"+selectedMinute;

        if(selectedHour<10 && selectedMinute>=10) {
            mDataset.add(s1+ ":" + selectedMinute + " " + AM_PM);
        }
        else if(selectedHour<10 && selectedMinute<10)
        {
            mDataset.add( s1+ ":" +"0"+selectedMinute+" " + AM_PM);
        }
        else {
            if((selectedHour==10 || selectedHour==11 || selectedHour==12) && selectedMinute<10)
            mDataset.add(selectedHour + ":"  + "0"+selectedMinute+" " + AM_PM);
            else
                mDataset.add(selectedHour + ":" + selectedMinute +" " + AM_PM);
        }
        notifyDataSetChanged();
    }
}

