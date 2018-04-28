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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.qwerapps.usermanager.data.AdminDetails;
import com.qwerapps.usermanager.data.DatabaseHelper;
import com.qwerapps.usermanager.data.MemberDetails;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewMemberRecordActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    private DatabaseHelper databaseHelper = null;

    @BindView(R.id.listview)
    ListView listview;

    private int selectedRecordPosition = -1;

    private Dao<AdminDetails, Integer> adminDao;
    private Dao<MemberDetails, Integer> memberDao;

    private List<MemberDetails> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_member_record);

        ButterKnife.bind(this);

        try {
            adminDao = getHelper().getAdminDao();
            memberDao = getHelper().getMemberDao();

            memberList = memberDao.queryForAll();

            final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.list_item, listview, false);
            listview.addHeaderView(rowView);

            listview.setAdapter(new RecordArrayAdapter(this, R.layout.list_item, memberList, adminDao));

            listview.setOnItemLongClickListener(this);
            listview.setOnItemClickListener(this);

            populateNoRecordMsg();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void populateNoRecordMsg()
    {
        if(memberList.size()  == 0)
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
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }

        return databaseHelper;
    }

    @Override
    protected  void onDestroy()
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
                            memberDao.delete(memberList.get(selectedRecordPosition));

                            memberList.remove(selectedRecordPosition);
                            listview.invalidateViews();

                            selectedRecordPosition = -1;
                            populateNoRecordMsg();

                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
