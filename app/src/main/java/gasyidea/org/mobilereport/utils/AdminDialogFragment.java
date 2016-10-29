package gasyidea.org.mobilereport.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import gasyidea.org.mobilereport.R;

import gasyidea.org.mobilereport.activities.AdminActivity_;
import gasyidea.org.mobilereport.models.User;

import static gasyidea.org.mobilereport.utils.Constants.ADM;
import static gasyidea.org.mobilereport.utils.Constants.ADMIN_USER;
import static gasyidea.org.mobilereport.utils.Constants.CANCEL;
import static gasyidea.org.mobilereport.utils.Constants.SIGN_IN;

public class AdminDialogFragment extends DialogFragment {

    private NotificationHelper notificationHelper;

    private UsersDB usersDB;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        usersDB = new UsersDB(getActivity());
        final View view = createView(getActivity(), R.layout.admin_validation_layout);
        final EditText userInput = (EditText) view.findViewById(R.id.txt_username);
        final EditText passInput = (EditText) view.findViewById(R.id.txt_password);
        notificationHelper = new NotificationHelper();
        super.onCreateDialog(savedInstanceState);
        return createAlertDialog(getActivity(), view, userInput, passInput, SIGN_IN, CANCEL);
    }

    private void createOrSignUser(UsersDB db, String username, String passwd) {
        if (username.equals(ADMIN_USER)) {
            if (passwd.length() > 6) {
                final String pass = ADM + username;
                User user = db.getEntity(username);
                if (user != null) {
                    if (passwd.equals(pass)) {
                        startActivity(getActivity(), AdminActivity_.class);
                    } else {
                        notificationHelper.createAndShowToast(getActivity().getApplicationContext(), R.layout.user_toast_layout);
                    }
                } else {
                    db.addEntity(createAdminUser(username, pass));
                    startActivity(getActivity(), AdminActivity_.class);
                }
            } else {
                notificationHelper.createAndShowToast(getActivity().getApplicationContext(), R.layout.user_toast_layout);
            }
        } else {
            notificationHelper.createAndShowToast(getActivity().getApplicationContext(), R.layout.user_toast_layout);
        }
    }

    private User createAdminUser(String name, String pass) {
        return new User(name, pass);
    }

    private void startActivity(Activity activity, Class z) {
        Intent intent = new Intent(activity, z);
        startActivity(intent);
    }

    private View createView(Activity activity, int layout) {
        return LayoutInflater.from(activity).inflate(layout, null);
    }

    private AlertDialog createAlertDialog(Activity activity, View view, EditText a, EditText b, String pos, String neg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view)
                .setPositiveButton(pos, (dialog, id) -> {
                    String username = a.getText().toString();
                    String password = b.getText().toString();
                    createOrSignUser(usersDB, username, password);
                })
                .setNegativeButton(neg, (dialog, id) -> {
                    AdminDialogFragment.this.getDialog().cancel();
                });
        return builder.create();
    }
}
