package com.kingsoft.shiyou.omnisdk.demo.v3.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.kingsoft.shiyou.omnisdk.api.v3.OmniSDKv3
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKError
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKLoginInfo
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.v3.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.v3.common.ConfigData
import com.kingsoft.shiyou.omnisdk.demo.v3.common.html
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IAccountApi
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IAccountCallback
import com.kingsoft.shiyou.omnisdk.demo.v3.common.utils.DemoDialogUtil
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * Description: 账号相关测试Demo UI
 *
 * @author: LuXing created on 2021/3/9 14:07
 *
 */
@SuppressLint("SetTextI18n")
class AccountDemoView : DemoView, IAccountCallback {

    private lateinit var accountApi: IAccountApi

    private lateinit var titleTv: TextView
    private lateinit var loginContainer: RelativeLayout
    private lateinit var loginButton: Button
    private lateinit var loginTypeContainer: LinearLayout
    private val loginTypeEt: EditText by EditTextDelegate()

    private lateinit var accountManageContainer: LinearLayout
    private lateinit var gameRoleInfoTv: TextView

    private lateinit var switchTypeContainer: LinearLayout
    private val switchTypeEt: EditText by EditTextDelegate()
    private lateinit var bindTypeContainer: LinearLayout
    private val bindTypeEt: EditText by EditTextDelegate()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun initView() {
        accountApi = ApiManager.instance.getAccountApi(appActivity, this)
        titleTv = findViewById(R.id.v3_account_demo_view_title_tv)
        initLoginView()
        initAccountManageView()
        showLoginView()
    }

    private fun initLoginView() {
        loginContainer = findViewById(R.id.v3_account_demo_view_login_container)
        loginButton = findViewById(R.id.v3_account_demo_view_login_btn)
        loginButton.visibility = if (appView.initializedDone) VISIBLE else INVISIBLE
        loginButton.setOnClickListener {
            accountApi.loginImpl(0)
        }
        loginTypeContainer = findViewById(R.id.v3_account_demo_view_login_type_container)
        loginTypeEt
        R.id.v3_account_demo_view_login_type_btn.addClickListener {
            val loginType = loginTypeEt.content().toIntOrNull()
            loginType?.let {
                if (appView.initializedDone) {
                    accountApi.loginImpl(it)
                } else {
                    appView.showToastMessage("正在初始化，请稍后")
                }
            } ?: appView.showToastMessage("无效账号类型")
        }
        if (OmniSDKv3.instance.getAccountMode(baseContext) == 2) {
            // 仅仅容许渠道账号登录
            loginTypeContainer.visibility = View.GONE
            val channel = ConfigData.singletonInstance.channelName.uppercase()
            if (appView.initializedDone) {
                loginButton.text = "${channel}登录"
            }

        }
    }

    private fun initAccountManageView() {
        accountManageContainer = findViewById(R.id.v3_account_demo_view_account_manage_container)

        // 角色数据构建
        gameRoleInfoTv = findViewById(R.id.v3_account_demo_view_game_role_info_tv)
        gameRoleInfoTv.setOnClickListener {
            val createGameRoleView = LayoutInflater.from(appActivity)
                .inflate(R.layout.v3_create_game_role_view, null) as CreateGameRoleView
            createGameRoleView.appView = appView
            createGameRoleView.gameRoleInfo = appView.gameRoleInfo
            DemoDialogUtil.showDialogWithView(
                appView.appActivity, "", createGameRoleView,
                { _, _ ->
                    if (createGameRoleView.gameRoleInfo.roleId.isBlank()) {
                        appView.showErrorDialog("角色数据无效：角色ID为必要数据")
                    } else {
                        // 刷新角色信息数据
                        appView.gameRoleInfo = createGameRoleView.gameRoleInfo
                        refreshGameRoleInfo()
                    }
                }, { _, _ -> }, "创建(更新)", "取消"
            )
        }

        // 账号切换
        switchTypeContainer = findViewById(R.id.v3_account_demo_view_switch_container)
        switchTypeEt
        R.id.v3_account_demo_view_switch_type_btn.addClickListener {
            val switchType = switchTypeEt.content().toIntOrNull()
            switchType?.let {
                accountApi.switchAccountImpl(it)
            } ?: accountApi.switchAccountImpl(0)
        }

        // 账号绑定
        bindTypeContainer = findViewById(R.id.v3_account_demo_view_bind_container)
        bindTypeEt
        R.id.v3_account_demo_view_bind_type_btn.addClickListener {
            val bindType = bindTypeEt.content().toIntOrNull()
            bindType?.let { type ->
                accountApi.bindAccountImpl(type)
            } ?: accountApi.bindAccountImpl(0)
        }

        // 账号登出
        R.id.v3_account_demo_view_logout_btn.addClickListener {
            showProcessingDialog()
            accountApi.logoutImpl()
        }
    }

