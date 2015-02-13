package com.thomtrance.smokes;

import android.provider.BaseColumns;

/**
 * Created by thom on 1/26/2015.
 */
public abstract  class SmokeEntry implements BaseColumns {

    public static final String TABLE_NAME = "the_smokes";
    public static final String COLUMN_NAME_SMOKE_TIME = "smoke_time";
    public static final String COLUMN_NAME_SMOKE_DATE = "smoke_date";
    public static final String COLUMN_NAME_SMOKE_LOCATION = "smoke_location";
}
