package com.oluwatayo.apps.medmanager.Fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.oluwatayo.apps.medmanager.Data.AppDatabase;
import com.oluwatayo.apps.medmanager.Data.Medication;
import com.oluwatayo.apps.medmanager.R;
import com.oluwatayo.apps.medmanager.utils.DateUtils;
import com.oluwatayo.apps.medmanager.utils.ReminderUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewMedFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static NewMedFragment NewInstance(Bundle bundle) {
        NewMedFragment newMedFragment = new NewMedFragment();
        newMedFragment.setArguments(bundle);
        return newMedFragment;
    }


    public static final int PDI_REQUEST_CODE = 100;

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
    EditText startDateSelector;
    @BindView(R.id.end_date_selector)
    EditText endDateSelector;
    @BindView(R.id.interval_editText)
    EditText intervalEditText;
    //to keep track of which date picker selected
    boolean isStartDateSelected = true;
    final String DEFAULT_MED_TYPE = "Tablet";
    String medType, interval_type, startDate, endDate, medName, medDescription, intInterval;
    final String DEFAULT_INTERVAL = "Hours";
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    AppDatabase mDb;
    Calendar calendar;
    public static final String MED_INTENT_DATA = "med_data";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_med, container, false);
        ButterKnife.bind(this, view);
        addMedButton.setOnClickListener(this);
        startDateSelector.setOnClickListener(this);
        endDateSelector.setOnClickListener(this);
        calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mDb = AppDatabase.getINSTANCE(getContext());
        datePickerDialog = new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        timePickerDialog = new TimePickerDialog(getContext(), this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
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
                interval_type = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                interval_type = DEFAULT_INTERVAL;
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add_med_button:
                getInputs();
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

    private void getInputs() {
        medName = medNameEdittext.getText().toString().trim();
        medDescription = medDescriptionEdittext.getText().toString().trim();
        intInterval = intervalEditText.getText().toString().trim();
        if (medType == null)
            medType = DEFAULT_MED_TYPE;
        if (interval_type == null)
            interval_type = DEFAULT_INTERVAL;

        if (!validateFields(medName, medDescription, medType, interval_type, intInterval, startDate, endDate)) {
            Toast.makeText(getContext(), "All Fields are required...", Toast.LENGTH_LONG).show();
            return;
        }

        long interval;
        switch (interval_type) {
            case "Minutes":
                interval = TimeUnit.MINUTES.toMillis(Integer.parseInt(intInterval));
                break;
            case "Seconds":
                interval = TimeUnit.SECONDS.toMillis(Integer.parseInt(intInterval));
                break;
            case "Hours":
            default:
                interval = TimeUnit.HOURS.toMillis(Integer.parseInt(intInterval));

        }
        insertToDatabase(interval);
    }

    private boolean validateFields(String... strings) {
        for (String a : strings) {
            Log.e(LOG_TAG, "data :" + a);
            if (null == a || TextUtils.isEmpty(a)) {
                return false;
            }
        }

        return true;
    }

    private void insertToDatabase(long interval) {
        String medUid = UUID.randomUUID().toString().replaceAll("-", "");
        Medication med = new Medication(medName, medDescription, medType, startDate, endDate, interval, medUid);
        mDb.medModel().insertMedication(med);
        //Set The Alarm For The Medicine
        Intent intent = new Intent(getActivity(), ReminderUtils.class);
        intent.putExtra(MED_INTENT_DATA, medUid);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), PDI_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30), pendingIntent);

        Toast.makeText(getContext(), "New Medication : " + medName + " Added", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        StringBuilder builder = new StringBuilder()
                .append(i).append("/").append(i1+1).append("/").append(i2);
        if (isStartDateSelected) {
            startDate = builder.toString();
            timePickerDialog.show();

        } else {
            endDate = builder.toString();
            endDate = endDate + " "+"23:59";
            if (startDate == null || TextUtils.isEmpty(startDate)) {
                endDate = "";
                showToast("Select Start Date First");
            } else if (DateUtils.getDateInMilliseconds(endDate) < DateUtils.getDateInMilliseconds(startDate)) {
                endDate = "";
                showToast("End Date Cannot be less than start date");
            }
            endDateSelector.setText(endDate);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getContext().getString(R.string.add_medication));
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Log.e(LOG_TAG, "kkk:"+startDate);
        startDate = startDate + " "+i+":"+i1;
        if ((calendar.getTimeInMillis()/1000 - TimeUnit.MINUTES.toMillis(3)) > DateUtils.getDateInMilliseconds(startDate)/1000) {
            showToast("Start Date Cannot Be Less Than Now");
            startDate = "";
        }
        startDateSelector.setText(startDate);
    }
}
