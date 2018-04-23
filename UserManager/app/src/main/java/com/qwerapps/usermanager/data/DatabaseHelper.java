package com.qwerapps.usermanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.qwerapps.usermanager.R;

import java.lang.reflect.Member;
import java.sql.SQLException;

/**
 * Created by IT02106 on 23/04/2018.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "member.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<MemberDetails, Integer> memberDao;
    private Dao<AdminDetails, Integer> adminDao;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource)
    {
        try
        {
            TableUtils.createTable(connectionSource, AdminDetails.class);
            TableUtils.createTable(connectionSource, MemberDetails.class);
        }
        catch(SQLException e)
        {
            Log.e(DatabaseHelper.class.getName(), "Unable to create databases", e);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer)
    {
        try
        {
            TableUtils.dropTable(connectionSource, AdminDetails.class, true);
            TableUtils.dropTable(connectionSource, MemberDetails.class, true);
        }
        catch(SQLException e)
        {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "+ newVer, e);
        }
    }

    public Dao<AdminDetails, Integer> getAdminDao() throws SQLException {
        if(adminDao == null)
        {
            adminDao = getDao(AdminDetails.class);
        }
        return adminDao;
    }

    public Dao<MemberDetails, Integer> getMemberDao() throws  SQLException
    {
        if(memberDao == null)
        {
            memberDao = getDao(Member.class);
        }

        return memberDao;
    }
}
