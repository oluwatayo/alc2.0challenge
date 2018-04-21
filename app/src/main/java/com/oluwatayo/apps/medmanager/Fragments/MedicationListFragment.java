package com.oluwatayo.apps.medmanager.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.oluwatayo.apps.medmanager.Adapters.MedicationListAdapter;
import com.oluwatayo.apps.medmanager.Data.AppDatabase;
import com.oluwatayo.apps.medmanager.Data.Medication;
import com.oluwatayo.apps.medmanager.Interfaces.ClickListeners.MedItemClickListener;
import com.oluwatayo.apps.medmanager.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicationListFragment extends Fragment implements MedItemClickListener{

    private View view;
    @BindView(R.id.medication_list_recycler_view)
    RecyclerView medRecyclerView;
    @BindView(R.id.empty_med_imageView)
    ImageView emptyMed;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.loading_indicator)
    ProgressBar indicator;
    MaterialSearchView searchView;
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
        mDb = AppDatabase.getINSTANCE(getContext());
        medications = new ArrayList<>();
        searchView = getActivity().findViewById(R.id.search_view);
        setHasOptionsMenu(true);
        medicationListAdapter = new MedicationListAdapter(getContext(), medications, this);
        medRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                NewMedFragment newMedFragment = NewMedFragment.NewInstance(null);
                ft.setCustomAnimations(R.anim.enter, R.anim.leave, R.anim.pop_enter, R.anim.pop_leave);
                ft.replace(R.id.fragment_container, newMedFragment).addToBackStack(null).commit();
            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchDataFromDatabase(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchDataFromDatabase(newText);
                return false;
            }
        });
        fetchDataFromDatabase(null);
        return view;
    }

    private void fetchDataFromDatabase(String query) {
        indicator.setVisibility(View.VISIBLE);
        if(query == null || TextUtils.isEmpty(query))
            medications = (ArrayList<Medication>) mDb.medModel().loadAllMedications();
        else
            medications = (ArrayList<Medication>) mDb.medModel().getMedicationByName(query);
        if(medications.size() < 1){
            emptyMed.setVisibility(View.VISIBLE);
        }else
            emptyMed.setVisibility(View.INVISIBLE);
        medicationListAdapter.swapData(medications);
        medRecyclerView.setAdapter(medicationListAdapter);
        indicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getContext().getString(R.string.medications));
    }

    @Override
    public void onMedItemClicked(Medication medication) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.enter, R.anim.leave, R.anim.pop_enter, R.anim.pop_leave);
        ft.replace(R.id.fragment_container, MedDetailsFragment.NewInstance(medication)).addToBackStack(null).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_delete_all:
                deleteAllMeds();
        }
        return true;
    }

    private void deleteAllMeds() {
        mDb.medModel().deleteAll();
        medicationListAdapter.swapData(new ArrayList<Medication>());
        medicationListAdapter.notifyDataSetChanged();
        emptyMed.setVisibility(View.VISIBLE);
    }
}
