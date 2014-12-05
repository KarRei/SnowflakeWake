package com.example.karin.snowflakewake;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

/**
 * Created by Karin on 2014-12-02.
 */
public class AlarmListAdapter extends BaseAdapter {

    private Context mContext;
    private List<AlarmModel> mAlarms;

    public AlarmListAdapter(Context context, List<AlarmModel> alarms) {
        mContext = context;
        mAlarms = alarms;
    }

    public void setAlarms(List<AlarmModel> alarms) {
        mAlarms = alarms;
    }

    @Override
    public int getCount() {
        if (mAlarms != null) {
            return mAlarms.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mAlarms != null) {
            return mAlarms.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mAlarms != null) {
            return mAlarms.get(position).id;
        }
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        //We’ll start the view by checking if the method has passed us a view to reuse or we can inflate our own
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.alarm_list_item, parent, false);
        }

        //Next we’ll get the alarm based on the position of the current item position
        AlarmModel model = (AlarmModel) getItem(position);

        TextView txtTime = (TextView) view.findViewById(R.id.alarm_item_time);
        txtTime.setText(String.format("%02d : %02d", model.timeHour, model.timeMinute));

        TextView txtName = (TextView) view.findViewById(R.id.alarm_item_name);
        txtName.setText(model.name);

        TextView txtSnowAmount = (TextView) view.findViewById(R.id.snow_amount);
        txtSnowAmount.setText(model.snowAmount);

        TextView txtTimeAmount = (TextView) view.findViewById(R.id.time_amount);
        txtTimeAmount.setText(model.timeAmount);

        ToggleButton btnToggle = (ToggleButton) view.findViewById(R.id.alarm_item_toggle);
        btnToggle.setChecked(model.isEnabled);
        btnToggle.setTag(Long.valueOf(model.id));
        btnToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((ListActivity) mContext).setAlarmEnabled(((Long) buttonView.getTag()).longValue(), isChecked);
            }
        });

        view.setTag(Long.valueOf(model.id));
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                ((ListActivity) mContext).startAlarmDetailsActivity(((Long) view.getTag()).longValue());
            }
        });

        return view;
    }

}
