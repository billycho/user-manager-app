package com.qwerapps.usermanager.addAdmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.qwerapps.usermanager.R;
import com.qwerapps.usermanager.viewAdminDetails.ViewAdminDetailsActivity;
import com.qwerapps.usermanager.data.AdminDetails;
import com.qwerapps.usermanager.data.DatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminAddActivity extends AppCompatActivity  implements View.OnClickListener, AdminAddContract.View{

    private DatabaseHelper databaseHelper = null;

    @BindView(R.id.admin_name_et)
    EditText admin_name_et;

    @BindView(R.id.address_et)
    EditText address_et;

    @BindView(R.id.reset_btn)
    Button reset_btn;

    @BindView(R.id.submit_btn)
    Button submit_btn;

    private AdminAddPresenter adminsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);

        ButterKnife.bind(this);

        adminsPresenter = new AdminAddPresenter(getHelper(),this);
        reset_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
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
    public void onClick(View v)
    {
        if(v == submit_btn)
        {
            if(admin_name_et.getText().toString().trim().length() > 0 &&
                    address_et.getText().toString().trim().length() > 0)
            {
                final AdminDetails adminDetails = new AdminDetails();

                adminDetails.adminName = admin_name_et.getText().toString();
                adminDetails.address = address_et.getText().toString();

                adminsPresenter.addAdmin(adminDetails);
                resetInput();
                adminAdded(adminDetails);
            }
            else
            {
                showMessageDialog("All fields are mandatory !!");
            }
        }
        else if(v == reset_btn)
        {
            resetInput();
        }
    }

    private void showMessageDialog(final String message)
    {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        final AlertDialog alertDialog  = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void resetInput() {
        admin_name_et.setText("");
        address_et.setText("");
    }

    @Override
    public void showNewAdminDetail(AdminDetails adminDetails) {
        Intent negativeActivity = new Intent(getApplicationContext(), ViewAdminDetailsActivity.class);
        negativeActivity.putExtra("details", adminDetails);
        startActivity(negativeActivity);
        finish();
    }

    @Override
    public void adminAdded(final AdminDetails adminDetails) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Admin record added successfully");

        alertDialogBuilder.setPositiveButton("Add More",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //finish
                    }
                });

        alertDialogBuilder.setNegativeButton("View Records",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showNewAdminDetail(adminDetails);

                    }
                });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}