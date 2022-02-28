package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.TextView
import com.kingsoft.shiyou.omnisdk.api.entity.RoleInfo
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.TestData

/**
 * Description: 玩家角色信息数据输入UI
 *
 * @author: LuXing created on 2021/3/12 11:14
 *
 */
class CreateGameRoleView : DemoView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    private lateinit var loadTestDataTv: TextView

    private lateinit var uidEt: EditText
    private lateinit var roleIdEt: EditText
    private lateinit var roleNameEt: EditText
    private lateinit var roleLevelEt: EditText
    private lateinit var roleTypeEt: EditText
    private lateinit var roleVipLevelEt: EditText
    private lateinit var roleFigureEt: EditText
    private lateinit var roleCreateTimeEt: EditText
    private lateinit var serverIdEt: EditText
    private lateinit var serverNameEt: EditText
    private lateinit var zoneIdEt: EditText
    private lateinit var zoneNameEt: EditText
    private lateinit var accountAgeInGameEt: EditText
    private lateinit var ageInGameEt: EditText
    private lateinit var balanceEt: EditText
    private lateinit var genderEt: EditText
    private lateinit var partyNameEt: EditText
    private lateinit var extEt: EditText

    override fun initView() {
        loadTestDataTv = findViewById(R.id.create_game_role_view_load_test_data_tv)
        loadTestDataTv.setOnClickListener {
            if (TestData.singletonInstance.testDataAvailableForDemoView(this::class.simpleName!!)) {
                // 尝试加载测试游戏角色数据
                EditTextDelegate.initData(this, this::roleIdEt)
                EditTextDelegate.initData(this, this::roleNameEt)
                EditTextDelegate.initData(this, this::roleLevelEt)
                EditTextDelegate.initData(this, this::roleTypeEt)
                EditTextDelegate.initData(this, this::roleVipLevelEt)
                EditTextDelegate.initData(this, this::roleFigureEt)
                EditTextDelegate.initData(this, this::roleCreateTimeEt)
                EditTextDelegate.initData(this, this::serverIdEt)
                EditTextDelegate.initData(this, this::serverNameEt)
                EditTextDelegate.initData(this, this::zoneIdEt)
                EditTextDelegate.initData(this, this::zoneNameEt)
                EditTextDelegate.initData(this, this::accountAgeInGameEt)
                EditTextDelegate.initData(this, this::ageInGameEt)
                EditTextDelegate.initData(this, this::balanceEt)
                EditTextDelegate.initData(this, this::genderEt)
                EditTextDelegate.initData(this, this::partyNameEt)
                EditTextDelegate.initData(this, this::extEt)
            } else {
                appView.showToastMessage("无角色测试数据")
            }
        }
    }

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
    var gameRoleInfo: RoleInfo
        get() {
            return RoleInfo().apply {
                uid = uidEt.content()
                roleId = roleIdEt.content()
                roleName = roleNameEt.content()
                roleLevel = roleLevelEt.content()
                roleType = roleTypeEt.content()
                roleVipLevel = roleVipLevelEt.content()
                roleFigure = roleFigureEt.content()
                roleCreateTime = roleCreateTimeEt.content()
                serverId = serverIdEt.content()
                serverName = serverNameEt.content()
                zoneId = zoneIdEt.content()
                zoneName = zoneNameEt.content()
                accountAgeInGame = accountAgeInGameEt.content()
                ageInGame = ageInGameEt.content()
                balance = balanceEt.content()
                gender = genderEt.content()
                partyName = partyNameEt.content()
                ext = extEt.content()
            }
        }
        set(inputRoleInfo) {
            uidEt = R.id.create_game_role_view_uid_et.editText().apply {
                setText(inputRoleInfo.uid)
            }
            roleIdEt = R.id.create_game_role_view_role_id_et.editText().apply {
                setText(inputRoleInfo.roleId)
            }
            roleNameEt = R.id.create_game_role_view_role_name_et.editText().apply {
                setText(inputRoleInfo.roleName)
            }
            roleLevelEt = R.id.create_game_role_view_role_level_et.editText().apply {
                setText(inputRoleInfo.roleLevel)
            }
            roleTypeEt = R.id.create_game_role_view_role_type_et.editText().apply {
                setText(inputRoleInfo.roleType)
            }
            roleVipLevelEt = R.id.create_game_role_view_role_vip_level_et.editText().apply {
                setText(inputRoleInfo.roleVipLevel)
            }
            roleFigureEt = R.id.create_game_role_view_role_figure_et.editText().apply {
                setText(inputRoleInfo.roleFigure)
            }
            roleCreateTimeEt = R.id.create_game_role_view_role_create_time_et.editText().apply {
                setText(inputRoleInfo.roleCreateTime)
            }
            serverIdEt = R.id.create_game_role_view_server_id_et.editText().apply {
                setText(inputRoleInfo.serverId)
            }
            serverNameEt = R.id.create_game_role_view_server_name_et.editText().apply {
                setText(inputRoleInfo.serverName)
            }
            zoneIdEt = R.id.create_game_role_view_zone_id_et.editText().apply {
                setText(inputRoleInfo.zoneId)
            }
            zoneNameEt = R.id.create_game_role_view_zone_name_et.editText().apply {
                setText(inputRoleInfo.zoneName)
            }
            accountAgeInGameEt =
                R.id.create_game_role_view_account_age_in_game_et.editText().apply {
                    setText(inputRoleInfo.accountAgeInGame)
                }
            ageInGameEt = R.id.create_game_role_view_age_in_game_et.editText().apply {
                setText(inputRoleInfo.ageInGame)
            }
            balanceEt = R.id.create_game_role_view_balance_et.editText().apply {
                setText(inputRoleInfo.balance)
            }
            genderEt = R.id.create_game_role_view_gender_et.editText().apply {
                setText(inputRoleInfo.gender)
            }
            partyNameEt = R.id.create_game_role_view_party_name_et.editText().apply {
                setText(inputRoleInfo.partyName)
            }
            extEt = R.id.create_game_role_view_ext_et.editText().apply {
                setText(inputRoleInfo.ext)
            }
        }

}