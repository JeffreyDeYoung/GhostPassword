package com.github.ghostpassword.android;

import android.app.Application;
import android.widget.Toast;

import com.github.ghostpassword.ghostpasswordbackend.BlueToothDao;
import com.github.ghostpassword.ghostpasswordbackend.GhostPasswordException;

/**
 * Created by usouzj2 on 11/22/15.
 */
public class GhostPassword extends Application {
    private static GhostPassword s_instance;
    public BlueToothDao dao;

    public GhostPassword()
    {
        s_instance = this;
    }

    public static GhostPassword getApplication()
    {
        return s_instance;
    }
    public synchronized BlueToothDao getBtConnection() {
        if (dao == null) {
            try{
                dao = new BlueToothDao();
            } catch (GhostPasswordException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            // construct a BluetoothDevice object and put it into variable device
        }
        return dao;
    }
}
