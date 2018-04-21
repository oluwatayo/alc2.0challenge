package com.oluwatayo.apps.medmanager.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oluwatayo.apps.medmanager.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsAdapter extends ArrayAdapter {

    Context context;
    ArrayList<MonthlyMed> monthlyMeds;
    View view;

    @BindView(R.id.month)
    TextView month;
    @BindView(R.id.month_meds)
    TextView monthMeds;

    public StatsAdapter(@NonNull Context context, @NonNull List<MonthlyMed> objects) {
        super(context, 0, objects);
        this.context = context;
        this.monthlyMeds = (ArrayList<MonthlyMed>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stat_list, parent, false);
        }
        ButterKnife.bind(this, view);
        MonthlyMed med = monthlyMeds.get(position);
        month.setText(med.month);
        monthMeds.setText(med.meds);
        return view;
    }

    public static class MonthlyMed {
        public String month;
        public String meds;

        public MonthlyMed(String month, String meds){
            this.month = month;
            this.meds = meds;
        }
    }
}
