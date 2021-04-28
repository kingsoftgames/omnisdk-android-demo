package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
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
            Log.i(TAG, "load test game role data")
            // 尝试加载测试游戏角色数据
            if (TestData.instance().isTestDataAvailable(this::class.simpleName!!)) {
                if (TestData.instance()
                        .isTestDataItemAvailable(this::class.simpleName!!, this::roleIdEt.name)
                ) {
                    EditTextDelegate.initData(this, this::roleIdEt)
                } else {
                    appView.showToastMessage("角色ID为必要数据")
                }
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
            }
        }
    }

    var gameRole: RoleInfo
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
        set(value) {
            uidEt = R.id.create_game_role_view_uid_et.editText().apply {
                setText(value.uid)
            }
            roleIdEt = R.id.create_game_role_view_role_id_et.editText().apply {
                setText(value.roleId)
            }
            roleNameEt = R.id.create_game_role_view_role_name_et.editText().apply {
                setText(value.roleName)
            }
            roleLevelEt = R.id.create_game_role_view_role_level_et.editText().apply {
                setText(value.roleLevel)
            }
            roleTypeEt = R.id.create_game_role_view_role_type_et.editText().apply {
                setText(value.roleType)
            }
            roleVipLevelEt = R.id.create_game_role_view_role_vip_level_et.editText().apply {
                setText(value.roleVipLevel)
            }
            roleFigureEt = R.id.create_game_role_view_role_figure_et.editText().apply {
                setText(value.roleFigure)
            }
            roleCreateTimeEt = R.id.create_game_role_view_role_create_time_et.editText().apply {
                setText(value.roleCreateTime)
            }
            serverIdEt = R.id.create_game_role_view_server_id_et.editText().apply {
                setText(value.serverId)
            }
            serverNameEt = R.id.create_game_role_view_server_name_et.editText().apply {
                setText(value.serverName)
            }
            zoneIdEt = R.id.create_game_role_view_zone_id_et.editText().apply {
                setText(value.zoneId)
            }
            zoneNameEt = R.id.create_game_role_view_zone_name_et.editText().apply {
                setText(value.zoneName)
            }
            accountAgeInGameEt =
                R.id.create_game_role_view_account_age_in_game_et.editText().apply {
                    setText(value.accountAgeInGame)
                }
            ageInGameEt = R.id.create_game_role_view_age_in_game_et.editText().apply {
                setText(value.ageInGame)
            }
            balanceEt = R.id.create_game_role_view_balance_et.editText().apply {
                setText(value.balance)
            }
            genderEt = R.id.create_game_role_view_gender_et.editText().apply {
                setText(value.gender)
            }
            partyNameEt = R.id.create_game_role_view_party_name_et.editText().apply {
                setText(value.partyName)
            }
            extEt = R.id.create_game_role_view_ext_et.editText().apply {
                setText(value.ext)
            }
        }

}