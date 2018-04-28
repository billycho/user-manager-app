package com.qwerapps.usermanager.addAdmin;

import com.j256.ormlite.dao.Dao;
import com.qwerapps.usermanager.admins.AdminsContract;
import com.qwerapps.usermanager.data.AdminDetails;
import com.qwerapps.usermanager.data.DatabaseHelper;

import java.sql.SQLException;

/**
 * Created by IT02106 on 28/04/2018.
 */

public class AdminAddPresenter implements AdminAddContract.Presenter {
    private DatabaseHelper databaseHelper = null;
    private Dao<AdminDetails,Integer> adminDao;

    private final AdminAddContract.View adminsView;

    public AdminAddPresenter(DatabaseHelper databaseHelper, AdminAddContract.View adminsView)
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
    public void addAdmin(AdminDetails adminDetail) {
                try
                {
                    adminDao.create(adminDetail);
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
    }
}
