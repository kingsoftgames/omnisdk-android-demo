package com.kingsoft.shiyou.omnisdk.demo.kotlin

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.demo.R

class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        OmniSDK.instance.onCreate(this, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        OmniSDK.instance.onStart(this)
    }

    override fun onRestart() {
        super.onRestart()
        OmniSDK.instance.onRestart(this)
    }

    override fun onResume() {
        super.onResume()
        OmniSDK.instance.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        OmniSDK.instance.onPause(this)
    }

    override fun onStop() {
        super.onStop()
        OmniSDK.instance.onStop(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        OmniSDK.instance.onDestroy(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        OmniSDK.instance.onNewIntent(this, intent)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (OmniSDK.instance.onKeyDown(this, keyCode, event)) {
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        OmniSDK.instance.onConfigurationChanged(this, newConfig)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        OmniSDK.instance.onSaveInstanceState(this, outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        OmniSDK.instance.onRestoreInstanceState(this, savedInstanceState)
    }

}