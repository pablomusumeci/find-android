package io.github.pablomusumeci.find.utils;

import android.widget.EditText;

/**
 * Created by pablomusumeci on 5/1/16.
 */
public class ViewUtils {

    public static boolean isEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

}
