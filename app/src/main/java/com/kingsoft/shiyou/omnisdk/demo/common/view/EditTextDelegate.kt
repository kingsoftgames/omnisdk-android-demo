package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.widget.EditText
import com.kingsoft.shiyou.omnisdk.demo.common.TestData
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoResourceIdUtil
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Description: EditText实例加载并设置初始化测试数据代理
 *
 * @author: LuXing created on 2021/3/18 14:46
 *
 */
internal class EditTextDelegate<out T : EditText> : ReadOnlyProperty<DemoView, EditText> {

    companion object {
        @JvmStatic
        fun initData(thisRef: DemoView, property: KProperty<*>) =
            EditTextDelegate<EditText>().getValue(thisRef, property)
    }

    private var targetView: T? = null

    override fun getValue(thisRef: DemoView, property: KProperty<*>): T {
        val className = thisRef::class.simpleName!!
        val propertyName = property.name
        if (targetView == null) {
            targetView = try {
                // 读取解析EditText的ID值
                val vid = DemoResourceIdUtil.getId(
                    thisRef.baseContext,
                    className.camelToUnderscore() + "_" + propertyName.camelToUnderscore()
                )
                (thisRef.findViewById(vid) as T).also {
                    /*
                     * 设置输入框EditText的初始测试数据，解析加载的Json格式的测试数据
                     * #当前DemoView的Class名称作为其对应的测试数据JsonObject的key值
                     * #当前EditText的propertyName属性名称作为其初始数据项的key值
                     */
                    it.setText(TestData.singletonInstance.getValue(className, propertyName))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        return targetView!!
    }

    private fun String?.camelToUnderscore() =
        if (this.isNullOrBlank()) {
            ""
        } else {
            val sb = StringBuilder(this.length)
            this.forEachIndexed { index, ch ->
                if (Character.isUpperCase(ch) && index > 0) {
                    sb.append("_")
                }
                sb.append(Character.toLowerCase(ch))
            }
            sb.toString()
        }

}