package com.oluwatayo.apps.medmanager.utils;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.oluwatayo.apps.medmanager.Data.AppDatabase;
import com.oluwatayo.apps.medmanager.Data.Medication;
import com.oluwatayo.apps.medmanager.Fragments.NewMedFragment;
import com.oluwatayo.apps.medmanager.R;

import java.util.ArrayList;

/**
 * Created by root on 4/18/18.
 */

public class ReminderUtils extends BroadcastReceiver {
    AppDatabase mDb;
    public static final String CHANNEL_ID = "MED_REMINDER";
    public static final String USED_MED_ACTION = "used_med";
    public static final String FORGOT_MED_ACTION = "forgot_med";
    public static final int ACTION_USED_MED_PD_ID = 1011;
    public static final int ACTION_FORGOT_MED_PD_ID = 1041;
    public static final int NOTIFICATION_ID = 199;

    String  uid;
    @Override
    public void onReceive(Context context, Intent intent) {
        uid = intent.getStringExtra(NewMedFragment.MED_INTENT_DATA);
        mDb = AppDatabase.getINSTANCE(context);
        ArrayList<Medication> medications = (ArrayList<Medication>) mDb.medModel().getMedicationByUid(uid);
        if(medications.size() < 1)
            return;
        Medication medication = medications.get(0);
        //Cancel The alarm after the end date
        if(System.currentTimeMillis() >= DateUtils.getDateInMilliseconds(medication.getEndDate())){
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, medication.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            assert alarmManager != null;
            alarmManager.cancel(pendingIntent);
        }
        if(System.currentTimeMillis() < DateUtils.getDateInMilliseconds(medication.getEndDate()) && medication.getShowReminder() == 1)
            showReminder(context, medication);
    }

    private void showReminder(Context context, Medication med) {
        String medName = med.getName();
        String medType = med.getMedType();
        int smallIcon = ResourceUtils.getSmallIcon(medType);
        Bitmap largeIcon = ResourceUtils.getLargeIcon(context);
        Uri sound = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Reminder", NotificationManager.IMPORTANCE_HIGH);
            assert manager != null;
            manager.createNotificationChannel(channel);
        }
        String longText = "Hey, it's time for your "+medType+" : "+medName+" Don't Forget";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentTitle("Medication Reminder")
                .setContentText(longText)
                .setSmallIcon(smallIcon)
                .addAction(usedMedAction(context))
                .addAction(forgotMedAction(context))
                .setLargeIcon(largeIcon)
                .setAutoCancel(false)
                .setLights(Color.GREEN, 1000, 900)
                .setSound(sound);

        assert manager != null;
        manager.notify("TAG", NOTIFICATION_ID, builder.build());
    }


    private NotificationCompat.Action usedMedAction(Context context){
        Intent usedMedIntent = new Intent(context, DrugUsageService.class);
        usedMedIntent.setAction(USED_MED_ACTION);
        usedMedIntent.putExtra(NewMedFragment.MED_INTENT_DATA, uid);
        PendingIntent pd = PendingIntent.getService(context, ACTION_USED_MED_PD_ID, usedMedIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return new NotificationCompat.Action(R.drawable.ic_check_black_24dp, "I Used It", pd);
    }

    private NotificationCompat.Action forgotMedAction(Context context){
        Intent forgotMedIntent = new Intent(context, DrugUsageService.class);
        forgotMedIntent.setAction(FORGOT_MED_ACTION);
        forgotMedIntent.putExtra(NewMedFragment.MED_INTENT_DATA, uid);
        PendingIntent pd = PendingIntent.getService(context, ACTION_FORGOT_MED_PD_ID, forgotMedIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        return new NotificationCompat.Action(R.drawable.ic_close_black_24dp, "I Forgot", pd);
    }

    public static void cancelNotification(Context context, int id){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.cancel("TAG", id);
    }
}
