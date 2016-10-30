package gasyidea.org.mobilereport.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gasyidea.org.mobilereport.models.SmsAlert;

@EBean
public class SmsDB extends SQLiteOpenHelper implements DBInterface<SmsAlert> {

    public static final String KEY_IP = "ip";
    public static final String KEY_DATE = "date";
    public static final String KEY_ATTACK = "attack";
    public static final String KEY_STATUS = "status";
    public static final String KEY_CODE_SOLUCE = "soluce";
    private static final String KEY_ID = "_id";
    private static final String[] COLUMNS = {KEY_ID, KEY_IP, KEY_DATE, KEY_ATTACK, KEY_STATUS, KEY_CODE_SOLUCE};
    private static final String TABLE_ALERTS = "alerts";
    private static final String CREATE_TABLE_ALERTS = "CREATE TABLE "
            + TABLE_ALERTS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_IP + " TEXT," +
            KEY_DATE + " TEXT," +
            KEY_ATTACK + " TEXT," +
            KEY_STATUS + " INTEGER," +
            KEY_CODE_SOLUCE + " TEXT" + ")";

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
        values.put(KEY_IP, alert.getId());
        values.put(KEY_DATE, alert.getDate());
        values.put(KEY_IP, alert.getIp());
        values.put(KEY_ATTACK, alert.getAttack());
        values.put(KEY_STATUS, alert.getStatus());
        values.put(KEY_CODE_SOLUCE, alert.getCodeSoluce());
        database.insert(TABLE_ALERTS, null, values);
        database.close();
    }

    public SmsAlert createEntity(Cursor cursor) {
        SmsAlert smsAlert = new SmsAlert();
        smsAlert.setId(cursor.getInt(0));
        smsAlert.setIp(cursor.getString(1));
        smsAlert.setDate(cursor.getString(2));
        smsAlert.setAttack((cursor.getString(3)));
        smsAlert.setStatus((cursor.getInt(4)));
        smsAlert.setCodeSoluce((cursor.getString(5)));
        return smsAlert;
    }

    public List<SmsAlert> getAllEntity() {
        List<SmsAlert> smsList = new ArrayList<>();
        String query = "SELECT COUNT(*) FROM " + TABLE_ALERTS + " group by " + KEY_IP;
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

    public HashMap<Integer, String> getStats() {
        HashMap<Integer, String> result = new HashMap<>();
        String query = "SELECT COUNT("+ KEY_STATUS +"), " + KEY_IP +  " FROM " + TABLE_ALERTS + " group by " + KEY_IP;
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                result.put(cursor.getInt(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public int updateEntity(SmsAlert alert) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_IP, alert.getIp());
        values.put(KEY_DATE, alert.getDate());
        values.put(KEY_IP, alert.getIp());
        values.put(KEY_ATTACK, alert.getAttack());
        values.put(KEY_STATUS, alert.getStatus());
        values.put(KEY_CODE_SOLUCE, alert.getCodeSoluce());
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
        return database.query(TABLE_ALERTS, COLUMNS, null, null, null, null, KEY_IP + " GROUP BY " + KEY_IP);
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
