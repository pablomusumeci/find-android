package io.github.pablomusumeci.find.ui.navigationDrawer;

import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.Fragment;
import io.github.pablomusumeci.find.ui.fragments.MainFragment;
import io.github.pablomusumeci.find.ui.fragments.SettingsFragment;

/**
 * Created by florencia on 30/01/16.
 */
public class DrawerFragmentsBuilder {

    private static DrawerFragmentsBuilder instance;

    private Map<DrawerAction, FragmentBuilder> navigationDrawer;

    private DrawerFragmentsBuilder(){

        navigationDrawer = new HashMap<>();
        navigationDrawer.put(DrawerAction.HOME, new FragmentBuilder() {
            public Fragment buildFragment() {
                return new MainFragment();
            }
        });
        navigationDrawer.put(DrawerAction.SETTINGS, new FragmentBuilder() {
            public Fragment buildFragment() {
                return new SettingsFragment();
            }
        });

    }

    public static DrawerFragmentsBuilder getInstance(){
        if(instance == null)
            instance = new DrawerFragmentsBuilder();

        return instance;
    }

    public Fragment getFragment(DrawerAction action){
        if (navigationDrawer.containsKey(action))
            return navigationDrawer.get(action).buildFragment();

        return null;
    }

    private interface FragmentBuilder {
        Fragment buildFragment();
    }

}
