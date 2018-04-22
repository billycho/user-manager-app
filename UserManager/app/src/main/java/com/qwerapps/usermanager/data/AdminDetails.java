package com.qwerapps.usermanager.data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by IT02106 on 22/04/2018.
 */

public class AdminDetails implements Serializable
{
    private static final long serialVersionUID = -222864131214757024L;

    @DatabaseField(generatedId = true, columnName = "admin_id")
    public int adminId;

    @DatabaseField(columnName = "admin_name")
    public String adminName;

    public String address;

    public AdminDetails()
    {

    }

    public AdminDetails(final String name, final String address)
    {
        this.adminName = name;
        this.address = address;
    }
}
