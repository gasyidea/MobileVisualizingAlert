package gasyidea.org.mobilereport.utils;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import org.androidannotations.annotations.EFragment;

import gasyidea.org.mobilereport.R;

import static gasyidea.org.mobilereport.utils.Constants.ADD_USER;
import static gasyidea.org.mobilereport.utils.Constants.CANCEL;
import static gasyidea.org.mobilereport.utils.Constants.CONFIRM_DELETE_USER;
import static gasyidea.org.mobilereport.utils.Constants.DELETE_USER;
import static gasyidea.org.mobilereport.utils.Constants.EDIT_USER;
import static gasyidea.org.mobilereport.utils.Constants.KEY_NAME;
import static gasyidea.org.mobilereport.utils.Constants.KEY_PASS;
import static gasyidea.org.mobilereport.utils.Constants.OK;
import static gasyidea.org.mobilereport.utils.Constants.USERS_CONTENT_URI;
import static gasyidea.org.mobilereport.utils.Constants.USER_ACTION;
import static gasyidea.org.mobilereport.utils.Constants.USER_ADD;
import static gasyidea.org.mobilereport.utils.Constants.USER_DEL;
import static gasyidea.org.mobilereport.utils.Constants.USER_EDIT;
import static gasyidea.org.mobilereport.utils.Constants.USER_ID;
import static gasyidea.org.mobilereport.utils.Constants.userAction;

@EFragment
public class UserDialogFragment extends DialogFragment implements LoaderCallbacks<Cursor> {

    private TextInputEditText txtName;
    private EditText txtPass;
    protected CoordinatorLayout snackbarCoordinatorLayout;
    private View userView;
    private int userId = 0;
    private NotificationHelper notificationHelper;

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        txtName = (TextInputEditText) userView.findViewById(R.id.txtName);
        txtPass = (EditText) userView.findViewById(R.id.txtPass);
        snackbarCoordinatorLayout = (CoordinatorLayout) userView.findViewById(R.id.snackbarCoordinatorLayout);
        getLoaderManager().initLoader(0, null, this);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        notificationHelper = new NotificationHelper();
        if (bundle != null) {
            if (bundle.containsKey(USER_ACTION))
                userAction = bundle.getInt(USER_ACTION);
            if (bundle.containsKey(USER_ID))
                userId = bundle.getInt(USER_ID);
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        userView = getActivity().getLayoutInflater().inflate(R.layout.user_layout, null);

        DialogInterface.OnClickListener clickListener = (dialog, which) -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_NAME, txtName.getText().toString());
            contentValues.put(KEY_PASS, txtPass.getText().toString());
            switch (userAction) {
                case USER_ADD:
                    if (isValidContentValues(contentValues)) {
                        getActivity().getContentResolver().insert(USERS_CONTENT_URI, contentValues);
                        break;
                    } else {
                        notificationHelper.createAndShowToast(getActivity().getApplicationContext(), R.layout.admin_toast_layout);
                        break;
                    }
                case USER_EDIT:
                    Uri uri = USERS_CONTENT_URI;
                    String pathSegment = Integer.toString(userId);
                    uri = Uri.withAppendedPath(uri, pathSegment);
                    if (isValidContentValues(contentValues)) {
                        getActivity().getContentResolver().update(uri, contentValues, null, null);
                        break;
                    } else {
                        notificationHelper.createAndShowToast(getActivity().getApplicationContext(), R.layout.admin_toast_layout);
                        break;
                    }
                case USER_DEL:
                    uri = USERS_CONTENT_URI;
                    pathSegment = Integer.toString(userId);
                    uri = Uri.withAppendedPath(uri, pathSegment);
                    getActivity().getContentResolver().delete(uri, null, null);
            }
            getActivity().getSupportLoaderManager().restartLoader(0, null, (LoaderCallbacks<Cursor>) getActivity());
        };

        switch (userAction) {
            case USER_ADD:
                dialogBuilder.setView(userView);
                dialogBuilder.setTitle(ADD_USER);
                break;
            case USER_EDIT:
                dialogBuilder.setView(userView);
                dialogBuilder.setTitle(EDIT_USER);
                break;
            case USER_DEL:
                dialogBuilder.setTitle(DELETE_USER);
                dialogBuilder.setMessage(CONFIRM_DELETE_USER);
                break;
        }

        dialogBuilder.setNegativeButton(CANCEL, null);
        dialogBuilder.setPositiveButton(OK, clickListener);
        return dialogBuilder.create();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        if (userAction != USER_EDIT) {
            return null;
        } else {
            Uri uri = USERS_CONTENT_URI;
            String pathSegment = Integer.toString(userId);
            uri = Uri.withAppendedPath(uri, pathSegment);
            return new CursorLoader(getActivity(), uri, null, null, null, null);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor data) {
        if (data.moveToFirst()) {
            txtName.setText(data.getString(1));
            txtPass.setText(data.getString(2));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
    }

    private boolean isValidContentValues(ContentValues values) {
        boolean isOk = false;
        if (values.containsKey(KEY_NAME)) {
            String value = String.valueOf(values.get(KEY_NAME));
            if (value.trim().length() > 0) {
                isOk = value.length() > 4;
            }
        }
        if (values.containsKey(KEY_PASS)) {
            String name = String.valueOf(values.get(KEY_NAME));
            isOk = name.length() >= 6;
        }
        return isOk;
    }
}
