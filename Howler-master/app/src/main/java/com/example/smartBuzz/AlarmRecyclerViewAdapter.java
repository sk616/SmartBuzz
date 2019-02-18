package com.example.smartBuzz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.smartBuzz.model.Alarm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/*
 ** Created by Gautam Krishnan {@link https://github.com/GautiKrish}
 */public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private RealmResults<Alarm> results;
    private List<Alarm> mAlarmList;
    private Realm realm;
    private onClickTrashIconListener mOnClickTrashIconListener;
    private onAddingAlarmItem mOnAddingAlarmItem;


    AlarmRecyclerViewAdapter(Context context, RealmResults<Alarm> results, Realm realm) {
        this.mContext = context;
        this.results = results;
        this.realm = realm;
        mAlarmList = realm.copyFromRealm(results);
    }

    @NonNull
    @Override
    public AlarmRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_alarm_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(mAlarmList.get(position).getAlarmTime());

    }

    @Override
    public int getItemCount() {
        return mAlarmList.size();
    }

    public void updateAdapter(Alarm alarm) {
        mAlarmList.add(alarm);
        notifyDataSetChanged();
        mOnAddingAlarmItem.runLayoutAnimation();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.alarm_time);
            mImageView = itemView.findViewById(R.id.trash_icon);

            mImageView.setOnClickListener(v -> {
                int iD = getAdapterPosition();
                String time = mTextView.getText().toString();
                deleteAlarm(iD, time);
            });
        }
    }

    private void deleteAlarm(int iD, String time) {
        Log.i("Adapter ID", String.valueOf(iD));
        mOnClickTrashIconListener.cancelPendingIntent(time);
        mAlarmList.remove(iD);
        notifyDataSetChanged();
    }


    public interface onClickTrashIconListener {
        void cancelPendingIntent(String time);
    }

    public interface onAddingAlarmItem {
        void runLayoutAnimation();
    }

    public void setOnAddingAlarmItem(onAddingAlarmItem mOnAddingAlarmItem) {
        this.mOnAddingAlarmItem = mOnAddingAlarmItem;
    }

    public void setOnClickTrashIconListener(onClickTrashIconListener mOnClickTrashIconListener) {
        this.mOnClickTrashIconListener = mOnClickTrashIconListener;

    }


}
