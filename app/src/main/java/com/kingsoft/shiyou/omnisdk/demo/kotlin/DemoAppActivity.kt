package com.kingsoft.shiyou.omnisdk.demo.kotlin

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.callback.ExitCallback
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.common.view.AppView
import kotlin.system.exitProcess

/**
 * Description: 应用主Activity
 *
 * @author: LuXing created on 2021/3/8 16:31
 *
 */
class DemoAppActivity : AppCompatActivity() {

    private lateinit var appView: AppView

    /**
     * CP对接方必须在游戏Activity的onCreate生命周期方法内调用如下SDK API接口
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApiManager.instance.initialize(this, ApiManager.Language.KOTLIN)
        appView = AppView(this)

        // SDK API接口(必须调用)
        OmniSDK.instance.onCreate(this, savedInstanceState)

        // CP自己的代码

    }

    /**
     * CP对接方必须在游戏Activity的onStart生命周期方法内调用如下SDK API接口
     */
    override fun onStart() {
        super.onStart()

        // SDK API(必须调用)
        OmniSDK.instance.onStart(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onRestart生命周期方法内调用如下SDK API接口
     */
    override fun onRestart() {
        super.onRestart()

        // SDK API(必须调用)
        OmniSDK.instance.onRestart(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onResume生命周期方法内调用如下SDK API接口
     */
    override fun onResume() {
        super.onResume()

        // SDK API(必须调用)
        OmniSDK.instance.onResume(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onPause生命周期方法内调用如下SDK API接口
     */
    override fun onPause() {
        super.onPause()

        // SDK API(必须调用)
        OmniSDK.instance.onPause(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onStop生命周期方法内调用如下SDK API接口
     */
    override fun onStop() {
        super.onStop()

        // SDK API(必须调用)
        OmniSDK.instance.onStop(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onDestroy生命周期方法内调用如下SDK API接口
     */
    override fun onDestroy() {
        super.onDestroy()

        // SDK API(必须调用)
        OmniSDK.instance.onDestroy(this)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity的onNewIntent生命周期方法内调用如下SDK API接口
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        // SDK API(必须调用)
        OmniSDK.instance.onNewIntent(this, intent)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onSaveInstanceState方法并在其中调用如下SDK API接口
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // SDK API(必须调用)
        OmniSDK.instance.onSaveInstanceState(this, outState)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onRestoreInstanceState方法并在其中调用如下SDK API接口
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // SDK API(必须调用)
        OmniSDK.instance.onRestoreInstanceState(this, savedInstanceState)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onConfigurationChanged方法并在其中调用如下SDK API接口
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // SDK API(必须调用)
        OmniSDK.instance.onConfigurationChanged(this, newConfig)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onActivityResult方法并在其中调用如下SDK API接口
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // SDK API(必须调用)
        OmniSDK.instance.onActivityResult(this, requestCode, resultCode, data)

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onRequestPermissionsResult方法并在其中调用如下SDK API接口
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // SDK API(必须调用)
        OmniSDK.instance.onRequestPermissionsResult(
            this,
            requestCode,
            permissions,
            grantResults
        )

        // CP自己的代码
    }

    /**
     * CP对接方必须在游戏Activity中重载onBackPressed方法并在其中调用如下SDK API接口
     */
    override fun onBackPressed() {
        // SDK API(必须调用)
        OmniSDK.instance.onBackPressed(this)

        // CP自己的代码

        super.onBackPressed()
    }

    /**
     * CP对接方必须在游戏Activity中重载onKeyDown方法并在其中调用如下SDK API接口
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 拦截返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            OmniSDK.instance.onExit(this, object : ExitCallback {
                override fun onExit() {
                    finish() // 杀掉UI
                    exitProcess(0) // 杀掉进程
                }
            })
            return true
        }

        // SDK API(必须调用)
        if (OmniSDK.instance.onKeyDown(this, keyCode, event)) {
            return true
        }

        // CP 自己的代码

        return super.onKeyDown(keyCode, event)
    }

}