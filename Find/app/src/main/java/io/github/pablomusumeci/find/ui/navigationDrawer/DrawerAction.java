package io.github.pablomusumeci.find.ui.navigationDrawer;

/**
 * Created by florencia on 30/01/16.
 */
public enum DrawerAction {

    HOME("home"),
    SETTINGS("settings"),
    DASHBOARD("dashboard");

    String code;

    DrawerAction(String code){
        this.code = code;
    }

}
