package com.github.ghostpassword.android;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.ghostpassword.ghostpasswordbackend.BlueToothDao;
import com.github.ghostpassword.ghostpasswordbackend.GhostPasswordException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainScreen extends AppCompatActivity {
    public static final String PREFS_NAME = "GhostPasswordData";
    public String[] OTP_keys;
    //TODO: Figure out the right place for this
    private BlueToothDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        //this is an ugly hack
        System.setProperty("com.github.ghostpassword.filedir", getFilesDir().getAbsolutePath());
        try{
            dao = new BlueToothDao();
        } catch (GhostPasswordException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        HashSet<String> keys = (HashSet<String>)settings.getStringSet("otp_keys", null);
        System.out.print("Keys from memory");
        System.out.print(keys);
        if(keys == null) {
            keys = new HashSet<String>();
        }
        OTP_keys = keys.toArray(new String[keys.size()]);
        if(OTP_keys.length == 0){
            keys.add("Test");
            SharedPreferences.Editor editor = settings.edit();
            editor.putStringSet("otp_keys", keys);
            OTP_keys = keys.toArray(new String[keys.size()]);
        }
    }

    public void sendString(View view) {
        System.out.print("Keys from memory");
        System.out.print(OTP_keys);
        synchronized (this) {
            //BlueToothDao dao = new BlueToothDao();
            try {
                dao.write("This is a string!");
            } catch (GhostPasswordException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //TODO: Figure out when the right time to close it is, if ever
                //dao.close();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the user clicks the Send button
     */
    public void gotoOnetimeScreen(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayOneTimeActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the user clicks the Send button
     */
    public void gotoSavedPassScreen(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, CheckAndInit.class);
        startActivity(intent);
    }
}
