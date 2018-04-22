package com.qwerapps.usermanager.data;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by IT02106 on 22/04/2018.
 */

public class DatabaseConfigUtil extends OrmLiteConfigUtil {
    public static void main(String[] args) throws SQLException, IOException {

        // Provide the name of .txt file which you have already created and kept in res/raw directory
        writeConfigFile("ormlite_config.txt");
    }
}
