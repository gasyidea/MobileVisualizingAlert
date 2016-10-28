package gasyidea.org.mobilereport.utils;

import android.net.Uri;

public class Constants {

    public static final String UNDERSCORE = "_";
    public static final String ZERO = "0";
    public static final String ALPHA = "[a-zA-Z]+";


    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PASS = "password";

    public static final String USER_ACTION = "user_action";
    public static final String USER_ID = "user_id";

    public static final String ADD_USER = "Add User";
    public static final String EDIT_USER = "Edit User";
    public static final String DELETE_USER = "Delete User";
    public static final String CONFIRM_DELETE_USER = "Are you sure to delete?";
    public static final String OK = "OK";

    public static final String USERS_PROVIDER_NAME = "gasyidea.org.mobilereport.utils.users";
    public static final Uri USERS_CONTENT_URI = Uri.parse("content://" + USERS_PROVIDER_NAME + "/users");
    public static final String USER_ERROR = "Failed to insert : ";

    public static final int USERS_PROVIDER = 1;
    public static final int USERS_ID = 2;

    public static final String S_USERS = "users";
    public static final String _S_USERS = "users/#";
    public static final String ADM = "adm";

    public static final int USER_ADD = 1;
    public static final int USER_EDIT = 2;
    public static final int USER_DEL = 3;
    public static final String USER = "user";
    public static final String ADMIN_USER = "rim_svr";
    public static final String PROMPT_EDIT_USER = "Please Select an user to edit";
    public static final String DEL_USER = "Please Select an user to delete";
    public static final String SIGN_IN = "Sign in";
    public static final String CANCEL = "Cancel";
    public static final String DIALOG = "dialog";
    public static final String EDITION = "Edition";
    public static int userAction = USER_ADD;

    public static final String MAIN_ACTIVITY = "gasyidea.org.mobilereport.activity.MainActivity";

}
