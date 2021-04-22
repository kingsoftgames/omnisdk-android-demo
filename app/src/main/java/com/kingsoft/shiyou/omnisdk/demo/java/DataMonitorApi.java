package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.Activity;

import androidx.annotation.Keep;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.entity.RoleInfo;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IDataMonitorApi;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IDataMonitorCallback;

/**
 * Description: OMNI SDK事件数据监控追踪API接口示例Demo
 *
 * @author: LuXing created on 2021/3/24 10:30
 */
@Keep
public class DataMonitorApi implements IDataMonitorApi {

    /* *************************** SDK 事件数据监控追踪API接口示例如下 *************************** */

    /**
     * 监控角色创建事件接口示例
     *
     * @param roleInfo 玩家角色信息
     * @see RoleInfo
     */
    public void onCreateRole(RoleInfo roleInfo) {
        OmniSDK.getInstance().onCreateRole(roleInfo);

        callback.onSucceeded("Monitor(Tracking) Event Is Done");
    }

    /**
     * 监控角色进入游戏事件接口示例
     *
     * @param roleInfo 玩家角色信息
     * @see RoleInfo
     */
    public void onEnterGame(RoleInfo roleInfo) {
        OmniSDK.getInstance().onEnterGame(roleInfo);

        callback.onSucceeded("Monitor(Tracking) Event Is Done");
    }

    /**
     * 监控角色升级事件接口示例
     *
     * @param roleInfo 玩家角色信息
     * @see RoleInfo
     */
    public void onRoleLevelUp(RoleInfo roleInfo) {
        OmniSDK.getInstance().onRoleLevelUp(roleInfo);

        callback.onSucceeded("Monitor(Tracking) Event Is Done");
    }

    /**
     * 监控游戏资源加载事件接口示例
     */
    public void onGameLoadResource() {
        OmniSDK.getInstance().onGameLoadResource();

        callback.onSucceeded("Monitor(Tracking) Event Is Done");
    }

    /**
     * 监控玩家消费虚拟货币事件接口示例
     *
     * @param roleInfo             [RoleInfo]
     * @param amount               虚拟货币数量,如:10、100、100
     * @param virtualCurrencyType  虚拟货币名称，如：元宝、金币等
     * @param virtualCurrencyTotal 虚拟货币总量,如：100、1000、10000
     * @param itemName             消费项目（比如十连抽，购买体力等)
     * @param itemNum              消费项目的数量,如1、2、3
     * @param itemType             消费类型 比如道具
     * @see RoleInfo
     */
    public void onVirtualCurrencyConsume(
            RoleInfo roleInfo,
            int amount,
            String virtualCurrencyType,
            int virtualCurrencyTotal,
            String itemName,
            int itemNum,
            String itemType) {

        OmniSDK.getInstance().onVirtualCurrencyConsume(
                roleInfo,
                amount,
                virtualCurrencyType,
                virtualCurrencyTotal,
                itemName,
                itemNum,
                itemType
        );

        callback.onSucceeded("Monitor(Tracking) Event Is Done");
    }

    /**
     * 监控自定义事件接口（游戏可以通过此接口进行自助数据上报及分析）
     *
     * @param roleInfo  [RoleInfo]
     * @param eventId   事件类型
     * @param eventDesc 事件描述：比如：初始化成功、登录成功等
     * @param eventVal  事件数值。XGSDK的统计平台可以对此数值进行sum／max／min／avg等操作
     * @param eventBody － 事件详细定义，JSON格式，默认为""
     * @see RoleInfo
     */
    public void onEvent(
            RoleInfo roleInfo,
            String eventId,
            String eventDesc,
            int eventVal,
            String eventBody) {

        OmniSDK.getInstance().onEvent(roleInfo, eventId, eventDesc, eventVal, eventBody);

        callback.onSucceeded("Monitor(Tracking) Event Is Done");
    }


    /* ****************************************************************************************** */

    private final String tag = "SDK: " + this.getClass().getName();
    private final Activity demoActivity;
    private final IDataMonitorCallback callback;

    public DataMonitorApi(Activity activity, IDataMonitorCallback dataMonitorCallback) {
        this.demoActivity = activity;
        this.callback = dataMonitorCallback;
    }

    @Override
    public void onCreateRoleImpl(RoleInfo roleInfo) {
        new Thread(() ->
                onCreateRole(roleInfo)
        ).start();

    }

    @Override
    public void onEnterGameImpl(RoleInfo roleInfo) {
        new Thread(() ->
                onEnterGame(roleInfo)
        ).start();

    }

    @Override
    public void onRoleLevelUpImpl(RoleInfo roleInfo) {
        new Thread(() ->
                onRoleLevelUp(roleInfo)
        ).start();
    }

    @Override
    public void onGameLoadResourceImpl() {
        new Thread(this::onGameLoadResource).start();
    }

    @Override
    public void onVirtualCurrencyConsumeImpl(
            RoleInfo roleInfo,
            int amount,
            String virtualCurrencyType,
            int virtualCurrencyTotal,
            String itemName,
            int itemNum,
            String itemType) {

        new Thread(() ->
                onVirtualCurrencyConsume(
                        roleInfo,
                        amount,
                        virtualCurrencyType,
                        virtualCurrencyTotal,
                        itemName,
                        itemNum,
                        itemType)
        ).start();
    }

    @Override
    public void onEventImpl(RoleInfo roleInfo, String eventId, String eventDesc, int eventVal, String eventBody) {
        new Thread(() ->
                onEvent(roleInfo, eventId, eventDesc, eventVal, eventBody)
        ).start();
    }
}
