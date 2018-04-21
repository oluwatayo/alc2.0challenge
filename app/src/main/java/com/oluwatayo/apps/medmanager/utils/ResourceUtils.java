package com.oluwatayo.apps.medmanager.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.oluwatayo.apps.medmanager.R;

/**
 * Created by root on 4/19/18.
 */

public class ResourceUtils {

    public static Drawable getMedDrawable(Context context, String medType){
        int i;
        switch (medType){
            case "Tablet":
                i = R.drawable.ic_pill;
                break;
            case "Capsule":
                i = R.drawable.ic_pill_capsule;
                break;
            case "Syrup":
                i = R.drawable.ic_syrup;
                break;
            case "Injection":
                i = R.drawable.ic_anesthesia;
                break;
            default:
                i = R.drawable.ic_pill;

        }
        return context.getResources().getDrawable(i);
    }

    public static int getSmallIcon(String medType){
        int i;
        switch (medType){
            case "Tablet":
                i = R.drawable.ic_pill_white_fill;
                break;
            case "Capsule":
                i = R.drawable.ic_pill_capsule_white_fill;
                break;
            case "Syrup":
                i = R.drawable.ic_syrup_white_fill;
                break;
            case "Injection":
                i = R.drawable.ic_anesthesia_white_fill;
                break;
            default:
                i = R.drawable.ic_pill_white_fill;

        }
        return i;
    }

    public static Bitmap getLargeIcon(Context context){
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.med);
    }
}
