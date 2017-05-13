package com.tappgames.sunshine.Database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by johndaratzikis on 13/05/2017.
 */

public class ForecastContentProvider extends ContentProvider {

    // database
    private DBTableHelper database;

    // used for the UriMacher
    private static final int FORECAST = 10;
    private static final int FORECAST_ID = 20;

    private static final String AUTHORITY = "com.tappgames.sunshine.contentprovider_forecast";

    private static final String BASE_PATH = "forecast";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/forecasts";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/forecast";

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, FORECAST);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", FORECAST_ID);
    }

    @Override
    public boolean onCreate() {
        database = new DBTableHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(DBTableHelper.TABLE_FORECASTS);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case FORECAST:
                break;
            case FORECAST_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(DBTableHelper.COLUMN_FORECASTS_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case FORECAST:
                id = sqlDB.insert(DBTableHelper.TABLE_FORECASTS, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case FORECAST:
                rowsDeleted = sqlDB.delete(DBTableHelper.TABLE_FORECASTS, selection,
                        selectionArgs);
                break;
            case FORECAST_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(
                            DBTableHelper.TABLE_FORECASTS,
                            DBTableHelper.COLUMN_FORECASTS_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(
                            DBTableHelper.TABLE_FORECASTS,
                            DBTableHelper.COLUMN_FORECASTS_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case FORECAST:
                rowsUpdated = sqlDB.update(DBTableHelper.TABLE_FORECASTS,
                        values,
                        selection,
                        selectionArgs);
                break;
            case FORECAST_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(DBTableHelper.TABLE_FORECASTS,
                            values,
                            DBTableHelper.COLUMN_FORECASTS_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(DBTableHelper.TABLE_FORECASTS,
                            values,
                            DBTableHelper.COLUMN_FORECASTS_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {

        String[] available = { DBTableHelper.COLUMN_FORECASTS_ID,
                DBTableHelper.COLUMN_FORECAST_DT,
                DBTableHelper.COLUMN_FORECAST_PRESSURE,
                DBTableHelper.COLUMN_FORECAST_HUMIDITY,
                DBTableHelper.COLUMN_FORECAST_SPEED,
                DBTableHelper.COLUMN_FORECAST_DEG,
                DBTableHelper.COLUMN_FORECAST_CLOUDS,
                DBTableHelper.COLUMN_FORECAST_TEMPERATURE_DAY,
                DBTableHelper.COLUMN_FORECAST_TEMPERATURE_MIN,
                DBTableHelper.COLUMN_FORECAST_TEMPERATURE_MAX,
                DBTableHelper.COLUMN_FORECAST_TEMPERATURE_NIGHT,
                DBTableHelper.COLUMN_FORECAST_TEMPERATURE_EVE,
                DBTableHelper.COLUMN_FORECAST_TEMPERATURE_MORN,
                DBTableHelper.COLUMN_FORECAST_WEATHER_ID,
                DBTableHelper.COLUMN_FORECAST_WEATHER_MAIN,
                DBTableHelper.COLUMN_FORECAST_WEATHER_DESC,
                DBTableHelper.COLUMN_FORECAST_WEATHER_ICON
        };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }

    }

}
