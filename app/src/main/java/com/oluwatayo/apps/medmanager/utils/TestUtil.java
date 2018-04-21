package com.oluwatayo.apps.medmanager.utils;

import com.oluwatayo.apps.medmanager.Data.Medication;

/**
 * Created by root on 4/21/18.
 */

public class TestUtil {

    public static Medication createMed(){
        Medication medication = new Medication("Amoxil", "Anti Biotics", "Syrup", "2018/4/20", "2018/04/22",1000,"iwks");
        return medication;
    }
}
