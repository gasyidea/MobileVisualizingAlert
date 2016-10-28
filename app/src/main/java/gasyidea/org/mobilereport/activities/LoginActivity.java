package gasyidea.org.mobilereport.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import gasyidea.org.mobilereport.R;

public class LoginActivity extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView username;
    private EditText password;
    private FloatingActionButton admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (AutoCompleteTextView) findViewById(R.id.username);
        admin = (FloatingActionButton) findViewById(R.id.admin);

        password = (EditText) findViewById(R.id.password);
        password.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        admin.setOnClickListener(e -> {
            Toast.makeText(getApplicationContext(), "show admin view", Toast.LENGTH_LONG).show();
        });
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        username.setError(null);
        username.setError(null);

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
        return username.length() >= 4;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
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
            // TODO : Test user existance
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                //TODO  start the main activity

            } else {
                password.setError(getString(R.string.error_incorrect_password));
                password.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}

