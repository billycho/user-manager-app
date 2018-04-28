package com.qwerapps.usermanager.data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IT02106 on 22/04/2018.
 */

public class MemberDetails implements Serializable{
    private static final long serialVersionUID = -222864131214757024L;

    public static final String ID_FIELD = "member_id";
    public static final String ADMIN_ID_FIELD = "admin_id";

    @DatabaseField(generatedId = true, columnName =  ID_FIELD)
    public int memberId;

    @DatabaseField(columnName = "member_name")
    public String memberName;

    public String address;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public AdminDetails admin;

    @DatabaseField(columnName = "added_date")
    public Date addedDate;

    public MemberDetails()
    {

    }

    public MemberDetails(final String name, final String address, AdminDetails admin)
    {
        this.memberName = name;
        this.address = address;
        this.admin = admin;
    }
}
