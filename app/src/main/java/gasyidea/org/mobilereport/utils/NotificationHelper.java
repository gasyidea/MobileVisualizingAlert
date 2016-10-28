package gasyidea.org.mobilereport.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;

@EBean
public class NotificationHelper {

    public void createAndShowToast(Context context, int layout) {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout, null);
        toast.setView(view);
        toast.show();
    }


}



