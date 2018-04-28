package com.qwerapps.usermanager.admins;

import com.qwerapps.usermanager.data.AdminDetails;

import java.util.List;

/**
 * Created by IT02106 on 28/04/2018.
 */

public interface AdminsContract {
    interface View
    {
        void showAdmins(List<AdminDetails> admins);

        void showDeleteConfirmation();

        void showAdminDetail(AdminDetails adminDetail);
    }

    interface Presenter
    {
        void getAdmins();

        void deleteAdmin(int selectedRecordPosition);

        void openAdminDetail(int selectedRecordPosition);

        boolean isListEmpty();
    }
}
