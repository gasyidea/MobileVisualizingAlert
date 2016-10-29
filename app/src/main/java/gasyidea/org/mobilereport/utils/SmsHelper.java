package gasyidea.org.mobilereport.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Date;

import gasyidea.org.mobilereport.activities.DetailsActivity_;
import gasyidea.org.mobilereport.models.SmsAlert;

@EBean
public class SmsHelper {

    public static final String NO_ALERT = "NO_ALERT";

    @Bean
    SmsDB smsDB;

    public String createTextSms(View view) {
        Cursor cursor = smsDB.getLatestAlert();
        String result = NO_ALERT;
        if (cursor != null && cursor.moveToLast()) {
            SmsAlert smsAlert = smsDB.createEntity(cursor);
            Date theDate = new Date(smsAlert.getDate());
            int maxScore = smsAlert.getMaxScore();
            int total = smsAlert.getTotal();
            String soluce = smsAlert.getSoluce();
            result = String.format("<p><h1>Dernière Tentative d'intrusion détéctée le %s  <h1> </p>" +
                    "<p>Total des tentatives : <bold> %d </bold> <br> </p>" +
                    "<h3>Score maximum: %d </h3> <br>" +
                    "<p>  " +
                    "<ul>" +
                    "<li> %s   </li>" +
                    "</ul>" +
                    "</p>", theDate, total, maxScore, soluce);
        }
        updateComponents(view, result);
        return result;
    }

    public void showAlertDetails(Context context) {
        Intent intent = new Intent(context, DetailsActivity_.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    private void updateComponents(View view, String result) {
        if (result.equals(NO_ALERT)) {
            view.setEnabled(false);
        } else {
            view.setEnabled(true);
        }
    }
}
