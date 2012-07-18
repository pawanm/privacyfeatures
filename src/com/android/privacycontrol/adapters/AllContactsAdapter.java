package com.android.privacycontrol.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.privacycontrol.R;
import com.android.privacycontrol.entities.DeviceContact;

import java.util.ArrayList;
import java.util.List;

public class AllContactsAdapter extends BaseAdapter
{
    private final Context mContext;
    private static LayoutInflater mInflater;
    private List<DeviceContact> contacts;

    public AllContactsAdapter(Context context)
    {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        contacts = new ArrayList<DeviceContact>();
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    public int getCount()
    {
        return contacts.size();
    }

    public DeviceContact getItem(int position)
    {
        return contacts.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.list_item_contact, null);
            holder = new ViewHolder();
            holder.contactName = (TextView) convertView.findViewById(R.id.contactName);
            holder.contactPhone = (TextView) convertView.findViewById(R.id.contactPhone);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        DeviceContact entry = contacts.get(position);

        holder.contactName.setText(entry.getContactName());
        holder.contactPhone.setText(entry.getContactNumber());
        return convertView;
    }

    public void setContacts(List<DeviceContact> dataset)
    {
        contacts = dataset;
    }

    public static class ViewHolder
    {
        TextView contactName;
        TextView contactPhone;
    }


}

