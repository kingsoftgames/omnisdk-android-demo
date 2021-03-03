package com.kingsoft.shiyou.omnisdk.demo.common

import android.content.Context
import android.util.Log
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoFileUtil
import org.json.JSONObject

/**
 * Description: Demo测试数据管理类
 *
 * @author: LuXing created on 2021/3/18 15:08
 *
 */
class TestData private constructor() {

    companion object {
        const val TEST_DATA_FILE_NAME = "omni_sdk_test_data.json"

        @JvmStatic
        fun instance() = Holder.singletonInstance
    }

    private object Holder {
        val singletonInstance = TestData()
    }

    private val tag = "SDK TestData #"
    private var dataJsonObject: JSONObject = JSONObject()

    fun initialize(context: Context) {
        Log.e(tag, "Demo(packageName) => ${context.packageName}")
        try {
            val jsonData = loadTestData(context)
            if (jsonData.isNotBlank()) {
                dataJsonObject = JSONObject(jsonData)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            dataJsonObject = JSONObject()
        }
    }

    fun isTestDataAvailable(demoViewName: String) = dataJsonObject.has(demoViewName)

    fun isTestDataItemAvailable(demoViewName: String, propertyName: String): Boolean {
        if (dataJsonObject.has(demoViewName)) {
            val jsonObject = dataJsonObject.optJSONObject(demoViewName)
            if (jsonObject != null && jsonObject.has(propertyName)) {
                return jsonObject.optString(propertyName, "").isNotBlank()
            }
        }
        return false
    }

    fun getValue(
        demoViewName: String?,
        propertyName: String?
    ): String {
        if (propertyName.isNullOrBlank()) {
            return ""
        }
        if (demoViewName.isNullOrEmpty()) {
            return dataJsonObject.optString(propertyName, "")
        }
        if (dataJsonObject.has(demoViewName)) {
            val functionDataJsonObject = dataJsonObject.optJSONObject(demoViewName)
            if (functionDataJsonObject != null && functionDataJsonObject.has(propertyName)) {
                return functionDataJsonObject.optString(propertyName, "")
            }
        }
        return ""
    }

    /**
     * 加载测试配置文件
     */
    private fun loadTestData(context: Context) = try {
        /*
        * 优先去设备内置存储卡里去读取测试数据，请将测试数据文件放置:
        * {设备内置存储卡根目录}/Android/data/{当前测试Demo App的包名}/files/omni_sdk_test_data.json
        */
        var testJsonData = DemoFileUtil.contentOfExternalFile(context, TEST_DATA_FILE_NAME)
        if (testJsonData.isNotBlank()) {
            Log.i(tag, "testJsonData(external file) : $testJsonData")
        } else {
            // 尝试去assets目录读取测试数据
            testJsonData = DemoFileUtil.contentOfAssetFile(context, TEST_DATA_FILE_NAME)
            Log.i(tag, "testJsonData(assets file) : $testJsonData")
        }
        testJsonData
    } catch (e: Exception) {
        e.printStackTrace()
        Log.i(tag, "exception = $e")
        ""
    }

}