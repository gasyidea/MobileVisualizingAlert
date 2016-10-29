package gasyidea.org.mobilereport.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import gasyidea.org.mobilereport.R;
import gasyidea.org.mobilereport.utils.SmsHelper;

@EActivity(R.layout.activity_basic)
public class BasicActivity extends AppCompatActivity {

    @Bean
    SmsHelper helper;

    @ViewById
    HtmlTextView html_text;

    @ViewById
    Button details;

    @AfterViews
    void showTextMessage() {
        html_text.setHtml(helper.createTextSms(details));
    }

    @Click
    void details() {
        helper.showAlertDetails(getApplicationContext());
    }
}
