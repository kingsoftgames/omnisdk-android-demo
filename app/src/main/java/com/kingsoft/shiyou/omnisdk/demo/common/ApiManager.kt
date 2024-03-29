package com.kingsoft.shiyou.omnisdk.demo.common

import android.app.Activity
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.*
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

    private var language = Language.JAVA

    fun initialize(apiLanguage: Language) {
        language = apiLanguage
    }

    fun getExitApi(appActivity: Activity, callback: IExitCallback): IExitApi =
        getApi(appActivity, "ExitApi", callback)

    fun getAccountApi(appActivity: Activity, callback: IAccountCallback): IAccountApi =
        getApi(appActivity, "AccountApi", callback)

    fun getPayApi(appActivity: Activity, callback: IPayCallback): IPayApi =
        getApi(appActivity, "PayApi", callback)

    fun getSocialApi(appActivity: Activity, callback: ISocialCallback): ISocialApi =
        getApi(appActivity, "SocialApi", callback)

    fun getAdApi(appActivity: Activity, callback: IAdCallback): IAdApi =
        getApi(appActivity, "AdApi", callback)

    fun getDataMonitorApi(appActivity: Activity, callback: IDataMonitorCallback): IDataMonitorApi =
        getApi(appActivity, "DataMonitorApi", callback)

    fun getOtherApi(appActivity: Activity, callback: IOtherCallback): IOtherApi =
        getApi(appActivity, "OtherApi", callback)

    fun getPermissionApi(appActivity: Activity, callback: IPermissionCallback): IPermissionApi =
        getApi(appActivity, "PermissionApi", callback)

    /**
     * 反射获取Api实例
     * @param className Api类名
     * @param callback Api要求传入的数据回调实例对象
     * @return Api实例
     */
    private inline fun <reified C, reified T> getApi(
        appActivity: Activity,
        className: String,
        callback: C
    ): T {
        val fullClassName =
            "com.kingsoft.shiyou.omnisdk.demo.${language.name.lowercase(Locale.US)}.${className}"
        val constructor =
            Class.forName(fullClassName).getConstructor(Activity::class.java, C::class.java)
        return constructor.newInstance(appActivity, callback) as T
    }

}