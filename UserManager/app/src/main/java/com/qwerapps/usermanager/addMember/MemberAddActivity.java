package com.qwerapps.usermanager.addMember;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.qwerapps.usermanager.R;
import com.qwerapps.usermanager.data.AdminDetails;
import com.qwerapps.usermanager.data.DatabaseHelper;
import com.qwerapps.usermanager.data.MemberDetails;
import com.qwerapps.usermanager.members.ViewMemberRecordActivity;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberAddActivity extends AppCompatActivity implements View.OnClickListener, MemberAddContract.View{

    private DatabaseHelper databaseHelper = null;

    @BindView(R.id.member_name_et)
    EditText member_name_et;
    @BindView(R.id.address_et)
    EditText address_et;
    @BindView(R.id.reset_btn)
    Button reset_btn;
    @BindView(R.id.submit_btn)
    Button submit_btn;
    @BindView(R.id.admin_sp)
    Spinner admin_sp;

    private List<AdminDetails> adminList;

    private MemberAddPresenter memberAddPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_add);

        ButterKnife.bind(this);

        memberAddPresenter = new MemberAddPresenter(getHelper(), this);

        admin_sp.setAdapter(new CustomAdapter(this,android.R.layout.simple_spinner_item, android.R.layout.simple_spinner_dropdown_item, memberAddPresenter.getAdminList() ));

        reset_btn.setOnClickListener(this);
        submit_btn.setOnClickListener(this);


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
            if(memberAddPresenter.getAdminList().size() > 0)
            {
                if(member_name_et.getText().toString().trim().length() > 0 &&
                        address_et.getText().toString().trim().length() >0)
                {
                    final MemberDetails memDetails = new MemberDetails();
                    memDetails.memberName = member_name_et.getText().toString();
                    memDetails.address = address_et.getText().toString();
                    memDetails.addedDate = new Date();

                    memDetails.admin = (AdminDetails) admin_sp.getSelectedItem();

                    memberAddPresenter.addMember(memDetails);
                    resetInput();
                    memberAdded();
                }
                else
                {
                    showMessageDialog("All fields are mandatory");
                }
            }
            else
            {
                showMessageDialog("Please, add Admin Details first !!");
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
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void resetInput() {
        member_name_et.setText("");
        address_et.setText("");
    }

    @Override
    public void showNewMemberDetail() {
        final Intent negativeActivity = new Intent(getApplicationContext(), ViewMemberRecordActivity.class);
        startActivity(negativeActivity);
        finish();
    }

    @Override
    public void memberAdded() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage("Member record added successfully !!");

        alertDialogBuilder.setPositiveButton("Add More",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        alertDialogBuilder.setNegativeButton("View Records",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showNewMemberDetail();
                    }
                });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @SuppressWarnings("rawtypes")
    class CustomAdapter extends ArrayAdapter<String>
    {
        LayoutInflater inflater;

        List objects;

        @SuppressWarnings("unchecked")
        public CustomAdapter(Context context, int resource, int drowDownViewResource, List objects)
        {
            super(context, resource, objects);
            this.setDropDownViewResource(drowDownViewResource);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            this.objects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent)
        {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent)
        {
            final View row = inflater.inflate(android.R.layout.simple_spinner_item, parent,false);

            final TextView label = (TextView) row.findViewById(android.R.id.text1);
            final AdminDetails admin = (AdminDetails) this.objects.get(position);
            label.setText(admin.adminName);

            return row;


        }
    }
}
