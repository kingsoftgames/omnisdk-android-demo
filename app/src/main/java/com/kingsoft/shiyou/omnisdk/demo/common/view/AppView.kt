package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.entity.RoleInfo
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.TestData
import com.kingsoft.shiyou.omnisdk.demo.common.html
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoDialogUtil
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoFileUtil
import java.util.*

/**
 * Description: Demo App 界面UI
 *
 * @author: LuXing created on 2021/3/22 11:28
 *
 */
@SuppressLint("SetTextI18n")
class AppView(val demoActivity: AppCompatActivity) {

    data class FunctionViewItem(var id: Int, var view: DemoView?)

    /**
     * 功能Demo集合
     */
    private val functionViewItems = mutableListOf<FunctionViewItem>()

    /**
     * 用户账号信息
     */
    private var userMap: Map<String, Any> = mapOf()

    /**
     * 用户角色账号数据
     */
    var gameRole: RoleInfo = RoleInfo()

    init {
        demoActivity.setContentView(R.layout.app_view)
        initTestData()
        initFunViews()
        initView()
    }

    /**
     * 初始化和加载测试数据
     */
    private fun initTestData() {
        TestData.instance().initialize(demoActivity.baseContext)
    }

    /**
     * 初始化和加载各个测试示例Demo View
     *
     * 非常重要: 所有示例View的Layout布局文件中的根控件Root ViewGroup必须
     * 继承自`com.kingsoft.shiyou.omnisdk.demo.common.view.DemoView`
     */
    private fun initFunViews() {
        functionViewItems.clear()
        // 添加示例Demo View
        functionViewItems.add(FunctionViewItem(R.layout.account_demo_view, null))
        functionViewItems.add(FunctionViewItem(R.layout.pay_demo_view, null))
        functionViewItems.add(FunctionViewItem(R.layout.social_demo_view, null))
        functionViewItems.add(FunctionViewItem(R.layout.ad_demo_view, null))
        functionViewItems.add(FunctionViewItem(R.layout.data_monitor_demo_view, null))
        functionViewItems.add(FunctionViewItem(R.layout.other_demo_view, null))
    }

    fun findDemoViewById(viewId: Int): DemoView? {
        val functionViewItem = functionViewItems.find { it.id == viewId }
        return functionViewItem?.view
    }

    fun setDemoViewById(viewId: Int, view: DemoView) {
        val functionViewItem = functionViewItems.find { it.id == viewId }
        functionViewItem?.view = view
    }

    private lateinit var mUserInfoTv: TextView

    /**
     * 初始化测试Demo UI界面
     */
    private fun initView() {

        // 渠道信息显示
        val mChannelInfoTv: TextView = demoActivity.findViewById(R.id.demo_app_channel_info_tv)
        mChannelInfoTv.text = """
            <b><u><font color="#8B0000">渠道:${OmniSDK.instance.getChannelName()}(${OmniSDK.instance.getChannelId()})</font></u></b>
        """.trimIndent().html()
        mChannelInfoTv.setOnClickListener {
            val configJson =
                DemoFileUtil.contentOfAssetFile(demoActivity, "shiyou/project_config.json")
            showMessageDialog(configJson, "配置(project_config.json):")
        }

        // 用户账号信息显示
        mUserInfoTv = demoActivity.findViewById(R.id.demo_app_user_info_tv)
        setUser(emptyMap())
        mUserInfoTv.setOnClickListener {
            if (userMap.count() > 0) {
                val userInfo = """
                    uid : ${userMap.getOrElse("uid", { "" })}
                    cpUid : ${userMap.getOrElse("cpUid", { "" })}
                    type : ${(userMap.getOrElse("type", { 0 }) as? Number)?.toInt()}
                    token : ${userMap.getOrElse("token", { "" })}
                    showName : ${userMap.getOrElse("showName", { "" })}
                    isRelated(正式账号) : ${userMap.getOrElse("isRelated", { false })}
                    ext : ${userMap.getOrElse("ext", { "" })}
                """.trimIndent()
                showMessageDialog(userInfo, "账号信息:")
            } else {
                showErrorDialog("请先登录账号")
            }
        }

        // Demo View显示
        val functionRecyclerView: RecyclerView = demoActivity.findViewById(R.id.demo_app_content_rv)
        functionRecyclerView.layoutManager = LinearLayoutManager(demoActivity)
        functionRecyclerView.adapter = FunctionAdapter()

    }

    /**
     * RecyclerView Adapter管理Demo View的显示
     */
    private inner class FunctionAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            // 构建各个测试模块UI view
            val demoView = findDemoViewById(viewType) ?: kotlin.run {
                val newDemoView = LayoutInflater.from(demoActivity)
                    .inflate(viewType, parent, false) as DemoView
                newDemoView.appView = this@AppView
                setDemoViewById(viewType, newDemoView)
                newDemoView
            }
            return object : RecyclerView.ViewHolder(demoView) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}
        override fun getItemCount() = functionViewItems.size
        override fun getItemViewType(position: Int) = functionViewItems[position].id
    }

    fun getUser(): Map<String, Any> {
        return userMap
    }

    fun setUser(accountMap: Map<String, Any>) {
        userMap = accountMap
        if (userMap.containsKey("uid") && userMap.containsKey("cpUid")) {
            val uid = userMap["uid"].toString()
            gameRole.uid = uid
            val cpUid = userMap["cpUid"].toString()
            val isGuestAccount = !(userMap["isRelated"] as Boolean)
            if (isGuestAccount) {
                mUserInfoTv.text = """
                    <b><u><font color="red">游客:${cpUid}</font></u></b>
                """.trimIndent().html()
            } else {
                mUserInfoTv.text = """
                    <b><u><font color="#8B0000">账号:${cpUid}</font></u></b>
                """.trimIndent().html()
            }
        } else {
            mUserInfoTv.text = """
                <b><font color="gray">未登录</font></b>
            """.trimIndent().html()
            gameRole = RoleInfo()
        }
    }

    fun showErrorDialog(errorContent: String) = showMessageDialog(errorContent, "错误")

    fun showMessageDialog(content: String) = showMessageDialog(content, "")

    fun showMessageDialog(content: String, title: String) = demoActivity.runOnUiThread {
        DemoDialogUtil.showDialogWithContent(demoActivity, "Demo: $title", content)
    }

    fun showToastMessage(content: String, duration: Int = Toast.LENGTH_SHORT) =
        demoActivity.runOnUiThread {
            Toast.makeText(demoActivity, "Demo: $content", duration).show()
        }

}