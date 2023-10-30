package com.kingsoft.shiyou.omnisdk.demo.v3.common

import android.text.Html
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.kingsoft.shiyou.omnisdk.basic.printDebugStackTrace
import java.io.File
import java.io.FileInputStream

internal fun String.html() =
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }

fun <T> fromJson(file: File, classOfT: Class<T>): T? {
    try {
        return Gson().fromJson(FileInputStream(file).reader(), classOfT)
    } catch (e: JsonParseException) {
        printDebugStackTrace { e }
    } catch (e: JsonSyntaxException) {
        printDebugStackTrace { e }
    } catch (e: Exception) {
        printDebugStackTrace { e }
    }
    return null
}
