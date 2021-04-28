package com.kingsoft.shiyou.omnisdk.demo.common

import android.app.Activity
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.*
import java.lang.ref.WeakReference
import java.util.*

/**
 * Description: SDK API实例化管理类
 *
 * @author: LuXing created on 2021/3/22 16:13
 *
 */
class ApiManager private constructor() {

    enum class Language { KOTLIN, JAVA }

    companion object {
        @JvmStatic
        val instance = Holder.singletonInstance
    }

    private object Holder {
        val singletonInstance = ApiManager()
    }

    private lateinit var demoActivity: WeakReference<Activity>
    private lateinit var language: Language

    fun initialize(activity: Activity, apiLanguage: Language) {
        demoActivity = WeakReference<Activity>(activity)
        language = apiLanguage
    }

    fun getAccountApi(callback: IAccountCallback): IAccountApi =
        getApi("AccountApi", callback)

    fun getPayApi(callback: IPayCallback): IPayApi =
        getApi("PayApi", callback)

    fun getSocialApi(callback: ISocialCallback): ISocialApi =
        getApi("SocialApi", callback)

    fun getAdApi(callback: IAdCallback): IAdApi =
        getApi("AdApi", callback)

    fun getDataMonitorApi(callback: IDataMonitorCallback): IDataMonitorApi =
        getApi("DataMonitorApi", callback)

    fun getOtherApi(callback: IOtherCallback): IOtherApi =
        getApi("OtherApi", callback)

    /**
     * 反射获取Api实例
     * @param className Api类名
     * @param callback Api要求传入的数据回调实例对象
     * @return Api实例
     */
    private inline fun <reified C, reified T> getApi(
        className: String,
        callback: C
    ): T {
        val fullClassName =
            "com.kingsoft.shiyou.omnisdk.demo.${language.name.toLowerCase(Locale.US)}.${className}"
        val constructor =
            Class.forName(fullClassName)
                .getConstructor(Activity::class.java, C::class.java)
        return constructor.newInstance(demoActivity.get(), callback) as T
    }

}