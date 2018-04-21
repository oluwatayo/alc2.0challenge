package com.oluwatayo.apps.medmanager.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.oluwatayo.apps.medmanager.LoginActivity;
import com.oluwatayo.apps.medmanager.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 4/21/18.
 */

public class EditProfileDialog extends DialogFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public static final String GENDER_MALE = "Male";
    public static final String GENDER_FEMALE = "Female" ;
    View view;
    SharedPreferences preferences;
    @BindView(R.id.dob_selector)
    EditText dobSelector;
    @BindView(R.id.gender_group)
    RadioGroup group;
    @BindView(R.id.save_btn)
    Button save;
    DatePickerDialog datePickerDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_edit_profile, container,false);
        ButterKnife.bind(this, view);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        dobSelector.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        save.setOnClickListener(this);
        initUI();
        return view;
    }

    private void initUI() {
        String dob = preferences.getString(LoginActivity.PREF_USER_DOB, "");
        String gender = preferences.getString(LoginActivity.PREF_USER_GENDER, "");
        dobSelector.setText(dob);
        if(!TextUtils.isEmpty(gender)){
            if(gender.equals(GENDER_MALE))
                group.check(R.id.gender_male);
            else
                group.check(R.id.gender_female);
        }else
            group.check(RadioGroup.NO_ID);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.dob_selector:
                datePickerDialog.show();
                break;
            case R.id.save_btn:
                saveChanges();
                break;
        }
    }

    private void saveChanges() {
        String date = dobSelector.getText().toString();
        int i = group.getCheckedRadioButtonId();
        String g;
        if(i == R.id.gender_male)
            g = GENDER_MALE;
        else g = GENDER_FEMALE;

        if(!validateFields(date, g))
            return;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LoginActivity.PREF_USER_DOB, date);
        editor.putString(LoginActivity.PREF_USER_GENDER, g);
        editor.apply();
        Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
        getDialog().dismiss();
    }

    private boolean validateFields(String... strings) {
        for (String  a : strings){
            if(a == null || TextUtils.isEmpty(a)){
                Toast.makeText(getContext(), "All Fields are required", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String s = String.valueOf(i) + "/" + i1 + "/" + i2;
        dobSelector.setText(s);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
    }
}
