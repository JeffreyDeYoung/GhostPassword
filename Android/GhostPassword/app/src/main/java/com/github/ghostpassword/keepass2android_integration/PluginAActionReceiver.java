package com.github.ghostpassword.keepass2android_integration;


import com.github.ghostpassword.android.GhostPassword;
import com.github.ghostpassword.android.R;
import com.github.ghostpassword.ghostpasswordbackend.BlueToothDao;
import com.github.ghostpassword.ghostpasswordbackend.GhostPasswordException;

import keepass2android.pluginsdk.PluginAccessException;
import keepass2android.pluginsdk.Strings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by jeffrey on 11/23/15.
 */
public class PluginAActionReceiver
        extends keepass2android.pluginsdk.PluginActionBroadcastReceiver {
    @Override
    protected void actionSelected(ActionSelectedAction actionSelected) {
        if (actionSelected.isEntryAction()) {
            Toast.makeText(actionSelected.getContext(), "PluginA rocks!", Toast.LENGTH_SHORT).show();
        } else {

            String fieldId = actionSelected.getFieldId().substring(Strings.PREFIX_STRING.length());
            //Toast.makeText(actionSelected.getContext(), actionSelected.getActionData().getString("text"), Toast.LENGTH_SHORT).show();
            Toast.makeText(actionSelected.getContext(), actionSelected.getEntryFields().get(fieldId), Toast.LENGTH_SHORT).show();

            String password = actionSelected.getEntryFields().get(fieldId);

            GhostPassword context = (GhostPassword) actionSelected.getContext().getApplicationContext();
            BlueToothDao dao = (context).getBtConnection();

            try {
                dao.write(password);
            } catch (GhostPasswordException e) {
                e.printStackTrace();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*try{
                synchronized (this) {
                    BlueToothDao dao = new BlueToothDao();
                    try {
                        dao.write(password);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        dao.close();
                    }
                }}catch (Exception e){
                e.printStackTrace();//TODO: ensure password doesn't get logged and handle exceptions better

            }
            */
        }

    }

    @Override
    protected void openEntry(OpenEntryAction oe) {

        try {

            Bundle bField = new Bundle();
            String password = oe.getEntryFields().get("Password");
            //Log.i("Ghostpass", "The password is: " + password);
            bField.putString("text", oe.getEntryFields().get("Password"));
            oe.addEntryFieldAction("keepass2android.plugina.bla", Strings.PREFIX_STRING + "Password", "Send to GhostPassword", R.mipmap.pacman_ghost_2, bField);
            //oe.addEntryFieldAction("keepass2android.plugina.bla", Strings.PREFIX_STRING + "UserName", "Be nice!", R.mipmap.ic_launcher, null);

            //Bundle bEntry = new Bundle();
            //oe.addEntryAction("PluginA", R.mipmap.ic_launcher, bEntry);

            //oe.setEntryField("newFieldFromPluginA", "I love pluginA", false);
           // //oe.setEntryField("SecretFieldFromPluginA", "I love pluginA uiadertn", true);
            //oe.setEntryField("UserName", "Batman (Overwritten by PluginA)", true);
/*
            try{
            synchronized (this) {
                BlueToothDao dao = new BlueToothDao();
                try {
                    dao.write(password);
                } catch (IOException e) {
                    dao.close();
                } finally {
                    dao.close();
                }
            }}catch (Exception e){
                e.printStackTrace();//TODO: ensure password doesn't get logged and handle exceptions better

            }
*/
        } catch (PluginAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void dbAction(DatabaseAction db) {

        Log.d("PluginA", db.getAction() + " in file " + db.getFileDisplayName() + " (" + db.getFilePath() + ")");
    }
}