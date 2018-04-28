package com.qwerapps.usermanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.qwerapps.usermanager.data.AdminDetails;
import com.qwerapps.usermanager.data.MemberDetails;

import java.util.List;

/**
 * Created by IT02106 on 27/04/2018.
 */

public class RecordArrayAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater;
    private List records;

    private Dao<AdminDetails, Integer> adminDao;


    @SuppressWarnings("unchecked")
    public RecordArrayAdapter(Context context, int resource, List objects, Dao<AdminDetails, Integer> adminDao) {
        super(context, resource, objects);
        this.records = objects;
        this.adminDao = adminDao;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
            convertView = inflater.inflate(R.layout.list_item, parent, false);

        if(records.get(position).getClass().isInstance(new MemberDetails()))
        {
            final MemberDetails memberDetails = (MemberDetails) records.get(position);

            try
            {
                adminDao.refresh(memberDetails.admin);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            ((TextView) convertView.findViewById(R.id.member_name_tv)).setText(memberDetails.memberName);
            ((TextView) convertView.findViewById(R.id.admin_tv)).setText(memberDetails.admin.adminName);

        }
        else
        {
            final AdminDetails adminDetails = (AdminDetails) records.get(position);
            ((TextView) convertView.findViewById(R.id.member_name_tv)).setText(adminDetails.adminName);
            ((TextView) convertView.findViewById(R.id.admin_tv)).setText(adminDetails.address);
            //Log.d("asdaxxx",adminDetails.address);
        }

        return convertView;
    }
}
