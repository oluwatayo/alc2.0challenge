package com.oluwatayo.apps.medmanager.utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.oluwatayo.apps.medmanager.Data.AppDatabase;
import com.oluwatayo.apps.medmanager.Fragments.MedicationListFragment;
import com.oluwatayo.apps.medmanager.Fragments.NewMedFragment;

public class DrugUsageService extends IntentService {

    AppDatabase mDb;
    Context context;
    public DrugUsageService() {
        super("DrugUsageService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        context = this;
        mDb = AppDatabase.getINSTANCE(context);
        assert intent != null;
        String action = intent.getAction();
        String medUid = intent.getStringExtra(NewMedFragment.MED_INTENT_DATA);
        assert action != null;
        switch (action){
            case ReminderUtils.USED_MED_ACTION:
                userUsedMed(medUid);
                break;
            case ReminderUtils.FORGOT_MED_ACTION:
                userForgotMed(medUid);
                break;
        }
    }

    void userUsedMed(String uid){
        mDb.medModel().updateMedicationUsed(uid);
        clearNotification();
        showToast("Yaay!, Keep taking your med and you'll get well soon");
    }

    void userForgotMed(String uid){
        mDb.medModel().updateMedicationMissed(uid);
        clearNotification();
        showToast("Oops!, Are you sure you want to get better?");
    }

    void clearNotification(){
        ReminderUtils.cancelNotification(context, ReminderUtils.NOTIFICATION_ID);
    }

    void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
