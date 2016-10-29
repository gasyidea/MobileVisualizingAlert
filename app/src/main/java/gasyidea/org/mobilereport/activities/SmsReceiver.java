package gasyidea.org.mobilereport.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsMessage;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EReceiver;

import gasyidea.org.mobilereport.models.SmsAlert;
import gasyidea.org.mobilereport.utils.SmsDB;

@EReceiver
public class SmsReceiver extends BroadcastReceiver {

    private static final String SMS_EXTRA = "pdus";

    @Bean
    SmsDB smsDB;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get(SMS_EXTRA);
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdus[i]);
                String body = sms.getMessageBody();
                if (body.startsWith("%") && body.endsWith("@")) {
                    String data = decodeData(body);
                    Object object = createSmsEntity(data);
                    if (object instanceof SmsAlert) {
                        SmsAlert smsAlert = (SmsAlert) object;
                        saveToDB(smsAlert);
                        abortBroadcast();
                    }
                }
            }
            return;
        }
    }

    private Object createSmsEntity(String data) {
        String[] result = data.split("_");
        return new SmsAlert(Integer.parseInt(result[0]), Integer.parseInt(result[1]), Integer.parseInt(result[2]), result[3]);
    }

    private String decodeData(String body) {
        String temp = body.replace("%", "");
        temp = temp.replace("@", "");
        return temp;
    }

    private void saveToDB(SmsAlert smsAlert) {
        smsDB.addEntity(smsAlert);
    }
}
