package com.oluwatayo.apps.medmanager;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.oluwatayo.apps.medmanager.Data.AppDatabase;
import com.oluwatayo.apps.medmanager.Data.Medication;
import com.oluwatayo.apps.medmanager.Data.MedicationDao;
import com.oluwatayo.apps.medmanager.utils.TestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.*;


@RunWith(AndroidJUnit4.class)
public class RoomDbTest {
    private MedicationDao medicationDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        medicationDao = mDb.medModel();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void testInsert() throws Exception {
        Medication med = TestUtil.createMed();
        medicationDao.insertMedication(med);
        List<Medication> medications = medicationDao.loadAllMedications();
        assertTrue(medications.size() > 0);
    }

    @Test
    public void testDelete() throws Exception{
        Medication med = TestUtil.createMed();
        medicationDao.insertMedication(med);
        medicationDao.deleteAll();
        assertFalse(medicationDao.loadAllMedications().size() > 0);
    }

    @Test
    public void testFindByUid() throws Exception{
        Medication med = TestUtil.createMed();
        medicationDao.insertMedication(med);
        List<Medication> medications = medicationDao.getMedicationByUid("iwks");
        assertTrue(medications.size() > 0);
    }

    @Test
    public void deleteWithId() throws Exception{
        Medication med = TestUtil.createMed();
        medicationDao.insertMedication(med);
        int id = med.getId();
        int delId = medicationDao.deleteMedicationById(id);
        assertEquals(id, delId);
    }
}

