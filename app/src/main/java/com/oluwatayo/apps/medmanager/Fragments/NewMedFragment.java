package com.oluwatayo.apps.medmanager.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oluwatayo.apps.medmanager.R;

import butterknife.ButterKnife;

/**
 * Created by root on 4/18/18.
 */

public class NewMedFragment extends Fragment {

    public static NewMedFragment NewInstance(Bundle bundle){
        NewMedFragment newMedFragment = new NewMedFragment();
        newMedFragment.setArguments(bundle);
        return newMedFragment;
    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_med, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
