package com.kingsoft.shiyou.omnisdk.demo.v3.common

import android.content.Context
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoFileUtil
import org.json.JSONObject

/**
 * Description:
 *
 * @author: LuXing created on 2022/1/18 17:31
 *
 */
class ConfigData private constructor() {

    companion object {
        private const val PROJECT_CONFIG_JSON__FILE_NAME = "shiyou/project_config.json"

        @JvmField
        val singletonInstance = ConfigData()
    }

    var projectConfigJsonData: String = ""
    private lateinit var dataJsonObject: JSONObject

    // Init by onStart
    var appId: String = ""
    var channelId: String = ""
    var sdkVersion: String = ""
    var channelName: String = ""
    var cpsName: String = ""

    fun initialize(context: Context) {
        dataJsonObject = JSONObject()
        kotlin.runCatching {
            projectConfigJsonData =
                DemoFileUtil.contentOfAssetFile(context, PROJECT_CONFIG_JSON__FILE_NAME)
            if (projectConfigJsonData.isNotBlank()) {
                dataJsonObject = JSONObject(projectConfigJsonData)
            }
        }
    }

    /** 当前是否为国内平台渠道 */
    fun isCnPlatform(): Boolean {
        val supportValue = dataJsonObject.optString("support", "")
        return supportValue.contains("cn", true) ||
                supportValue.contains("xg", true)
    }

    /** 当前是否为海外平台渠道 */
    fun isEnPlatform() = !(isCnPlatform())

}