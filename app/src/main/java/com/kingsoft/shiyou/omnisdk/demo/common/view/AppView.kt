package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.entity.RoleInfo
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.ConfigData
import com.kingsoft.shiyou.omnisdk.demo.common.TestData
import com.kingsoft.shiyou.omnisdk.demo.common.html
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoDialogUtil
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoLogger

/**
 * Description: Demo App 界面UI
 *
 * @author: LuXing created on 2021/3/22 11:28
 *
 */
@SuppressLint("SetTextI18n")
class AppView {

    private val tag = "AppView# "

    /** SDK是否完成相关初始化业务 */
    @Volatile
    var initializedDone = false

    /** Demo应用主Activity */
    lateinit var appActivity: Activity
    val baseContext: Context get() = appActivity.baseContext
    val appContext: Context get() = appActivity.applicationContext

    /** 各项业务功能Demo View集合 */
    private val functionViews = mutableMapOf<Int, DemoView?>()
    private val functionViewKeys: List<Int> by lazy { functionViews.keys.toList() }

    /** 当前用户账号数据 */
    @Volatile
    private var userMap: Map<String, Any> = emptyMap()

    /**
     * 当前游戏角色账号数据
     * `uid` 用户账号ID
     * `roleId` 角色ID
     * `roleLevel` 角色等级
     * `roleName` 角色名称
     * `roleType` 角色类型
     * `roleVipLevel` 角色VIP等级
     * `roleFigure` 体型
     * `roleCreateTime` 角色创建时间 Unix 时间戳，单位秒
     * `serverId` 服ID
     * `serverName` 服名称
     * `zoneId` 区ID
     * `zoneName` 区名称
     * `accountAgeInGame` 账号游戏年龄（单位：天）
     * `ageInGame` 角色游戏年龄（单位：天）
     * `balance` 角色账户余额
     * `gender` 性别
     * `partyName` 所在公会或帮派
     * `ext` 扩展参数
     */
    @Volatile
    var gameRoleInfo: RoleInfo = RoleInfo()

    private lateinit var channelInfoTv: TextView
    private lateinit var userInfoTv: TextView
    private lateinit var functionViewsRv: RecyclerView

    fun attachToActivity(activity: Activity) {
        // 设置Demo应用UI主界面
        activity.setContentView(R.layout.app_view)
        appActivity = activity
        initData()
        initFunctionViews()
        initViews()
    }

    /** 初始化和加载测试数据 */
    private fun initData() {
        TestData.singletonInstance.initialize(appContext)
        ConfigData.singletonInstance.initialize(appContext)
    }

    /**
     * 初始化和加载各项业务功能Demo View
     *
     * 非常重要: 所有示例View的Layout布局文件中的根控件Root ViewGroup必须
     * 继承自`com.kingsoft.shiyou.omnisdk.demo.common.view.DemoView`
     */
    private fun initFunctionViews() =
        functionViews.apply {
            this.clear()
            this[R.layout.exit_demo_view] = null
            this[R.layout.account_demo_view] = null
            this[R.layout.pay_demo_view] = null
            this[R.layout.data_monitor_demo_view] = null
            this[R.layout.social_demo_view] = null
            if (ConfigData.singletonInstance.isEnPlatform()) {
                // 国内渠道暂时无广告和其他功能模块
                this[R.layout.ad_demo_view] = null
                this[R.layout.other_demo_view] = null
            }
        }

