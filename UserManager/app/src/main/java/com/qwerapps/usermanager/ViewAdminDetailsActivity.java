package com.qwerapps.usermanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
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

public class ViewAdminDetailsActivity extends AppCompatActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_admin_details);

        ButterKnife.bind(this);

        close_btn.setOnClickListener(this);

        final AdminDetails aDetails = (AdminDetails) getIntent().getExtras().getSerializable("details");

        admin_name_et.setText(aDetails.adminName);
        address_et.setText(aDetails.address);

        final List<String> memberName = new ArrayList<String>();

        try
        {
            memberDao = getHelper().getMemberDao();

            final QueryBuilder<MemberDetails, Integer> queryBuilder = memberDao.queryBuilder();

            queryBuilder.where().eq(MemberDetails.ADMIN_ID_FIELD, aDetails.adminId);

            final PreparedQuery<MemberDetails> preparedQuery = queryBuilder.prepare();

            final Iterator<MemberDetails> membersIt = memberDao.query(preparedQuery).iterator();

            while(membersIt.hasNext())
            {
                final MemberDetails mDetails = membersIt.next();
                memberName.add(mDetails.memberName);
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        members_et.setText(memberName.toString());
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
}
