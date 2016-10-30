package gasyidea.org.mobilereport.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import gasyidea.org.mobilereport.activities.DetailsActivity_;
import gasyidea.org.mobilereport.models.SmsAlert;

@EBean
public class SmsHelper {

    public static final String NO_ALERT = "NO_ALERT";

    private String keyAttack;
    private String codeSoluce;

    @Bean
    SmsDB smsDB;

    public String createTextSms(View view) {
        Cursor cursor = smsDB.getLatestAlert();
        String result = NO_ALERT;
        if (cursor != null && cursor.moveToLast()) {
            SmsAlert smsAlert = smsDB.createEntity(cursor);
            String theDate = smsAlert.getDate();
            String ip = smsAlert.getIp();
            keyAttack = smsAlert.getAttack();
            int status = smsAlert.getStatus();
            codeSoluce = smsAlert.getCodeSoluce();
            result = String.format("<p><h1>Dernière Tentative d'intrusion détéctée le %s  <h1> </p><br>" +
                    "<p><h1> venant de : <bold> %s </bold> </h1><br> </p>" +
                    "<h1>Type de l'attaque: %s </h1> <br>" +
                    "<p><h1> <bold> succes de l'attaque: %d </bold> </h1></p>", theDate, ip, keyAttack, status);
        }
        updateComponents(view, result);
        return result;
    }

    public void showAlertDetails(Context context) {
        Intent intent = new Intent(context, DetailsActivity_.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("keyAttack", keyAttack);
        intent.putExtra("soluceCode", codeSoluce);
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
