package com.oluwatayo.apps.medmanager.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.oluwatayo.apps.medmanager.Adapters.MedicationListAdapter;
import com.oluwatayo.apps.medmanager.Data.AppDatabase;
import com.oluwatayo.apps.medmanager.Data.Medication;
import com.oluwatayo.apps.medmanager.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicationListFragment extends Fragment {

    private View view;
    @BindView(R.id.medication_list_recycler_view)
    RecyclerView medRecyclerView;
    @BindView(R.id.empty_med_imageView)
    ImageView emptyMed;
    private AppDatabase mDb;
    private MedicationListAdapter medicationListAdapter;
    ArrayList<Medication> medications;
    public static MedicationListFragment NewInstance() {
        return new MedicationListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_medication_list, container, false);
        ButterKnife.bind(this, view);
        getActivity().findViewById(R.id.fab).setVisibility(View.VISIBLE);
        mDb = AppDatabase.getInMemoryDatabase(getContext());
        medications = new ArrayList<>();
        medicationListAdapter = new MedicationListAdapter(getContext(), medications);
        medRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchDataFromDatabase();
        return view;
    }

    private void fetchDataFromDatabase() {
        medications = (ArrayList<Medication>) mDb.medModel().loadAllMedications();
        if(medications.size() < 1){
            emptyMed.setVisibility(View.VISIBLE);
            return;
        }
        medicationListAdapter.swapData(medications);
        medRecyclerView.setAdapter(medicationListAdapter);
    }
}
