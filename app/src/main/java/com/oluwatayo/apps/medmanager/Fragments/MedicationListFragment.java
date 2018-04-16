package com.oluwatayo.apps.medmanager.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oluwatayo.apps.medmanager.R;

public class MedicationListFragment extends Fragment {

    private View view;
    public MedicationListFragment NewInstance(){
        return new MedicationListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_medication_list, container, false);
        return view;
    }
}
