package gasyidea.org.mobilereport.utils;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import org.androidannotations.annotations.EBean;

import java.sql.SQLException;

@EBean
public class UsersProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "gasyidea.org.mobilereport.utils.users";
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/users");
    private final static String ERROR = "Failed to insert : ";

    private static final int USERS = 1;
    private static final int USER_ID = 2;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "users", USERS);
        uriMatcher.addURI(PROVIDER_NAME, "users/#", USER_ID);
    }

    private UsersDB usersDB;

    @Override
    public boolean onCreate() {
        usersDB = new UsersDB(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2) {
        if (uriMatcher.match(uri) == USERS) {
            return usersDB.getAllUsers();
        } else {
            String userId = uri.getPathSegments().get(1);
            return usersDB.getUserByID(userId);
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long rowId = usersDB.insert(contentValues);
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
        if (uriMatcher.match(uri) == USER_ID) {
            String userId = uri.getPathSegments().get(1);
            result = usersDB.delete(userId);
        }
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int result = 0;
        if (uriMatcher.match(uri) == USER_ID) {
            String userId = uri.getPathSegments().get(1);
            result = usersDB.update(contentValues, userId);
        }
        return result;
    }
}
