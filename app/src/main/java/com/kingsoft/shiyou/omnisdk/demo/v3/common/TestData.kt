package com.kingsoft.shiyou.omnisdk.demo.v3.common

import android.content.Context
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoFileUtil
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoLogger
import org.json.JSONObject

/**
 * Description: Demo测试数据管理类
 *
 * @author: LuXing created on 2021/3/18 15:08
 *
 */
class TestData private constructor() {

    companion object {
        // 测试数据文件名称
        private const val TEST_DATA_FILE_NAME = "omni_sdk_test_data.json"

        @JvmField
        val singletonInstance = TestData()
    }

    private val tag = "TestData# "
    private lateinit var dataJsonObject: JSONObject

    fun initialize(context: Context) {
        dataJsonObject = JSONObject()
        kotlin.runCatching {
            val jsonData = loadTestData(context)
            if (jsonData.isNotBlank()) {
                dataJsonObject = JSONObject(jsonData)
                DemoLogger.d(tag, "loading test data successfully")
            }
        }
    }

    fun testDataAvailableForDemoView(demoViewName: String) = dataJsonObject.has(demoViewName)

    /** 获取测试数据 */
    fun getValue(demoViewName: String?, propertyName: String?): String {
        try {
            if (propertyName.isNullOrBlank()) {
                return ""
            }
            if (demoViewName.isNullOrBlank()) {
                return dataJsonObject.optString(propertyName, "")
            }
            // 传入的`demoViewName`和`propertyName`参数值都不为空
            if (dataJsonObject.has(demoViewName)) {
                val functionDataJsonObject = dataJsonObject.optJSONObject(demoViewName)
                if (functionDataJsonObject != null && functionDataJsonObject.has(propertyName)) {
                    return functionDataJsonObject.optString(propertyName, "")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /** 加载测试Json数据 */
    private fun loadTestData(context: Context): String {
        // 优先去设备内置存储卡里去读取测试数据，请将测试数据文件放置:
        // {设备内置存储卡根目录}/Android/data/{当前测试Demo App的包名}/files/omni_sdk_test_data.json
        var testJsonData = DemoFileUtil.contentOfExternalFile(context, TEST_DATA_FILE_NAME)
        if (testJsonData.isBlank()) {
            // 尝试去assets目录读取测试数据
            testJsonData = DemoFileUtil.contentOfAssetFile(context, TEST_DATA_FILE_NAME)
        } else {
            DemoLogger.d(tag, "### using test data from the external file ###")
        }
        return testJsonData
    }

}