    /** 显示登录界面 */
    private fun showLoginView() {
        loginContainer.visibility = View.VISIBLE
        accountManageContainer.visibility = View.GONE
        titleTv.text = "请登录"
    }

    /** 显示登录成功后的账号管理界面 */
    private fun showAccountManageView() {
        accountManageContainer.visibility = View.VISIBLE
        loginContainer.visibility = View.GONE
        titleTv.text = "账号管理"

        if (OmniSDKv3.instance.getLinkAccountMode(appView.appActivity) == 0) {
            // 隐藏账号绑定功能入口
            bindTypeContainer.visibility = View.GONE
        } else {
            bindTypeContainer.visibility = View.VISIBLE
        }

        if (OmniSDKv3.instance.getSwitchAccountMode(appView.appActivity) == 0) {
            // 隐藏账号切换功能入口
            switchTypeContainer.visibility = View.GONE
        } else {
            switchTypeContainer.visibility = View.VISIBLE
        }

        refreshGameRoleInfo()
    }

    /** 刷新角色数据 */
    private fun refreshGameRoleInfo() {
        if (appView.gameRoleInfo.uid.isNotBlank() && appView.gameRoleInfo.roleId.isNotBlank()) {
            gameRoleInfoTv.text = appView.gameRoleInfo.let {
                """
                   <b><font color="#8B0000">游戏角色数据：</font></b><br />
                   <b>uid:</b> ${it.uid} <br />
                   <b>roleId:</b> ${it.roleId} <br />
                   <b>roleLevel:</b> ${it.roleLevel} <br />
                   <b>roleName:</b> ${it.roleName} <br />
                   <b>roleType:</b> ${it.roleType} <br />
                   <b>roleVipLevel:</b> ${it.roleVipLevel} <br />
                   <b>roleFigure:</b> ${it.roleFigure} <br />
                   <b>roleCreateTime:</b> ${it.roleCreateTime} <br />
                   <b>serverId:</b> ${it.serverId} <br />
                   <b>serverName:</b> ${it.serverName} <br />
                   <b>zoneId:</b> ${it.zoneId} <br />
                   <b>zoneName:</b> ${it.zoneName} <br />
                   <b>accountAgeInGame:</b> ${it.accountAgeInGame} <br />
                   <b>ageInGame:</b> ${it.ageInGame} <br />
                   <b>balance:</b> ${it.balance} <br />
                   <b>gender:</b> ${it.gender} <br />
                   <b>partyName:</b> ${it.partyName} <br />
                   <b>ext:</b> ${it.ext} <br />
                """.trimIndent().html()
            }
        } else {
            gameRoleInfoTv.text = """
                <b><u><font color="red">创建游戏角色数据(用于事件数据监控追踪等等接口调用)</font></u></b>
            """.trimIndent().html()
        }
    }

    override fun onLoginSucceeded(loginInfo: OmniSDKLoginInfo) {
        appView.showToastMessage("登录成功")
        MainScope().launch {
            appView.setUser(loginInfo)
            showAccountManageView()
            cancelProcessingDialog()
        }
    }

    override fun onBindSucceeded(loginInfo: OmniSDKLoginInfo) {
        appView.showToastMessage("绑定成功")
        MainScope().launch {
            appView.setUser(loginInfo)
            showAccountManageView()
            cancelProcessingDialog()
        }
    }

    override fun onSwitchSucceeded(loginInfo: OmniSDKLoginInfo) {
        appView.showToastMessage("切换成功")
        MainScope().launch {
            appView.setUser(loginInfo)
            showAccountManageView()
            cancelProcessingDialog()
        }
    }

    override fun onDeleteSucceeded() {
        appView.showToastMessage("deleteAccount success")
        MainScope().launch {
            appView.setUser(null)
            showAccountManageView()
            cancelProcessingDialog()
        }
    }

    override fun onRestoreSucceeded(loginInfo: OmniSDKLoginInfo) {
        appView.showToastMessage("restoreAccount success")
        MainScope().launch {
            appView.setUser(loginInfo)
            showAccountManageView()
            cancelProcessingDialog()
        }
    }

    override fun onFailed(error: OmniSDKError) {
        appView.showErrorDialog(error.getDescription())
        MainScope().launch {
            cancelProcessingDialog()
        }
    }

    override fun onCancelled() {
        appView.showToastMessage("取消")
        MainScope().launch {
            cancelProcessingDialog()
        }
    }

    override fun onLogoutSucceeded() {
        appView.showToastMessage("登出成功")
        MainScope().launch {
            appView.setUser(null)
            showLoginView()
            cancelProcessingDialog()
        }
    }

    override fun onLogoutFailed(responseCode: Pair<Int, String>) {
        appView.showErrorDialog(responseCode.toString())
        MainScope().launch {
            cancelProcessingDialog()
        }
    }

}