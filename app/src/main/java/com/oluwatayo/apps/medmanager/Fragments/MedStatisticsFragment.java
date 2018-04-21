package com.oluwatayo.apps.medmanager.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.oluwatayo.apps.medmanager.Adapters.StatsAdapter;
import com.oluwatayo.apps.medmanager.Adapters.StatsAdapter.MonthlyMed;
import com.oluwatayo.apps.medmanager.Data.AppDatabase;
import com.oluwatayo.apps.medmanager.Data.Medication;
import com.oluwatayo.apps.medmanager.Data.MedicationDao;
import com.oluwatayo.apps.medmanager.R;
import com.oluwatayo.apps.medmanager.utils.DateUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MedStatisticsFragment extends Fragment {

    View view;
    @BindView(R.id.completed_med_count)
    TextView completedMeds;
    @BindView(R.id.meds_in_use)
    TextView medInUse;
    @BindView(R.id.med_usage_list)
    ListView medUsageList;
    ArrayList<MonthlyMed> monthlyMeds;
    AppDatabase mDb;
    MedicationDao medicationDao;
    int completed = 0;
    int inUse = 0;
    String[] months;
    String[] monthMedList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmet_med_statistics, container, false);
        ButterKnife.bind(this, view);
        mDb = AppDatabase.getINSTANCE(getContext());
        months = new String[]{"January", "February", "March", "April",
                "May", "June", "July", "August", "September",
                "October", "November", "December"};
        monthMedList = new String[]{"","","","","","","","","","","",""};
        medicationDao = mDb.medModel();
        monthlyMeds = new ArrayList<>();
        initUI();
        return view;
    }

    private void initUI() {
        ArrayList<Medication> medications = (ArrayList<Medication>) medicationDao.loadAllMedications();
        for(int i = 0; i<medications.size(); i++){
            Medication medication = medications.get(i);
            long d = DateUtils.getDateInMilliseconds(medication.getStartDate());
            long e = DateUtils.getDateInMilliseconds(medication.getEndDate());
            if(System.currentTimeMillis() >= e)
                completed++;
            else
                inUse++;
            Date date = new Date(d);
            monthMedList[date.getMonth()] += "\n "+medication.getName();
        }
        setUpListview();
    }

    private void setUpListview() {
        for(int j = 0; j<monthMedList.length; j++){
            if(TextUtils.isEmpty(monthMedList[j]))
                continue;
            monthlyMeds.add(new MonthlyMed(months[j], monthMedList[j]));
        }
        String completedMed = getContext().getString(R.string.completed_medications, String.valueOf(completed));
        String inUseMeds = getContext().getString(R.string.meds_in_use, String.valueOf(inUse));
        completedMeds.setText(completedMed);
        medInUse.setText(inUseMeds);
        medUsageList.setAdapter(new StatsAdapter(getContext(), monthlyMeds));
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Statistics");
    }
}
