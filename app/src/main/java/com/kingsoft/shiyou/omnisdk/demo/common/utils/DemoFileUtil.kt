package com.kingsoft.shiyou.omnisdk.demo.common.utils

import android.content.Context
import java.io.File

/**
 * Description: 文件读取方法类
 *
 * @author: LuXing created on 2021/4/8 16:11
 *
 */
object DemoFileUtil {

    fun contentOfAssetFile(context: Context, fileName: String) = try {
        var content = ""
        val inputStream = context.assets.open(fileName)
        content = inputStream.readBytes().toString(Charsets.UTF_8)
        inputStream.close()
        content
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }

    fun contentOfExternalFile(context: Context, fileName: String) = try {
        var content = ""
        val file = File(context.getExternalFilesDir(null), fileName)
        DemoLogger.d("try to get the content of ${file.absolutePath}")
        if (file.exists()) {
            content = file.readText(Charsets.UTF_8)
        }
        content
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }

}