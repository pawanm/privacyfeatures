package com.android.phoneagent.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.phoneagent.R;
import com.android.phoneagent.entities.ContactState;

public class Utils
{
    private static Typeface FONT_REGULAR;
    private static Typeface FONT_BOLD;

    public static int getColorForContactState(ContactState contactState)
    {
        switch (contactState)
        {
            case FAVOURITE:
                return R.color.favouriteContact;
            case RESTRICTED:
                return R.color.restrictedContact;
            default:
                return R.color.normalContact;
        }
    }

    public static void initializeFonts(Context context)
    {
        FONT_REGULAR = Typeface.createFromAsset(context.getAssets(), "font/roboto_regular.ttf");
        FONT_BOLD = Typeface.createFromAsset(context.getAssets(), "font/roboto_bold.ttf");
    }

    public static void overrideFontToRobotoRegular(final View view)
    {
        overrideFont(view, FONT_REGULAR);
    }

    public static void overrideFontToRobotoBold(final View view)
    {
        overrideFont(view, FONT_BOLD);
    }

    private static void overrideFont(final View view, Typeface type)
    {
        if (view instanceof ViewGroup)
        {
            ViewGroup view1 = (ViewGroup) view;
            for (int i = 0; i < view1.getChildCount(); i++)
            {
                View child = view1.getChildAt(i);
                overrideFont(child, type);

            }

        }
        else if (view instanceof TextView)
        {
            ((TextView) view).setTypeface(type);
        }

    }
}
