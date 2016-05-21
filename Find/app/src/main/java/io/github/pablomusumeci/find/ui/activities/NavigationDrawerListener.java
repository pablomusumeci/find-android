package io.github.pablomusumeci.find.ui.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;

public interface NavigationDrawerListener {

    void startActivityWithIntent(Intent intent);
    void startFragment(Fragment fragment);

}
