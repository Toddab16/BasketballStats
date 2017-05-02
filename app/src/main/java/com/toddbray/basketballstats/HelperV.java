package com.toddbray.basketballstats;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Brad on 5/1/2017.
 */

public class HelperV {

    public static boolean isValidString(String s, Context c) {

        if(s.isEmpty()) {
            Toast.makeText(c, "Required Field Missing", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
