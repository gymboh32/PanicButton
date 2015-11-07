package org.ragecastle.panicbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        int id = v.getId();

        switch(id){
            case R.id.panic_button:
                try{

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("6315149716", null, "Send", null, null);

                } catch (Exception e){
                    Log.d(LOG_TAG, "Shits broke");
                }
        }
    }
}
