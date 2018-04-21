package com.oluwatayo.apps.medmanager.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.parceler.Parcel;

@Parcel
@Entity
public class Medication {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public String description;

    @ColumnInfo(name = "med_type")
    public String medType;

    @ColumnInfo(name = "start_date")
    public String startDate;

    @ColumnInfo(name = "end_date")
    public String endDate;

    public long interval;

    @ColumnInfo(name = "med_uid")
    public String meduid;

    @ColumnInfo(name = "no_of_time_used")
    public int noOfTimeUsed;

    @ColumnInfo(name = "no_of_time_missed")
    public int noOfTimeMissed;

    @ColumnInfo(name = "show_reminder")
    public int showReminder;

    @Ignore
    public Medication(){}

    public Medication(String name, String description, String medType, String startDate, String endDate, long interval, String meduid) {
        this.name = name;
        this.description = description;
        this.medType = medType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.interval = interval;
        this.meduid = meduid;
        this.noOfTimeUsed = 0;
        this.noOfTimeMissed = 0;
        this.showReminder = 1;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getMedType() {
        return medType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public long getInterval() {
        return interval;
    }

    public String getMeduid() {
        return meduid;
    }

    public int getNoOfTimeUsed() {
        return noOfTimeUsed;
    }

    public int getNoOfTimeMissed() {
        return noOfTimeMissed;
    }

    public int getShowReminder(){
        return showReminder;
    }
}
