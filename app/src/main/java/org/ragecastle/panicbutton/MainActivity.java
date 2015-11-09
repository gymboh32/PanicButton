package org.ragecastle.panicbutton;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private final int PICK_CONTACT = 0;
    private String phoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onContactClick(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }

    public void onClick(View v){
        int id = v.getId();
        phoneNum = "5555555555";

        switch(id){
            case R.id.panic_button:
                try{

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNum, null, "Send", null, null);

                } catch (Exception e){
                    Log.d(LOG_TAG, "Shits broke");
                }
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor cursor =  getContentResolver().query(contactData, null, null, null, null);

                    while (cursor.moveToNext()) {
                        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                        String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1"))
                            hasPhone = "true";
                        else
                            hasPhone = "false";

                        if (Boolean.parseBoolean(hasPhone)) {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (phones.moveToNext()) {
                                phoneNum = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            }
                            phones.close();
                        }
                    }
                    Toast.makeText(MainActivity.this, phoneNum, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
