package com.qwerapps.usermanager.viewAdminDetails;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.qwerapps.usermanager.data.AdminDetails;
import com.qwerapps.usermanager.data.DatabaseHelper;
import com.qwerapps.usermanager.data.MemberDetails;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IT02106 on 28/04/2018.
 */

public class ViewAdminDetailsPresenter implements ViewAdminDetailsContract.Presenter {
    private DatabaseHelper databaseHelper;
    private Dao<AdminDetails, Integer> adminDao;
    private Dao<MemberDetails, Integer> memberDao;

    private ViewAdminDetailsContract.View adminDetailsView;
    public ViewAdminDetailsPresenter(DatabaseHelper databaseHelper, ViewAdminDetailsContract.View adminDetailsView)
    {
        this.databaseHelper = databaseHelper;
        this.adminDetailsView = adminDetailsView;

        try {
            memberDao = databaseHelper.getMemberDao();
            adminDao = databaseHelper.getAdminDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public String getMembers(Integer adminId) {
        final List<String> memberName = new ArrayList<String>();

        try
        {

            final QueryBuilder<MemberDetails, Integer> queryBuilder = memberDao.queryBuilder();

            queryBuilder.where().eq(MemberDetails.ADMIN_ID_FIELD, adminId);

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

        return memberName.toString();
    }
}
