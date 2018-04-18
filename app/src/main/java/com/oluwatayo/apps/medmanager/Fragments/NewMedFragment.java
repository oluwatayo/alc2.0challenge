package com.oluwatayo.apps.medmanager.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.oluwatayo.apps.medmanager.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewMedFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public static NewMedFragment NewInstance(Bundle bundle){
        NewMedFragment newMedFragment = new NewMedFragment();
        newMedFragment.setArguments(bundle);
        return newMedFragment;
    }

    private final static String LOG_TAG = NewMedFragment.class.getSimpleName();
    private View view;
    @BindView(R.id.med_type_spinner)
    Spinner medTypeSpinner;
    @BindView(R.id.interval_spinner)
    Spinner intervalSpinner;
    @BindView(R.id.med_description_editText)
    EditText medDescriptionEdittext;
    @BindView(R.id.med_name_editText)
    EditText medNameEdittext;
    @BindView(R.id.add_med_button)
    Button addMedButton;
    @BindView(R.id.start_date_selector)
    Button startDateSelector;
    @BindView(R.id.end_date_selector)
    Button endDateSelector;
    @BindView(R.id.start_date_view)
    TextView startDateTextView;
    @BindView(R.id.end_date_view)
    TextView endDateView;
    //to keep track of which date picker selected
    boolean isStartDateSelected = true;
    final String DEFAULT_MED_TYPE = "Tablet";
    String medType;
    final String DEFAULT_INTERVAL = "Hours";
    String interval;
    DatePickerDialog datePickerDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_med, container, false);
        ButterKnife.bind(this, view);
        addMedButton.setOnClickListener(this);
        startDateSelector.setOnClickListener(this);
        endDateSelector.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        setUpSpinners();
        return view;
    }

    private void setUpSpinners() {
        Context context = getActivity().getApplicationContext();
        final ArrayAdapter<CharSequence> medTypeadapter = ArrayAdapter.createFromResource(context,
                R.array.med_types, android.R.layout.simple_spinner_item);
        medTypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medTypeSpinner.setAdapter(medTypeadapter);
        medTypeSpinner.setSelection(0, true);
        medTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                medType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                medType = DEFAULT_MED_TYPE;
            }
        });

        final ArrayAdapter<CharSequence> intervalAdapter = ArrayAdapter.createFromResource(context,
                R.array.intervals, android.R.layout.simple_spinner_item);
        intervalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(intervalAdapter);
        intervalSpinner.setSelection(0, true);
        intervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                interval = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                interval = DEFAULT_INTERVAL;
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.add_med_button:
                break;
            case R.id.start_date_selector:
                isStartDateSelected = true;
                datePickerDialog.show();
                break;
            case R.id.end_date_selector:
                isStartDateSelected = false;
                datePickerDialog.show();
                break;
        }
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        StringBuilder builder = new StringBuilder()
                .append(i).append("/").append(i1).append("/").append(i2);
        if(isStartDateSelected)
            startDateTextView.setText(builder.toString());
        else
            endDateView.setText(builder.toString());
    }
}
