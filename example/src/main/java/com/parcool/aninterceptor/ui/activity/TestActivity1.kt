package com.parcool.aninterceptor.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.parcool.aninterceptor.R
import com.parcool.aninterceptor.interceptor.LoginInterceptor
import com.parcool.aninterceptor.interceptor.UserInfoInterceptor
import com.parcool.annotation.Interceptor
import kotlinx.android.synthetic.main.activity_test1.*


import org.json.JSONObject

@Interceptor(interceptors = [LoginInterceptor::class, UserInfoInterceptor::class])
class TestActivity1 : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test1)

        val jsonObject = JSONObject(SPUtils.getInstance().getString("user_info"))
        tv_username.text = jsonObject.getString("name")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.i("TestActivity1.onDestroy()")
    }
}