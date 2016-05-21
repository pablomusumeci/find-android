package io.github.pablomusumeci.find.ui.navigationDrawer;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;

import io.github.pablomusumeci.find.ui.activities.NavigationDrawerListener;
import io.github.pablomusumeci.find.ui.fragments.MainFragment;
import io.github.pablomusumeci.find.ui.fragments.SettingsFragment;

public class DrawerManager {

    private static final String URL="http://www.google.com";
    private static DrawerManager instance;

    private Map<DrawerAction, TransactionManager> navigationDrawer;

    private DrawerManager(){

        navigationDrawer = new HashMap<>();
        navigationDrawer.put(DrawerAction.HOME, new TransactionManager() {
            public void beginTransaction(NavigationDrawerListener listener) {
                listener.startFragment(new MainFragment());
            }
        });
        navigationDrawer.put(DrawerAction.SETTINGS, new TransactionManager() {
            public void beginTransaction(NavigationDrawerListener listener) {
                listener.startFragment(new SettingsFragment());
            }
        });
        navigationDrawer.put(DrawerAction.DASHBOARD, new TransactionManager() {
            public void beginTransaction(NavigationDrawerListener listener) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                listener.startActivityWithIntent(browserIntent);
            }
        });

    }

    public static DrawerManager getInstance(){
        if(instance == null)
            instance = new DrawerManager();

        return instance;
    }

    public void startTransaction(DrawerAction action, NavigationDrawerListener listener){
        if (navigationDrawer.containsKey(action))
            navigationDrawer.get(action).beginTransaction(listener);
    }

    private interface TransactionManager {
        void beginTransaction(NavigationDrawerListener listener);
    }

}
