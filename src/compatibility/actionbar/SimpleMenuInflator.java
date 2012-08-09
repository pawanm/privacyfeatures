package compatibility.actionbar;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SimpleMenuInflator extends MenuInflater
{
    private static final String MENU_RES_NAMESPACE = "http://schemas.android.com/apk/res/android";
    private static final String MENU_ATTR_ID = "id";
    private static final String MENU_ATTR_SHOW_AS_ACTION = "showAsAction";
    private static final String MENU_ATTR_ANIMATION = "animation";
    private MenuInflater mInflater;
    private Context mActivity;
    private OnInflate mCallback;

    public SimpleMenuInflator(Context context, MenuInflater inflater, OnInflate callback)
    {
        super(context);
        mInflater = inflater;
        mActivity = context;
        mCallback = callback;
    }

    @Override
    public void inflate(int menuRes, Menu menu)
    {
        loadActionBarMetadata(menuRes);
        mInflater.inflate(menuRes, menu);
    }

    private void loadActionBarMetadata(int menuResId)
    {
        Set<Integer> actionItemIds = new HashSet<Integer>();
        Set<Integer> animationItemIds = new HashSet<Integer>();
        XmlResourceParser parser = null;
        try
        {
            parser = mActivity.getResources().getXml(menuResId);

            int eventType = parser.getEventType();
            int itemId;
            int showAsAction;

            boolean eof = false;
            while (!eof)
            {
                switch (eventType)
                {
                    case XmlPullParser.START_TAG:
                        if (!parser.getName().equals("item"))
                        {
                            break;
                        }

                        itemId = parser.getAttributeResourceValue(MENU_RES_NAMESPACE, MENU_ATTR_ID, 0);
                        if (itemId == 0)
                        {
                            break;
                        }

                        showAsAction = parser.getAttributeIntValue(MENU_RES_NAMESPACE, MENU_ATTR_SHOW_AS_ACTION, -1);
                        if (showAsAction != -1 && (showAsAction & MenuItem.SHOW_AS_ACTION_ALWAYS) == MenuItem.SHOW_AS_ACTION_ALWAYS)
                        {
                            actionItemIds.add(itemId);
                        }

                        if (parser.getAttributeBooleanValue(null, MENU_ATTR_ANIMATION, false))
                        {
                            animationItemIds.add(itemId);
                        }
                        break;


                    case XmlPullParser.END_DOCUMENT:
                        eof = true;
                        break;
                }

                eventType = parser.next();
            }
        }
        catch (XmlPullParserException e)
        {
            throw new InflateException("Error inflating menu XML", e);
        }
        catch (IOException e)
        {
            throw new InflateException("Error inflating menu XML", e);
        }
        finally
        {
            if (parser != null)
            {
                parser.close();
            }
        }

        mCallback.onInflate(actionItemIds, animationItemIds);
    }

}

