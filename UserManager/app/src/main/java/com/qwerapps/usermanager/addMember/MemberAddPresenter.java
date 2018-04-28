package com.qwerapps.usermanager.addMember;

import com.j256.ormlite.dao.Dao;
import com.qwerapps.usermanager.data.AdminDetails;
import com.qwerapps.usermanager.data.DatabaseHelper;
import com.qwerapps.usermanager.data.MemberDetails;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by IT02106 on 28/04/2018.
 */

public class MemberAddPresenter implements MemberAddContract.Presenter {

    private DatabaseHelper databaseHelper;

    private MemberAddContract.View membersView;

    private Dao<MemberDetails,Integer> memberDao;
    private Dao<AdminDetails,Integer> adminDao;

    private List<AdminDetails> adminList = null;

    public MemberAddPresenter(DatabaseHelper databaseHelper, MemberAddContract.View membersView)
    {
        this.databaseHelper = databaseHelper;
        this.membersView = membersView;
        try
        {
            memberDao = this.databaseHelper.getMemberDao();
            adminDao = this.databaseHelper.getAdminDao();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void addMember(MemberDetails memberDetail) {
        try {
            memberDao.create(memberDetail);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AdminDetails> getAdminList() {

        try {
            adminList = adminDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adminList;
    }
}
