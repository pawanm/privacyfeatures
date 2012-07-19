package compatibility.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import com.android.phoneagent.R;

import java.util.LinkedHashMap;
import java.util.Map;


public class SeparatedListAdapter extends BaseAdapter
{

    public final Map<String, Adapter> sections = new LinkedHashMap<String, Adapter>();
    public final ArrayAdapter<String> headers;

    public SeparatedListAdapter(Context context)
    {
        headers = new ArrayAdapter<String>(context, R.layout.seprated_list_view_header);
    }

    public void addSection(String section, Adapter adapter)
    {
        this.headers.add(section);
        this.sections.put(section, adapter);
    }

    public Object getItem(int position)
    {
        for (Object section : this.sections.keySet())
        {
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            if (position == 0) return section;
            if (position < size) return adapter.getItem(position - 1);

            position -= size;
        }
        return null;
    }

    public int getCount()
    {
        int total = 0;
        for (Adapter adapter : this.sections.values())
        {
            total += adapter.getCount() + 1;
        }
        return total;
    }

    public int getViewTypeCount()
    {
        int total = 1;
        for (Adapter adapter : this.sections.values())
        {
            total += adapter.getViewTypeCount();
        }
        return total;
    }

    public int getItemViewType(int position)
    {
        int type = 1;
        for (Object section : this.sections.keySet())
        {
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            if (position == 0)
            {
                return 0;
            }
            if (position < size)
            {
                return type + adapter.getItemViewType(position - 1);
            }

            position -= size;
            type += adapter.getViewTypeCount();
        }
        return -1;
    }

    public boolean isEnabled(int position)
    {
        return (getItemViewType(position) != 0);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        int sectionNum = 0;
        for (Object section : this.sections.keySet())
        {
            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            if (position == 0)
            {
                return headers.getView(sectionNum, convertView, parent);
            }
            if (position < size)
            {
                return adapter.getView(position - 1, convertView, parent);
            }

            position -= size;
            sectionNum++;
        }
        return null;
    }

    public long getItemId(int position)
    {
        return position;
    }

}
