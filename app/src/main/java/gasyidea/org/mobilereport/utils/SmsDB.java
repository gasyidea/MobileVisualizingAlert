package gasyidea.org.mobilereport.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import gasyidea.org.mobilereport.models.SmsAlert;

@EBean
public class SmsDB extends SQLiteOpenHelper implements DBInterface<SmsAlert> {

    public static final String KEY_MAX_SCORE = "max_score";
    public static final String KEY_TOTAL = "total";
    public static final String KEY_DATE = "date";
    public static final String KEY_SOLUCE = "soluce";
    private static final String KEY_ID = "_id";
    private static final String[] COLUMNS = {KEY_ID, KEY_MAX_SCORE, KEY_TOTAL, KEY_DATE, KEY_SOLUCE};
    private static final String TABLE_ALERTS = "alerts";
    private static final String CREATE_TABLE_ALERTS = "CREATE TABLE "
            + TABLE_ALERTS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_MAX_SCORE + " INTEGER," +
            KEY_TOTAL + " INTEGER," +
            KEY_DATE + " INTEGER," +
            KEY_SOLUCE + " TEXT" + ")";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "alerts.db";

    public SmsDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ALERTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS alerts");
        onCreate(sqLiteDatabase);
    }

    @Override
    public void addEntity(SmsAlert alert) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MAX_SCORE, alert.getMaxScore());
        values.put(KEY_TOTAL, alert.getTotal());
        values.put(KEY_DATE, alert.getDate());
        values.put(KEY_SOLUCE, alert.getSoluce());
        database.insert(TABLE_ALERTS, null, values);
        database.close();
    }

    public SmsAlert createEntity(Cursor cursor) {
        SmsAlert smsAlert = new SmsAlert();
        smsAlert.setId(cursor.getInt(0));
        smsAlert.setMaxScore(cursor.getInt(1));
        smsAlert.setTotal((cursor.getInt(2)));
        smsAlert.setDate((cursor.getInt(3)));
        smsAlert.setSoluce((cursor.getString(4)));
        return smsAlert;
    }

    public List<SmsAlert> getAllEntity() {
        List<SmsAlert> smsList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_ALERTS;
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                smsList.add(createEntity(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return smsList;
    }

    public int updateEntity(SmsAlert alert) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MAX_SCORE, alert.getMaxScore());
        values.put(KEY_TOTAL, alert.getTotal());
        values.put(KEY_DATE, alert.getDate());
        values.put(KEY_SOLUCE, alert.getSoluce());
        int i = database.update(TABLE_ALERTS, values, KEY_ID + " = ?", new String[]{String.valueOf(alert.getId())});
        database.close();
        return i;
    }

    public SmsAlert getEntity(Object object) {
        SQLiteDatabase database = getWritableDatabase();
        String param = KEY_DATE;
        Cursor cursor = database.query(TABLE_ALERTS, COLUMNS, param + "=?", new String[]{String.valueOf(object)}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            return createEntity(cursor);
        } else {
            return null;
        }
    }

    public void deleteEntity(Object object) {
        SQLiteDatabase database = getWritableDatabase();
        String value = String.valueOf(object);
        String param = KEY_DATE;
        try {
            database.delete(TABLE_ALERTS, param + " = ?", new String[]{value});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
    }

    public long insert(ContentValues contentValues) {
        SQLiteDatabase database = getWritableDatabase();
        long rowID = database.insert(TABLE_ALERTS, null, contentValues);
        database.close();
        return rowID;
    }

    public int update(ContentValues contentValues, String alertId) {
        SQLiteDatabase database = getWritableDatabase();
        int cnt = database.update(TABLE_ALERTS, contentValues, "_id=" + alertId, null);
        database.close();
        return cnt;
    }

    public int delete(String alertId) {
        SQLiteDatabase database = getWritableDatabase();
        int cnt = database.delete(TABLE_ALERTS, "_id=" + alertId, null);
        database.close();
        return cnt;
    }

    public Cursor getAllAlerts() {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(TABLE_ALERTS, COLUMNS, null, null, null, null, KEY_DATE + " asc ");
    }

    public Cursor getUserByID(String alertId) {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(TABLE_ALERTS, COLUMNS, "_id=" + alertId, null, null, null, KEY_DATE + " asc ");
    }

    public Cursor getLatestAlert() {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(TABLE_ALERTS, COLUMNS, null, null, null, null, KEY_ID + " DESC", "1");
    }
}

