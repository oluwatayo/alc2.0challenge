package com.oluwatayo.apps.medmanager.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by root on 4/18/18.
 */

@Entity
public class Medication {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    private String description;

    @ColumnInfo(name = "med_type")
    private String medType;

    @ColumnInfo(name = "start_date")
    private String startDate;

    @ColumnInfo(name = "end_date")
    private String endDate;

    private int interval;

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

    public int getInterval() {
        return interval;
    }
}