    /** 初始化测试Demo应用的UI界面 */
    private fun initViews() {
        DemoLogger.d(tag, "initViews...")
        // 渠道信息显示
        channelInfoTv = appActivity.findViewById(R.id.app_view_channel_info_tv)
        val platform = if (ConfigData.singletonInstance.isCnPlatform()) "国内渠道" else "海外渠道"
        channelInfoTv.text = """
            <b><u><font color="#8B0000">${platform}: ${OmniSDK.instance.getChannelName()}(${OmniSDK.instance.getChannelId()}) #project_config.json#</font></u></b>
        """.trimIndent().html()
        channelInfoTv.setOnClickListener {
            showMessageDialog(
                ConfigData.singletonInstance.projectConfigJsonData,
                "配置(project_config.json)"
            )
        }

        // 用户账号信息显示
        userInfoTv = appActivity.findViewById(R.id.app_view_user_info_tv)
        setUser(null)
        userInfoTv.setOnClickListener {
            if (userMap.containsAccountInfo()) {
                val userInfo = """
                    uid : ${userMap["uid"].toString()}
                    cpUid : ${userMap["cpUid"].toString()}
                    type : ${userMap["type"].toString()}
                    token : ${userMap["token"].toString()}
                    showName : ${userMap["showName"].toString()}
                    certification : ${userMap["certification"].toString()}
                    isRelated(正式账号) : ${userMap["isRelated"].toString()}
                    ext : ${userMap["ext"].toString()}
                """.trimIndent()
                showMessageDialog(userInfo, "账号信息:")
            }
        }

        // 各项业务功能Demo View的显示
        functionViewsRv = appActivity.findViewById(R.id.app_view_function_views_rv)
        functionViewsRv.layoutManager = LinearLayoutManager(appActivity)
        functionViewsRv.adapter = FunctionViewsAdapter()
    }

    /** RecyclerView Adapter管理各项业务功能Demo View的显示 */
    private inner class FunctionViewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            // 构建各个测试模块UI view
            val demoView = functionViews[viewType] ?: kotlin.run {
                val newDemoView = LayoutInflater.from(appActivity)
                    .inflate(viewType, parent, false) as DemoView
                newDemoView.appView = this@AppView
                functionViews[viewType] = newDemoView
                newDemoView
            }
            return object : RecyclerView.ViewHolder(demoView) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}
        override fun getItemCount() = functionViewKeys.size
        override fun getItemViewType(position: Int) = functionViewKeys[position]
    }

    fun getUser(): Map<String, Any> {
        return userMap
    }

    /** 设置用户账号数据 */
    fun setUser(accountUserMap: Map<String, Any>?) {
        DemoLogger.d(tag, "setUser(...) called")
        userMap = accountUserMap ?: emptyMap()
        if (userMap.containsAccountInfo()) {
            val uid = userMap["uid"].toString()
            // 确保游戏角色的用户账号uid为当前登录的账号uid
            gameRoleInfo.uid = uid
            val cpUid = userMap["cpUid"].toString()
            val isNotGuestAccount = (true == (userMap["isRelated"] as? Boolean))
            if (isNotGuestAccount) {
                userInfoTv.text = """
                    <b><u><font color="blue">账号ID: ${cpUid}</font></u></b>
                """.trimIndent().html()
            } else {
                userInfoTv.text = """
                    <b><u><font color="red">游客账号ID: ${cpUid}</font></u></b>
                """.trimIndent().html()
            }
        } else {
            userInfoTv.text = """
                <b><font color="gray">未登录</font></b>
            """.trimIndent().html()
            // 清空游戏角色数据
            gameRoleInfo = RoleInfo()
        }
    }

    private fun Map<String, Any>?.containsAccountInfo() =
        if (!(this.isNullOrEmpty())) {
            this.containsKey("uid") && this["uid"].toString().length > 5 &&
                    this.containsKey("cpUid") && this["cpUid"].toString().length > 5
        } else {
            false
        }

    fun showErrorDialog(errorContent: String) = showMessageDialog(errorContent, "错误")

    fun showMessageDialog(content: String) = showMessageDialog(content, "")

    fun showMessageDialog(content: String, title: String) =
        appActivity.runOnUiThread {
            DemoDialogUtil.showDialogWithContent(appActivity, "Demo $title", content)
        }

    fun showToastMessage(content: String, duration: Int = Toast.LENGTH_SHORT) =
        appActivity.runOnUiThread {
            Toast.makeText(appActivity, "Demo: $content", duration).show()
        }

}