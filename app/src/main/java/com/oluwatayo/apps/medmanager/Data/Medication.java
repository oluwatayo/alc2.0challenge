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
    @NonNull
    public String id;

    public String name;

    public String description;

    @ColumnInfo(name = "med_type")
    public String medType;

    @ColumnInfo(name = "start_date")
    public String startDate;

    @ColumnInfo(name = "end_date")
    public String endDate;

    public int interval;

}
