package com.qwerapps.usermanager.addMember;

import com.qwerapps.usermanager.data.AdminDetails;
import com.qwerapps.usermanager.data.MemberDetails;

import java.util.List;

/**
 * Created by IT02106 on 28/04/2018.
 */

public interface MemberAddContract {
    interface View
    {
        void resetInput();

        void showNewMemberDetail();

        void memberAdded();
    }

    interface Presenter
    {
        void addMember(MemberDetails memberDetail);

        List<AdminDetails> getAdminList();
    }
}
