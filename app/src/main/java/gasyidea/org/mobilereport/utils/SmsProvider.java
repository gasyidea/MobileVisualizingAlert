package gasyidea.org.mobilereport.utils;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import java.sql.SQLException;

public class SmsProvider extends ContentProvider{

    private static final String PROVIDER_NAME = "gasyidea.org.mobilereport.utils.alerts";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/alerts");
    private final static String ERROR = "Failed to insert : ";

    private static final int ALERTS = 1;
    private static final int ALERT_ID = 2;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "alerts", ALERTS);
        uriMatcher.addURI(PROVIDER_NAME, "alerts/#", ALERT_ID);
    }

    private SmsDB smsDB;

    @Override
    public boolean onCreate() {
        smsDB = new SmsDB(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2) {
        if (uriMatcher.match(uri) == ALERTS) {
            return smsDB.getAllAlerts();
        } else {
            String userId = uri.getPathSegments().get(1);
            return smsDB.getUserByID(userId);
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long rowId = smsDB.insert(contentValues);
        Uri _uri = null;
        if (rowId > 0) {
            _uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
        } else {
            try {
                throw new SQLException(ERROR + uri);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _uri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int result = 0;
        if (uriMatcher.match(uri) == ALERT_ID) {
            String userId = uri.getPathSegments().get(1);
            result = smsDB.delete(userId);
        }
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int result = 0;
        if (uriMatcher.match(uri) == ALERT_ID) {
            String userId = uri.getPathSegments().get(1);
            result = smsDB.update(contentValues, userId);
        }
        return result;
    }
}
