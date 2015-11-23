package com.github.ghostpassword.keepass2android_integration;

public class PluginAAccessReceiver
        extends keepass2android.pluginsdk.PluginAccessBroadcastReceiver
{

    @Override
    public ArrayList<String> getScopes() {
        ArrayList<String> scopes = new ArrayList<String>();
        //scopes.add(Strings.SCOPE_DATABASE_ACTIONS);
        scopes.add(Strings.SCOPE_CURRENT_ENTRY);
        return scopes;

    }

}