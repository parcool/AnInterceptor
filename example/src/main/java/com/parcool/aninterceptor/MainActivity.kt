package com.parcool.aninterceptor

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.SPUtils
import com.parcool.aninterceptor.ui.activity.BaseActivity
import com.parcool.aninterceptor.ui.activity.NeeLogonActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_go.setOnClickListener {
            startActivity(Intent(this, NeeLogonActivity::class.java))
        }
        btn_clear_login.setOnClickListener{
            SPUtils.getInstance().remove("login")
        }
        btn_clear_user_info.setOnClickListener {
            SPUtils.getInstance().remove("user_info")
        }
    }
}