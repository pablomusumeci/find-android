package io.github.pablomusumeci.find.domain.model;

import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by pablomusumeci on 5/3/16.
 */
public class ApplicationSettings {

    private static final String USERNAME = "username";
    private static final String GROUP_NAME = "group_name";
    private static final String SERVER_ADDRESS = "server_address";
    private static final String DEFAULT_SERVER_ADDRESS = "https://ml.internalpositioning.com/";

    public static String getUsername() {
        return Prefs.getString(USERNAME, "");
    }

    public static String getServerAddress() {
        return Prefs.getString(SERVER_ADDRESS, DEFAULT_SERVER_ADDRESS);
    }

    public static String getGroupName() {
        return Prefs.getString(GROUP_NAME, "");
    }

    public static void setUsername(String username) {
        Prefs.putString(USERNAME, username);
    }

    public static void setServerAddress(String serverAddress) {
        Prefs.putString(SERVER_ADDRESS, serverAddress);
    }

    public static void setGroupName(String groupName) {
        Prefs.putString(GROUP_NAME, groupName);
    }


}
