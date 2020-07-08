package com.parcool.aninterceptor.interceptor

import android.app.Activity
import android.app.ProgressDialog
import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.parcool.an_interceptor.AnInterceptor
import com.parcool.internal.HandlerInterceptor
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class UserInfoInterceptor(activity: Activity) : HandlerInterceptor {

    private var activityRef: WeakReference<Activity> = WeakReference(activity)

    override fun preHandle(): Boolean {
        return !TextUtils.isEmpty(SPUtils.getInstance().getString("user_info"))
    }

    override fun handle() {
        MainScope().launch {
            var dialog: ProgressDialog? = null
            activityRef.get()?.let {
                dialog = ProgressDialog(it)
                dialog?.setMessage("loading...")
                dialog?.show()
            }
            delay(1000)
            SPUtils.getInstance().put("user_info", "{\"name\":\"parcool\"}")
            dialog?.dismiss()
            AnInterceptor.trigger()
        }
    }
}