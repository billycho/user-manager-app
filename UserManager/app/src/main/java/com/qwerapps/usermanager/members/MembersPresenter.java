package com.qwerapps.usermanager.members;

import com.j256.ormlite.dao.Dao;
import com.qwerapps.usermanager.admins.AdminsContract;
import com.qwerapps.usermanager.data.AdminDetails;
import com.qwerapps.usermanager.data.DatabaseHelper;
import com.qwerapps.usermanager.data.MemberDetails;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by IT02106 on 28/04/2018.
 */

public class MembersPresenter implements MembersContract.Presenter{

    private DatabaseHelper databaseHelper = null;
    private Dao<MemberDetails,Integer> memberDao;

    private final MembersContract.View membersView;

    private List<MemberDetails> memberList = null;

    public MembersPresenter(DatabaseHelper databaseHelper,MembersContract.View membersView)
    {
        this.databaseHelper = databaseHelper;
        this.membersView = membersView;

        try
        {
            memberDao = this.databaseHelper.getMemberDao();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void getMembers() {
        try
        {
            memberList = memberDao.queryForAll();
            membersView.showMembers(memberList);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMember(int selectedRecordPosition) {
        try
        {
            memberDao.delete(memberList.get(selectedRecordPosition));
            memberList.remove(selectedRecordPosition);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void openMemberDetail(int selectedRecordPosition) {
        membersView.showMemberDetail(memberList.get(selectedRecordPosition));
    }

    @Override
    public boolean isListEmpty() {
        return false;
    }
}
