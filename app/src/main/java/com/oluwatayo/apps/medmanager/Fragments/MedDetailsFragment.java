package com.oluwatayo.apps.medmanager.Fragments;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.oluwatayo.apps.medmanager.Data.AppDatabase;
import com.oluwatayo.apps.medmanager.Data.Medication;
import com.oluwatayo.apps.medmanager.R;
import com.oluwatayo.apps.medmanager.utils.ResourceUtils;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MedDetailsFragment extends Fragment {
    private static final String MED_PARCELABLE = "med_parcel";
    private View view;
    @BindView(R.id.med_det_name)
    TextView medDetNameView;
    @BindView(R.id.med_det_description)
    TextView medDetDescriptionView;
    @BindView(R.id.med_det_image)
    ImageView medDetImage;
    @BindView(R.id.med_det_info)
    TextView medDetInfoView;
    @BindView(R.id.med_det_used)
    TextView medDetUsedView;
    @BindView(R.id.med_det_missed)
    TextView medDetMissed;
    @BindView(R.id.med_det_message_good)
    TextView medDetMessageGood;
    @BindView(R.id.med_det_message_bad)
    TextView medDetMessageBad;
    @BindView(R.id.reminder_toggle)
    CheckBox toggle;

    Medication medication;
    AppDatabase mDb;

    int id;

    public static MedDetailsFragment NewInstance(Medication medication) {
        Bundle args = new Bundle();
        args.putParcelable(MED_PARCELABLE, Parcels.wrap(medication));
        MedDetailsFragment medDetails = new MedDetailsFragment();
        medDetails.setArguments(args);
        return medDetails;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_med_details, container, false);
        ButterKnife.bind(this, view);
        medication = Parcels.unwrap(getArguments().getParcelable(MED_PARCELABLE));
        mDb = AppDatabase.getINSTANCE(getContext());
        setHasOptionsMenu(true);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    mDb.medModel().updateShowReminder(1);
                else
                    mDb.medModel().updateShowReminder(0);
            }
        });
        initUI();
        return view;
    }

    private void initUI() {
        medDetNameView.setText(medication.getName());
        medDetDescriptionView.setText(medication.getDescription());
        medDetUsedView.setText(String.valueOf(medication.getNoOfTimeUsed()));
        medDetMissed.setText(String.valueOf(medication.getNoOfTimeMissed()));
        String info = getContext().getString(R.string.med_info, medication.getStartDate(), medication.getEndDate());
        Drawable d = ResourceUtils.getMedDrawable(getContext(), medication.getMedType());
        medDetImage.setImageDrawable(d);
        medDetInfoView.setText(info);
        id = medication.getId();
        if (medication.noOfTimeMissed >= medication.noOfTimeUsed) {
            medDetMessageGood.setVisibility(View.INVISIBLE);
            medDetMessageBad.setVisibility(View.VISIBLE);
        } else {
            medDetMessageGood.setVisibility(View.VISIBLE);
            medDetMessageBad.setVisibility(View.INVISIBLE);
        }
        if (medication.getShowReminder() == 1)
            toggle.setChecked(true);
        else
            toggle.setChecked(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_delete_med:
                deleteMed();
        }
        return true;
    }

    private void deleteMed() {
        mDb.medModel().deleteMedicationById(id);
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
