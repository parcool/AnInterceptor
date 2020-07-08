package com.parcool.an_interceptor.action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parcool.an_interceptor.AnConstant;
import com.parcool.internal.Action;

import java.lang.ref.WeakReference;

public class StartActivityForResultAction implements Action {

    private WeakReference<Activity> activityRef;
    private Intent intent;
    private int requestCode;
    private Bundle options;

    public StartActivityForResultAction(Activity sourceActivity, Intent intent, int requestCode, Bundle options) {
        activityRef = new WeakReference<>(sourceActivity);
        this.intent = intent;
        this.requestCode = requestCode;
        this.options = options;
        if (this.options == null) {
            this.options = new Bundle();
        }
        this.options.putBoolean(AnConstant.REQUIRE_BREAK, true);
    }

    @Override
    public void call() {
        if (activityRef.get() != null) {
            activityRef.get().startActivityForResult(intent, requestCode, options);
        }
    }
}
