package com.oluwatayo.apps.medmanager.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.SimpleTimeZone;

/**
 * Created by root on 4/16/18.
 */

public final class MedContract {

    public static final String CONTENT_AUTHORITY = "com.oluwatayo.apps.medmanager";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MEDS = "meds";

    private MedContract(){}

    public static final class MedEntry implements BaseColumns{
        public static final String TABLE_NAME = "meds";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_MED_NAME = "name";
        public static final String COLUMN_MED_DESCRIPTION = "description";
        public static final String COLUMN_MED_INTERVAL = "interval";
        public static final String COLUMN_MED_START_DATE = "start";
        public static final String COLUMN_MED_END_DATE = "end";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEDS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDS;
    }
}
