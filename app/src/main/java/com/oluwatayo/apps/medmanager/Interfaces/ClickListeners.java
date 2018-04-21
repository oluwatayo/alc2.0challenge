package com.oluwatayo.apps.medmanager.Interfaces;

import com.oluwatayo.apps.medmanager.Data.Medication;

/**
 * Created by root on 4/18/18.
 */

public interface ClickListeners {

    interface MedItemClickListener{
        void onMedItemClicked(Medication medication);
    }
}
