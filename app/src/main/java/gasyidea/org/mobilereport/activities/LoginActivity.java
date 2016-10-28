package gasyidea.org.mobilereport.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gasyidea.org.mobilereport.R;
import gasyidea.org.mobilereport.models.User;
import gasyidea.org.mobilereport.utils.AdminDialogFragment;
import gasyidea.org.mobilereport.utils.UsersDB;

import static gasyidea.org.mobilereport.utils.Constants.DIALOG;
import static gasyidea.org.mobilereport.utils.Constants.KEY_NAME;
import static gasyidea.org.mobilereport.utils.Constants.MAIN_ACTIVITY;

public class LoginActivity extends AppCompatActivity {


    private UsersDB usersDB;

    private AutoCompleteTextView username;
    private EditText password;
    private FloatingActionButton admin;
    private Button signIn;

    private UserLoginTask mAuthTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usersDB = new UsersDB(getApplicationContext());

        username = (AutoCompleteTextView) findViewById(R.id.username);
        admin = (FloatingActionButton) findViewById(R.id.admin);
        password = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.sign_in_button);

        admin.setOnClickListener(e -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            AdminDialogFragment newFragment = new AdminDialogFragment();
            newFragment.show(transaction, DIALOG);
        });

        signIn.setOnClickListener(e -> {
            attemptLogin();
        });
    }

    private void attemptLogin() {
        String name = username.getText().toString();
        String pass = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(pass) && !isPasswordValid(pass)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)) {
            username.setError(getString(R.string.error_field_required));
            focusView = username;
            cancel = true;
        } else if (!isUsernameValid(name)) {
            username.setError(getString(R.string.error_invalid_username));
            focusView = username;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            mAuthTask = new UserLoginTask(name, pass);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 4;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            User user = usersDB.getEntity(mUsername);
            if (user != null) {
                if (user.getPassword().equals(mPassword)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                Intent intent = new Intent(getApplicationContext(), MainActivity_.class);
                startActivity(intent);
                finish();
            } else {
                username.setError("ERROR");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}

