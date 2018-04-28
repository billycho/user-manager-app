package com.qwerapps.usermanager.viewAdminDetails;

/**
 * Created by IT02106 on 28/04/2018.
 */

public interface ViewAdminDetailsContract {
    interface View
    {
        void showMembers();
    }

    interface Presenter
    {
        String getMembers(Integer adminId);
    }
}
