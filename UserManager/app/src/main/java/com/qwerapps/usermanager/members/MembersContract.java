package com.qwerapps.usermanager.members;

import com.qwerapps.usermanager.data.MemberDetails;

import java.util.List;

/**
 * Created by IT02106 on 28/04/2018.
 */

public interface MembersContract {
    interface View
    {
        void showMembers(List<MemberDetails> members);

        void showDeleteConfirmation();

        void showMemberDetail(MemberDetails memberDetails);
    }

    interface Presenter
    {
        void getMembers();

        void deleteMember(int selectedRecordPosition);

        void openMemberDetail(int selectedRecordPosition);

        boolean isListEmpty();
    }
}
