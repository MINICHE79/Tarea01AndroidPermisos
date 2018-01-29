package com.example.inspiron.tarea01permisos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class App extends AppCompatActivity {
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app);
        lista = (ListView) findViewById(R.id.List);
    }

    public void llamar(View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

            } else {
                int permissionCall = 1;
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, permissionCall);
            }
        }else{
            String number = "tel:5556";
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(number));
            startActivity(callIntent);
        }

    }

    public void mensaje(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {

            } else {
                int permissionSms = 1;
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, permissionSms);
            }
        }else{
            PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, Object.class), 0);
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("5556", null, "prueba", pi, null);
        }
    }

    public void contactos(View view) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)){

            }else{
                int permissionContact = 1;
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS}, permissionContact);
            }
        }else {
            List<String> contacts = getContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            lista.setAdapter(adapter);
        }
    }

    private List<String> getContactNames(){
        List<String> contacts = new ArrayList<>();
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            }while(cursor.moveToNext());
        }
        cursor.close();

        return contacts;
    }
}
