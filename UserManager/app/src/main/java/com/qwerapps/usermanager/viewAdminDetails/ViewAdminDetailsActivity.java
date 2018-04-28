package com.qwerapps.usermanager.viewAdminDetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.qwerapps.usermanager.R;
import com.qwerapps.usermanager.addMember.MemberAddContract;
import com.qwerapps.usermanager.data.AdminDetails;
import com.qwerapps.usermanager.data.DatabaseHelper;
import com.qwerapps.usermanager.data.MemberDetails;

import java.lang.reflect.Member;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewAdminDetailsActivity extends AppCompatActivity implements View.OnClickListener, ViewAdminDetailsContract.View {

    private DatabaseHelper databaseHelper = null;

    @BindView(R.id.admin_name_et)
    TextView admin_name_et;

    @BindView(R.id.address_et)
    TextView address_et;

    @BindView(R.id.members_et)
    TextView members_et;

    @BindView(R.id.close_btn)
    Button close_btn;

    private Dao<MemberDetails, Integer> memberDao;

    private ViewAdminDetailsContract.Presenter adminPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_admin_details);

        ButterKnife.bind(this);

        close_btn.setOnClickListener(this);

        adminPresenter = new ViewAdminDetailsPresenter(getHelper(), this);
        final AdminDetails aDetails = (AdminDetails) getIntent().getExtras().getSerializable("details");


        admin_name_et.setText(aDetails.adminName);
        address_et.setText(aDetails.address);

        members_et.setText(adminPresenter.getMembers(aDetails.adminId));
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
            databaseHelper  = null;
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v == close_btn)
        {
            finish();
        }
    }

    @Override
    public void showMembers() {

    }
}
