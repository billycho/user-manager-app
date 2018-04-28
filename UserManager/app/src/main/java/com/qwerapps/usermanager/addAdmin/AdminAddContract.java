package com.qwerapps.usermanager.addAdmin;

import com.qwerapps.usermanager.data.AdminDetails;

/**
 * Created by IT02106 on 28/04/2018.
 */

public interface AdminAddContract {
    interface View
    {
        void resetInput();

        void showNewAdminDetail(AdminDetails adminDetails);

        void adminAdded(AdminDetails adminDetails);
    }

    interface Presenter
    {
        void addAdmin(AdminDetails adminDetail);
    }
}
