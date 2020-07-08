package com.parcool.aninterceptor.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.parcool.an_interceptor.AnConstant
import com.parcool.an_interceptor.AnInterceptor

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        intent?.let {
            if (options != null && options.getBoolean(AnConstant.REQUIRE_BREAK, false)) {
                super.startActivityForResult(intent, requestCode, options)
                return
            }
            AnInterceptor.startActivityForResult(this, intent, requestCode, options)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}