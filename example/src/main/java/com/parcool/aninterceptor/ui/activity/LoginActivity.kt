package com.parcool.aninterceptor.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.parcool.an_interceptor.AnInterceptor
import com.parcool.aninterceptor.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            SPUtils.getInstance().put("login", true)
            finish()
            AnInterceptor.trigger()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.i("LoginActivity.onDestroy()")
    }
}