package gasyidea.org.mobilereport.activities;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import gasyidea.org.mobilereport.R;
import gasyidea.org.mobilereport.utils.UserDialogFragment;
import gasyidea.org.mobilereport.utils.UsersDB;
import gasyidea.org.mobilereport.utils.UsersProvider;

import static gasyidea.org.mobilereport.utils.Constants.USER_ADD;
import static gasyidea.org.mobilereport.utils.Constants.USER_DEL;
import static gasyidea.org.mobilereport.utils.Constants.USER_EDIT;


@EActivity(R.layout.activity_admin)
public class AdminActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final String TITLE = "Mobile report";
    private static final String USER = "user";
    private static final String USER_ACTION = "user_action";
    private static final String USER_ID = "user_id";
    private static final String EDIT_USER = "Please Select an user to edit";
    private static final String DEL_USER = "Please Select an user to delete";
    @ViewById
    Button btnAdd, btnEdit, btnDel;
    @ViewById
    ListView usersList;
    private SimpleCursorAdapter cursorAdapter;

    @AfterViews
    void initAdapter() {
        UsersDB usersDB = new UsersDB(getApplicationContext());
        cursorAdapter = new SimpleCursorAdapter(getBaseContext(),
                android.R.layout.simple_list_item_single_choice, usersDB.getAllUsers(), new String[]{UsersDB.KEY_NAME, UsersDB.KEY_PASS},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
        usersList.setAdapter(cursorAdapter);
        usersList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Click
    void btnAdd() {
        Bundle args = new Bundle();
        args.putInt(USER_ACTION, USER_ADD);
        FragmentManager fm = getSupportFragmentManager();
        UserDialogFragment contact = new UserDialogFragment();
        contact.setArguments(args);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(contact, USER);
        ft.commit();
    }

    @Click
    void btnEdit() {
        int id = 0;
        int position = usersList.getCheckedItemPosition();
        if (cursorAdapter.getCursor().moveToPosition(position))
            id = Integer.parseInt(cursorAdapter.getCursor().getString(0));
        if (id == 0) {
            Toast.makeText(getBaseContext(), EDIT_USER, Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle args = new Bundle();
        args.putInt(USER_ID, id);
        args.putInt(USER_ACTION, USER_EDIT);
        FragmentManager fm = getSupportFragmentManager();
        UserDialogFragment contact = new UserDialogFragment();
        contact.setArguments(args);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(contact, USER);
        ft.commit();
    }

    @Click
    void btnDel() {
        int id = 0;
        int position = usersList.getCheckedItemPosition();
        if (cursorAdapter.getCursor().moveToPosition(position))
            id = Integer.parseInt(cursorAdapter.getCursor().getString(0));
        if (id == 0) {
            Toast.makeText(getBaseContext(), DEL_USER, Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle args = new Bundle();
        args.putInt(USER_ID, id);
        args.putInt(USER_ACTION, USER_DEL);
        FragmentManager fm = getSupportFragmentManager();
        UserDialogFragment contact = new UserDialogFragment();
        contact.setArguments(args);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(contact, USER);
        ft.commit();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle bundle) {
        Uri uri = UsersProvider.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
