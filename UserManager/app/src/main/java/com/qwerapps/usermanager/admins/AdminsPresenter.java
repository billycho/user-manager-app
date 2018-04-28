package com.qwerapps.usermanager.admins;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.qwerapps.usermanager.data.AdminDetails;
import com.qwerapps.usermanager.data.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by IT02106 on 28/04/2018.
 */

public class AdminsPresenter implements  AdminsContract.Presenter {

    private DatabaseHelper databaseHelper = null;
    private Dao<AdminDetails,Integer> adminDao;

    private final AdminsContract.View adminsView;

    private List<AdminDetails> adminList = null;

    public AdminsPresenter(DatabaseHelper databaseHelper, AdminsContract.View adminsView)
    {
        this.databaseHelper = databaseHelper;
        this.adminsView = adminsView;

        try
        {
            adminDao = this.databaseHelper.getAdminDao();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void getAdmins()
    {

        try
        {
            adminList = adminDao.queryForAll();
            adminsView.showAdmins(adminList);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAdmin(int selectedRecordPosition)
    {
        try
        {
            adminDao.delete(adminList.get(selectedRecordPosition));
            adminList.remove(selectedRecordPosition);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void openAdminDetail(int selectedRecordPosition)
    {
        adminsView.showAdminDetail(adminList.get(selectedRecordPosition));
    }

    @Override
    public boolean isListEmpty()
    {
        if(adminList.size() > 0 || adminList == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


}
