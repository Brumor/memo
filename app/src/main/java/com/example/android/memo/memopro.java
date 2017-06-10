package com.example.android.memo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by pbric on 08/01/2017.
 */
public class memopro extends ContentProvider {


    public static final String PROVIDER_NAME = "com.example.android.memop.Data.MemoProvider";

    public static final String URL = "content://" + PROVIDER_NAME + "/memo";

    public static final Uri Content_URL = Uri.parse(URL);

    public static final String _ID = BaseColumns._ID;
    public static final String title = "title";
    public static final String content = "content";
    public static final int uri_code = 2;

    private static HashMap<String, String> values;

    private static final int MEMO = 2;

    private static final UriMatcher sUriMatcher;

    static {

        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        sUriMatcher.addURI(PROVIDER_NAME, URL, uri_code);
    }

    private SQLiteDatabase sqlDB;


    static final String DATABASE_NAME = "memosdatabase";
    static final String TABLE_NAME = "memotable";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "title TEXT NOT NULL, "
            + "content TEXT);";


    public static final String LOG_TAG = memopro.class.getSimpleName();

    Context mContext;


    @Override
    public boolean onCreate() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        sqlDB = dbHelper.getWritableDatabase();

        if (sqlDB != null) {
            return true;
        }

        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
            case uri_code:
                queryBuilder.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknoewn URI" + uri);
        }

        Cursor cursor = queryBuilder.query(sqlDB, strings, s, strings1, null, null, s1);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case uri_code:
                return "vnd.android.cursor.dir/memo";
            default:
                throw new IllegalArgumentException("Unsupported Uri" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long rowId = sqlDB.insert(TABLE_NAME, null, contentValues);

        if (rowId > 0) {

            Uri _uri = ContentUris.withAppendedId(Content_URL, rowId);

            getContext().getContentResolver().notifyChange(_uri, null);

            return uri;
        } else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {

        int rowsDeleted = 0;

        switch (sUriMatcher.match(uri)) {
            case uri_code:
                rowsDeleted = sqlDB.delete(TABLE_NAME, s, strings);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int rowsUpdated = 0;

        switch (sUriMatcher.match(uri)) {
            case uri_code:
                rowsUpdated = sqlDB.update(TABLE_NAME, contentValues, s, strings);
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }


    ///////////////////////////////////////////////////////////////////
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqlDB) {
            sqlDB.execSQL("DROP  TABLE IF EXISTS " + TABLE_NAME);
            sqlDB.execSQL(CREATE_DB_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqlDB, int oldVersion, int newVersion) {
            sqlDB.execSQL("DROP  TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqlDB);
        }
    }
}
