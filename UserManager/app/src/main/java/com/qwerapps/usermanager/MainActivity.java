package com.qwerapps.usermanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.qwerapps.usermanager.addAdmin.AdminAddActivity;
import com.qwerapps.usermanager.addMember.MemberAddActivity;
import com.qwerapps.usermanager.admins.ViewAdminRecordActivity;
import com.qwerapps.usermanager.members.ViewMemberRecordActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.add_member_btn)
    Button add_member_btn;

    @BindView(R.id.add_admin_btn)
    Button add_admin_btn;

    @BindView(R.id.view_member_btn)
    Button view_btn;

    @BindView(R.id.view_admin_btn)
    Button view_admin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        add_admin_btn.setOnClickListener(this);
        add_member_btn.setOnClickListener(this);
        view_btn.setOnClickListener(this);
        view_admin_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Log.v("abc", "" +v);
        if(v == add_member_btn)
        {
            startActivity(new Intent(this, MemberAddActivity.class));
        }
        else if(v == add_admin_btn)
        {
            startActivity(new Intent(this, AdminAddActivity.class));
        }
        else if(v == view_btn)
        {
            startActivity(new Intent(this, ViewMemberRecordActivity.class));
        }
        else if(v == view_admin_btn)
        {
            startActivity(new Intent(this, ViewAdminRecordActivity.class));
        }

    }

}
