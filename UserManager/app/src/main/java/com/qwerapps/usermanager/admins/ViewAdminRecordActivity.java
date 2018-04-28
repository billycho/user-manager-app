package com.qwerapps.usermanager.admins;

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
import com.qwerapps.usermanager.R;
import com.qwerapps.usermanager.RecordArrayAdapter;
import com.qwerapps.usermanager.viewAdminDetails.ViewAdminDetailsActivity;
import com.qwerapps.usermanager.data.AdminDetails;
import com.qwerapps.usermanager.data.DatabaseHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewAdminRecordActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, AdminsContract.View {

    private DatabaseHelper databaseHelper = null;

    @BindView(R.id.listview)
    ListView listview;

    private int selectedRecordPosition = -1;
    private AdminsContract.Presenter adminPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_admin_record);

        ButterKnife.bind(this);

        adminPresenter = new AdminsPresenter(getHelper(), this);
        adminPresenter.getAdmins();


        ((TextView) findViewById(R.id.header_tv)).setText("View Admin Records");
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.list_item, listview, false);
        ((TextView) rowView.findViewById(R.id.admin_tv)).setText("Address");
        ((TextView) rowView.findViewById(R.id.member_name_tv)).setText("Admin Name");
        listview.addHeaderView(rowView);
    }

    private void populateNoRecordMsg()
    {
        if(adminPresenter.isListEmpty())
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
            adminPresenter.openAdminDetail(selectedRecordPosition);
            //showAdminDetail(adminList.get(selectedRecordPosition));
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
    {
        if(position > 0)
        {
            selectedRecordPosition = position - 1;
            showDeleteConfirmation();
        }

        return true;
    }



    @Override
    public void showAdmins(List<AdminDetails> admins)
    {
        try
        {
            listview.setAdapter(new RecordArrayAdapter(this, R.layout.list_item, admins, getHelper().getAdminDao()));
            listview.setOnItemClickListener(this);
            listview.setOnItemLongClickListener(this);

            populateNoRecordMsg();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void showDeleteConfirmation()
    {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Are you really want to delete the selected record ?");

        alertDialogBuilder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try
                        {

                            adminPresenter.deleteAdmin(selectedRecordPosition);
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

    @Override
    public void showAdminDetail(AdminDetails adminDetail)
    {
        final Intent intent = new Intent(this, ViewAdminDetailsActivity.class);
        intent.putExtra("details", adminDetail);
        startActivity(intent);
    }
}
