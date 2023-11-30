package com.kingsoft.shiyou.omnisdk.demo.v3.common.view

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
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKAuthMethod
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKLoginInfo
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.v3.common.ConfigData
import com.kingsoft.shiyou.omnisdk.demo.v3.common.TestData
import com.kingsoft.shiyou.omnisdk.demo.v3.common.html
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoDialogUtil
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoLogger
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

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
    private var loginInfo: OmniSDKLoginInfo? = null

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
        activity.setContentView(R.layout.v3_app_view)
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

    public fun onInitializedDone(result: Boolean) {
        this.initializedDone = result
        // 等待初始化完成后，再初始化 UI 部分信息 (内部有依赖 OmniSDK API)
        MainScope().launch {
            initViews()
        }
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
            this[R.layout.v3_jinshan_demo_view] = null
            this[R.layout.v3_exit_demo_view] = null
            this[R.layout.v3_account_demo_view] = null
            this[R.layout.v3_pay_demo_view] = null
            this[R.layout.v3_permisson_demo_view] = null
            this[R.layout.v3_data_monitor_demo_view] = null
            this[R.layout.v3_ads_demo_view] = null
            this[R.layout.v3_social_demo_view] = null
            this[R.layout.v3_other_demo_view] = null
        }

    /** 初始化测试Demo应用的UI界面 */
    private fun initViews() {
        DemoLogger.d(tag, "initViews...")
        // 渠道信息显示
        channelInfoTv = appActivity.findViewById(R.id.v3_app_view_channel_info_tv)
        val platform = if (ConfigData.singletonInstance.isCnPlatform()) "国内渠道" else "海外渠道"
        channelInfoTv.text = """
            <b><u><font color="#8B0000">${platform}: ${ConfigData.singletonInstance.channelName}(${ConfigData.singletonInstance.channelId}) #project_config.json#</font></u></b>
        """.trimIndent().html()
        channelInfoTv.setOnClickListener {
            showMessageDialog(
                ConfigData.singletonInstance.projectConfigJsonData,
                "配置(project_config.json)"
            )
        }

        // 用户账号信息显示
        userInfoTv = appActivity.findViewById(R.id.v3_app_view_user_info_tv)
        setUser(null)
        userInfoTv.setOnClickListener {
            loginInfo?.let {
                val userInfo = """
                    uid : ${it.userId}
                    type : ${it.authMethod.modeValue}
                    token : ${it.token}
                    showName : ${it.username}
                """.trimIndent()
                showMessageDialog(userInfo, "账号信息:")
            }
        }

        // 各项业务功能Demo View的显示
        functionViewsRv = appActivity.findViewById(R.id.v3_app_view_function_views_rv)
        functionViewsRv.layoutManager = LinearLayoutManager(appActivity)
        functionViewsRv.adapter = FunctionViewsAdapter()
    }

    /** RecyclerView Adapter管理各项业务功能Demo View的显示 */
    private inner class FunctionViewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder {
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

    @Deprecated("Use getLoginInfo")
    fun getUser(): OmniSDKLoginInfo? {
        return loginInfo
    }

    fun getLoginInfo(): OmniSDKLoginInfo? {
        return loginInfo
    }

    /** 设置用户账号数据 */
    fun setUser(info: OmniSDKLoginInfo?) {
        DemoLogger.d(tag, "setUser(...) called")
        loginInfo = info
        loginInfo?.let {
            gameRoleInfo.uid = it.userId
            val isGuest = it.authMethod.modeValue == OmniSDKAuthMethod.GUEST.modeValue
            if (!isGuest) {
                userInfoTv.text = """
                    <b><u><font color="blue">账号ID: ${it.userId}</font></u></b>
                """.trimIndent().html()
            } else {
                userInfoTv.text = """
                    <b><u><font color="red">游客账号ID: ${it.userId}</font></u></b>
                """.trimIndent().html()
            }
        } ?: run {
            userInfoTv.text = """
                <b><font color="gray">未登录</font></b>
            """.trimIndent().html()
            // 清空游戏角色数据
            gameRoleInfo = RoleInfo()
        }
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