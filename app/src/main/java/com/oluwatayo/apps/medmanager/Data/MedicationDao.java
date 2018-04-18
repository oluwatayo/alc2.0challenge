package com.oluwatayo.apps.medmanager.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by root on 4/18/18.
 */

@Dao
public interface MedicationDao {

    @Query("select * from medication")
    List<Medication> loadAllUsers();

    @Query("select * from medication where id = :id")
    Medication loadMedicationById(int  id);

    @Insert(onConflict = IGNORE)
    void insertMedication(Medication medication);

    @Delete
    void deleteMedication(Medication medication);

    @Insert(onConflict = IGNORE)
    void insertOrReplaceMedication(Medication... medications);

    @Delete
    void deleteMedication(Medication medication1, Medication medication2);

    @Query("delete from medication where id = :id")
    int deleteMedicationById(int  id);

    @Query("DELETE FROM medication")
    void deleteAll();
}

