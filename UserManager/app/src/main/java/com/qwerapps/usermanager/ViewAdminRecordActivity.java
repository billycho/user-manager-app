package com.qwerapps.usermanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.qwerapps.usermanager.data.AdminDetails;
import com.qwerapps.usermanager.data.DatabaseHelper;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewAdminRecordActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private DatabaseHelper databaseHelper = null;

    @BindView(R.id.listview)
    ListView listview;

    private int selectedRecordPosition = -1;

    private Dao<AdminDetails, Integer> adminDao;

    private List<AdminDetails> adminList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_admin_record);

        ButterKnife.bind(this);
        ((TextView) findViewById(R.id.header_tv)).setText("View Admin Records");

        try
        {
            adminDao = getHelper().getAdminDao();

            adminList = adminDao.queryForAll();

            final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.list_item, listview, false);
            ((TextView) rowView.findViewById(R.id.admin_tv)).setText("Address");
            ((TextView) rowView.findViewById(R.id.member_name_tv)).setText("Admin Name");
            listview.addHeaderView(rowView);

            listview.setAdapter(new RecordArrayAdapter(this, R.layout.list_item, adminList, adminDao));
            listview.setOnItemClickListener(this);
            listview.setOnItemLongClickListener(this);

            populateNoRecordMsg();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void populateNoRecordMsg()
    {
        if(adminList.size() == 0)
        {
            final TextView tv = new TextView(this);
            tv.setPadding(5,5,5,5);
            tv.setTextSize(15);
            tv.setText("No Record Found !!");
            listview.addFooterView(tv);
        }
    }

    private DatabaseHelper getHelper()
    {
        if(databaseHelper == null)
        {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }

        return databaseHelper;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if(databaseHelper != null)
        {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if(position > 0)
        {
            selectedRecordPosition = position - 1;
            final Intent intent = new Intent(this, ViewAdminDetailsActivity.class);
            intent.putExtra("details", adminList.get(selectedRecordPosition));
            startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
    {
        if(position > 0)
        {
            selectedRecordPosition = position - 1;
            showDialog();
        }

        return true;
    }

    private void showDialog()
    {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Are you really want to delete the selected record ?");

        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try
                        {
                            adminDao.delete(adminList.get(selectedRecordPosition));

                            adminList.remove(selectedRecordPosition);
                            listview.invalidateViews();;
                            selectedRecordPosition = -1;
                            populateNoRecordMsg();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                }
        );

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
