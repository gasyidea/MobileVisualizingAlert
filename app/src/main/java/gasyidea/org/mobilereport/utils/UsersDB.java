package gasyidea.org.mobilereport.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import gasyidea.org.mobilereport.models.User;

public class UsersDB extends SQLiteOpenHelper implements DBInterface<User> {

    public static final String KEY_NAME = "name";
    public static final String KEY_PASS = "password";
    private static final String KEY_ID = "_id";
    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_PASS};
    private static final String TABLE_USERS = "users";
    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            KEY_NAME + " TEXT," +
            KEY_PASS + " TEXT" + ")";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "users.db";

    public UsersDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        onCreate(sqLiteDatabase);
    }

    public void addEntity(User user) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PASS, user.getPassword());
        database.insert(TABLE_USERS, null, values);
        database.close();
    }

    public User createEntity(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(0));
        user.setName(cursor.getString(1));
        user.setPassword((cursor.getString(2)));
        return user;
    }

    public List<User> getAllEntity() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                users.add(createEntity(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    public int updateEntity(User user) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PASS, user.getPassword());
        int i = database.update(TABLE_USERS, values, KEY_ID + " = ?", new String[]{String.valueOf(user.getId())});
        database.close();
        return i;
    }

    public User getEntity(Object object) {
        SQLiteDatabase database = getWritableDatabase();
        String param = KEY_NAME;
        Cursor cursor = database.query(TABLE_USERS, COLUMNS, param + "=?", new String[]{String.valueOf(object)}, null, null, null, null);
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
        String param = KEY_NAME;
        try {
            database.delete(TABLE_USERS, param + " = ?", new String[]{value});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
    }

    public long insert(ContentValues contentValues) {
        SQLiteDatabase database = getWritableDatabase();
        long rowID = database.insert(TABLE_USERS, null, contentValues);
        database.close();
        return rowID;
    }

    public int update(ContentValues contentValues, String userId) {
        SQLiteDatabase database = getWritableDatabase();
        int cnt = database.update(TABLE_USERS, contentValues, "_id=" + userId, null);
        database.close();
        return cnt;
    }

    public int delete(String userId) {
        SQLiteDatabase database = getWritableDatabase();
        int cnt = database.delete(TABLE_USERS, "_id=" + userId, null);
        database.close();
        return cnt;
    }

    public Cursor getAllUsers() {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(TABLE_USERS, COLUMNS, null, null, null, null, KEY_NAME + " asc ");
    }

    public Cursor getUserByID(String userId) {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(TABLE_USERS, COLUMNS, "_id=" + userId, null, null, null, KEY_NAME + " asc ");
    }
}
