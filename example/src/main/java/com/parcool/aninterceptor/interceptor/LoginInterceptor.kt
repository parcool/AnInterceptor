package com.parcool.aninterceptor.interceptor

import android.app.Activity
import android.content.Intent
import com.blankj.utilcode.util.SPUtils
import com.parcool.aninterceptor.ui.activity.LoginActivity
import com.parcool.internal.HandlerInterceptor
import java.lang.ref.WeakReference

class LoginInterceptor(activity: Activity) : HandlerInterceptor {

    var activityRef: WeakReference<Activity> = WeakReference(activity)

    override fun preHandle(): Boolean {
        return SPUtils.getInstance().getBoolean("login", false)
    }

    override fun handle() {
        activityRef.get()?.startActivity(Intent(activityRef.get(), LoginActivity::class.java))
    }
}