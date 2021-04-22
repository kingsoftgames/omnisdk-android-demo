package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.kingsoft.shiyou.omnisdk.api.entity.RoleInfo
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.ApiManager
import com.kingsoft.shiyou.omnisdk.demo.common.html
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAccountApi
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IAccountCallback
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoDialogUtil
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

    /**
     * 指定登录账号类型输入框
     */
    private val loginTypeEt: EditText by EditTextDelegate()

    private lateinit var accountContainer: LinearLayout
    private lateinit var gameRoleInfoTv: TextView

    /**
     * 指定切换账号类型输入框
     */
    private val switchTypeEt: EditText by EditTextDelegate()

    private lateinit var bindContainer: LinearLayout

    /**
     * 指定绑定账号类型输入框
     */
    private val bindTypeEt: EditText by EditTextDelegate()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun initView() {

        // 获取账号API接口实例
        accountApi = ApiManager.instance.getAccountApi(this)

        loginContainer = findViewById(R.id.account_demo_view_login_container)
        accountContainer = findViewById(R.id.account_demo_view_account_container)
        titleTv = findViewById(R.id.account_demo_view_title_tv)

        R.id.account_demo_view_login_btn.addClickListener {
            accountApi.loginImpl(0)
        }
        loginTypeEt
        R.id.account_demo_view_login_type_btn.addClickListener {
            val loginType = loginTypeEt.text.toString().toIntOrNull()
            loginType?.let {
                accountApi.loginImpl(it)
            } ?: appView.showToastMessage("无效账号类型")
        }

        R.id.account_demo_view_logout_btn.addClickListener {
            showProcessingDialog()
            accountApi.logoutImpl()
        }

        switchTypeEt
        R.id.account_demo_view_switch_btn.addClickListener {
            val typeString = switchTypeEt.text.toString()
            val switchType = if (typeString.isBlank()) {
                0
            } else {
                typeString.toIntOrNull()
            }
            switchType?.let {
                accountApi.switchAccountImpl(it)
            } ?: appView.showToastMessage("无效账号类型")
        }

        bindContainer = findViewById(R.id.account_demo_view_bind_container)
        bindTypeEt
        R.id.account_demo_view_bind_btn.addClickListener {
            val typeString = bindTypeEt.text.toString()
            val bindType = if (typeString.isBlank()) {
                0
            } else {
                typeString.toIntOrNull()
            }
            bindType?.let { type ->
                accountApi.bindAccountImpl(type)
            } ?: appView.showToastMessage("无效账号类型")
        }

        gameRoleInfoTv = findViewById(R.id.account_demo_view_game_role_info_tv)
        gameRoleInfoTv.setOnClickListener {
            val createGameRoleView = LayoutInflater.from(demoActivity)
                .inflate(R.layout.create_game_role_view, null) as CreateGameRoleView
            createGameRoleView.appView = appView
            createGameRoleView.gameRole = appView.gameRole
            DemoDialogUtil.showDialogWithView(
                appView.demoActivity, "", createGameRoleView,
                { _, _ ->

                    if (createGameRoleView.gameRole.roleId.isBlank()) {
                        appView.showToastMessage("角色ID为必要数据")
                    } else {
                        // 刷新角色信息数据
                        appView.gameRole = createGameRoleView.gameRole
                        refreshGameRoleInfo()
                    }

                }, { _, _ -> }, "创建(更新)", "取消"
            )
        }

        showLoginView()
    }

    private fun refreshGameRoleInfo() {
        gameRoleInfoTv.text = appView.gameRole.display()
    }

    private fun RoleInfo.display(): CharSequence {
        return """
            <b><font color="#8B0000">游戏角色信息: </font></b><br />
            <b>uid:</b> $uid <br />
            <b>roleId:</b> $roleId <br />
            <b>roleLevel:</b> $roleLevel <br />
            <b>roleName:</b> $roleName <br />
            <b>roleType:</b> $roleType <br />
            <b>roleVipLevel:</b> $roleVipLevel <br />
            <b>roleFigure:</b> $roleFigure <br />
            <b>roleCreateTime:</b> $roleCreateTime <br />
            <b>serverId:</b> $serverId <br />
            <b>serverName:</b> $serverName <br />
            <b>zoneId:</b> $zoneId <br />
            <b>zoneName:</b> $zoneName <br />
            <b>accountAgeInGame:</b> $accountAgeInGame <br />
            <b>ageInGame:</b> $ageInGame <br />
            <b>balance:</b> $balance <br />
            <b>gender:</b> $gender <br />
            <b>partyName:</b> $partyName <br />
            <b>ext:</b> $ext <br />
        """.trimIndent().html()
    }

    /**
     * 显示登录界面
     */
    private fun showLoginView() {
        loginContainer.visibility = View.VISIBLE
        accountContainer.visibility = View.GONE
        titleTv.text = "请登录"
    }

    /**
     * 显示登录成功后的账号管理界面
     * @param isGuest Boolean
     */
    private fun showAccountView(isGuest: Boolean) {
        accountContainer.visibility = View.VISIBLE
        loginContainer.visibility = View.GONE
        titleTv.text = "账号管理"
        if (isGuest) {
            bindContainer.visibility = View.VISIBLE
        } else {
            bindContainer.visibility = View.GONE
        }
        gameRoleInfoTv.text = """
           <b><u><font color="red">创建游戏角色数据</font></u></b>
        """.trimIndent().html()
    }

    override fun onLoginSucceeded(userMap: Map<String, Any>, type: Int) {
        MainScope().launch {
            appView.showToastMessage("登录成功")
            showAccountView(type == 1)
            appView.setUser(userMap)
        }
    }

    override fun onBindSucceeded(userMap: Map<String, Any>) {
        MainScope().launch {
            appView.showToastMessage("绑定成功")
            showAccountView(false)
            appView.setUser(userMap)
        }
    }

    override fun onSwitchSucceeded(userMap: Map<String, Any>) {
        MainScope().launch {
            appView.showToastMessage("切换成功")
            showAccountView(false)
            appView.setUser(userMap)
        }
    }

    override fun onFailed(responseCode: Pair<Int, String>) {
        appView.showErrorDialog(responseCode.toString())
    }

    override fun onCancelled() {
        // 操作取消,无需弹提示
    }

    override fun onLogoutSucceeded() {
        MainScope().launch {
            showLoginView()
            cancelProcessingDialog()
            appView.setUser(emptyMap())
            appView.showToastMessage("登出成功")
        }
    }

    override fun onLogoutFailed(responseCode: Pair<Int, String>) {
        MainScope().launch {
            cancelProcessingDialog()
            appView.showErrorDialog(responseCode.toString())
        }
    }

}