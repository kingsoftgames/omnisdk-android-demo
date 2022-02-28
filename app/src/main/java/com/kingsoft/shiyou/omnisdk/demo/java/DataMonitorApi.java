package com.kingsoft.shiyou.omnisdk.demo.java;

import android.app.Activity;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.kingsoft.shiyou.omnisdk.api.OmniSDK;
import com.kingsoft.shiyou.omnisdk.api.entity.PayInfo;
import com.kingsoft.shiyou.omnisdk.api.entity.RoleInfo;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IDataMonitorApi;
import com.kingsoft.shiyou.omnisdk.demo.common.interfaces.IDataMonitorCallback;

/**
 * Description: OmniSDK事件数据追踪埋点相关业务API接口代码示例Demo
 *
 * @author: LuXing created on 2021/3/24 10:30
 */
@Keep
public class DataMonitorApi implements IDataMonitorApi {

    private final String tag = "DataMonitorApi# ";
    private final Activity appActivity;
    private final IDataMonitorCallback callback;

    public DataMonitorApi(Activity activity, IDataMonitorCallback dataMonitorCallback) {
        this.appActivity = activity;
        this.callback = dataMonitorCallback;
    }

    /* ************************** OmniSDK事件数据追踪埋点功能接口示例如下 ************************** */

    private void addCommonAttribute(String key, String value) {
        OmniSDK.getInstance().addCommonAttribute(key, value);

        callback.onSucceeded("添加 [ " + key + " = " + value + " ]");
    }

    /**
     * 监控追踪角色创建事件接口示例
     *
     * @param roleInfo 玩家角色信息
     * @see RoleInfo
     */
    public void onCreateRole(RoleInfo roleInfo) {
        OmniSDK.getInstance().onCreateRole(roleInfo);

        callback.onSucceeded("Monitor(Tracking) `onCreateRole` Is Done");
    }

    /**
     * 监控追踪角色进入游戏事件接口示例
     *
     * @param roleInfo 玩家角色信息
     * @see RoleInfo
     */
    public void onEnterGame(RoleInfo roleInfo) {
        OmniSDK.getInstance().onEnterGame(roleInfo);

        callback.onSucceeded("Monitor(Tracking) `onEnterGame` Is Done");
    }

    /**
     * 监控追踪角色等级升级事件接口示例
     *
     * @param roleInfo 玩家角色信息
     * @see RoleInfo
     */
    public void onRoleLevelUp(RoleInfo roleInfo) {
        OmniSDK.getInstance().onRoleLevelUp(roleInfo);

        callback.onSucceeded("Monitor(Tracking) `onRoleLevelUp` Is Done");
    }

    /**
     * 玩家完成新手任务时调用该接口
     *
     * @param roleInfo 玩家角色信息
     * @see RoleInfo
     */
    public void onNewUserMission(RoleInfo roleInfo) {
        OmniSDK.getInstance().onNewUserMission(roleInfo);

        callback.onSucceeded("Monitor(Tracking) `onNewUserMission` Is Done");
    }

    /**
     * 游戏加载资源完成时调用该接口，发送相关信息。
     */
    public void onGameLoadResource() {
        OmniSDK.getInstance().onGameLoadResource();

        callback.onSucceeded("Monitor(Tracking) `onGameLoadResource` Is Done");
    }

    /**
     * 游戏加载配置完成时调用该接口，发送相关信息。
     */
    public void onGameLoadConfig() {
        OmniSDK.getInstance().onGameLoadConfig();

        callback.onSucceeded("Monitor(Tracking) `onGameLoadConfig` Is Done");
    }

    /**
     * 用户打开公告时调用该接口，发送相关信息。
     */
    public void onOpenAnnouncement() {
        OmniSDK.getInstance().onOpenAnnouncement();

        callback.onSucceeded("Monitor(Tracking) `onOpenAnnouncement` Is Done");
    }

    /**
     * 用户关闭公告时调用该接口，发送相关信息。
     */
    public void onCloseAnnouncement() {
        OmniSDK.getInstance().onCloseAnnouncement();

        callback.onSucceeded("Monitor(Tracking) `onCloseAnnouncement` Is Done");
    }

    /**
     * 用户购买支付成功的时候调用此接口
     *
     * @param payInfo 支付相关数据
     * @see PayInfo
     */
    public void onPayFinish(PayInfo payInfo) {
        OmniSDK.getInstance().onPayFinish(payInfo);

        callback.onSucceeded("Monitor(Tracking) `onPayFinish` Is Done");
    }

    /**
     * 跟踪玩家的任务或关卡完成情况。当开始某个任务或关卡时，调用此接口
     *
     * @param roleInfo         - 角色信息，请参考RoleInfo的定义
     * @param missionId        - 任务ID
     * @param missionName      - 任务名称，比如游戏关卡任务开始 传值 game_level_begin
     * @param doMissionTimes   次数，第几次执行该任务/关卡就传几
     * @param roleCurrentPower 角色当前体力值
     * @param customParams     - 扩展参数，JSON格式，比如{"level":10},默认为null
     */
    public void onMissionBegin(
            RoleInfo roleInfo,
            String missionId,
            String missionName,
            int doMissionTimes,
            int roleCurrentPower,
            String customParams
    ) {
        OmniSDK.getInstance().onMissionBegin(
                roleInfo,
                missionId,
                missionName,
                doMissionTimes,
                roleCurrentPower,
                customParams);

        callback.onSucceeded("Monitor(Tracking) `onMissionBegin` Is Done");
    }

    /**
     * 跟踪玩家的任务或关卡完成情况。当某个任务或关卡已经成功完成，调用此接口
     *
     * @param roleInfo         - 角色信息，请参考RoleInfo的定义
     * @param missionId        - 任务ID
     * @param missionName      - 任务名称，比如游戏关卡成功完成: game_level_end_succeeded
     * @param doMissionTimes   次数，第几次执行该任务/关卡就传几
     * @param roleCurrentPower 角色当前体力值
     * @param customParams     - 扩展参数，JSON格式，比如{"level":10},默认为null
     */
    public void onMissionSuccess(
            RoleInfo roleInfo,
            String missionId,
            String missionName,
            int doMissionTimes,
            int roleCurrentPower,
            String customParams
    ) {
        OmniSDK.getInstance().onMissionSuccess(
                roleInfo,
                missionId,
                missionName,
                doMissionTimes,
                roleCurrentPower,
                customParams);

        callback.onSucceeded("Monitor(Tracking) `onMissionSuccess` Is Done");
    }

    /**
     * 跟踪玩家的任务或关卡完成情况。当某个任务或关卡执行失败时，调用此接口
     *
     * @param roleInfo         - 角色信息，请参考RoleInfo的定义
     * @param missionId        - 任务ID
     * @param missionName      - 任务名称，比如游戏关卡完成失败: game_level_end_failed
     * @param doMissionTimes   次数，第几次执行该任务/关卡就传几
     * @param roleCurrentPower 角色当前体力值
     * @param customParams     - 扩展参数，JSON格式，比如{"level":10},默认为null
     */
    public void onMissionFail(
            RoleInfo roleInfo,
            String missionId,
            String missionName,
            int doMissionTimes,
            int roleCurrentPower,
            String customParams
    ) {
        OmniSDK.getInstance().onMissionFail(
                roleInfo,
                missionId,
                missionName,
                doMissionTimes,
                roleCurrentPower,
                customParams);

        callback.onSucceeded("Monitor(Tracking) `onMissionFail` Is Done");
    }

    /**
     * 跟踪玩家的购买及消费行为。当玩家通过充值获得游戏内虚拟货币时，调用此接口
     *
     * @param roleInfo             [RoleInfo]
     * @param amount               虚拟货币数量,如:10、100、100
     * @param virtualCurrencyType  虚拟货币名称，如：元宝、金币等
     * @param virtualCurrencyTotal 虚拟货币总量,如：100、1000、10000
     * @param gameTradeNo          游戏传入的订单号,r如trade123456789
     * @see RoleInfo
     */
    public void onVirtualCurrencyPurchase(
            RoleInfo roleInfo,
            int amount,
            String virtualCurrencyType,
            int virtualCurrencyTotal,
            String gameTradeNo
    ) {
        OmniSDK.getInstance().onVirtualCurrencyPurchase(
                roleInfo,
                amount,
                virtualCurrencyType,
                virtualCurrencyTotal,
                gameTradeNo
        );

        callback.onSucceeded("Monitor(Tracking) `onVirtualCurrencyPurchase` Is Done");
    }

    /**
     * 跟踪玩家的购买及消费行为。当玩家完成活动或任务后，系统赠送给玩家虚拟货币时，调用此接口
     *
     * @param roleInfo             [RoleInfo]
     * @param amount               虚拟货币数量,如:10、100、100
     * @param virtualCurrencyType  虚拟货币名称，如：元宝、金币等
     * @param virtualCurrencyTotal 虚拟货币总量,如：100、1000、10000
     * @param gainChannel          货币来源，比如（每日登录奖励,抽宝箱等)
     * @param gainChannelType      货币来源类型比如日常任务、闯关等
     * @param gameTradeNo          游戏传入的订单号,r如trade123456789
     * @see RoleInfo
     */
    public void onVirtualCurrencyReward(
            RoleInfo roleInfo,
            int amount,
            String virtualCurrencyType,
            int virtualCurrencyTotal,
            String gainChannel,
            String gainChannelType,
            String gameTradeNo
    ) {
        OmniSDK.getInstance().onVirtualCurrencyReward(
                roleInfo,
                amount,
                virtualCurrencyType,
                virtualCurrencyTotal,
                gainChannel,
                gainChannelType,
                gameTradeNo);

        callback.onSucceeded("Monitor(Tracking) `onVirtualCurrencyReward` Is Done");
    }

    /**
     * 跟踪玩家的购买及消费行为。当玩家消费虚拟货币时，调用此接口
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
            String itemType
    ) {
        OmniSDK.getInstance().onVirtualCurrencyConsume(
                roleInfo,
                amount,
                virtualCurrencyType,
                virtualCurrencyTotal,
                itemName,
                itemNum,
                itemType
        );

        callback.onSucceeded("Monitor(Tracking) `onVirtualCurrencyConsume` Is Done");
    }

    /**
     * 用户使用专用的功能码（激活码、礼品码等）时，调用该接口，发送相关信息。
     *
     * @param roleInfo       角色信息
     * @param funCode        码值
     * @param funCodeDesc    码的描述
     * @param funCodeType    码的类型
     * @param funCodeBatchId 码的批号
     * @see RoleInfo
     */
    public void onPrivateFunCodeUse(
            RoleInfo roleInfo,
            String funCode,
            String funCodeDesc,
            String funCodeType,
            String funCodeBatchId
    ) {

        OmniSDK.getInstance().onPrivateFunCodeUse(roleInfo, funCode, funCodeDesc, funCodeType, funCodeBatchId);

        callback.onSucceeded("Monitor(Tracking) `onPrivateFunCodeUse` Is Done");
    }

    /**
     * 用户使用公用的功能码（激活码、礼品码等）时，调用该接口，发送相关信息。
     *
     * @param roleInfo       角色信息
     * @param roleInfo       角色信息
     * @param funCode        码值
     * @param funCodeDesc    码的描述
     * @param funCodeType    码的类型
     * @param funCodeBatchId 码的批号
     * @see RoleInfo
     */
    public void onPublicFunCodeUse(
            RoleInfo roleInfo,
            String funCode,
            String funCodeDesc,
            String funCodeType,
            String funCodeBatchId
    ) {
        OmniSDK.getInstance().onPublicFunCodeUse(roleInfo, funCode, funCodeDesc, funCodeType, funCodeBatchId);

        callback.onSucceeded("Monitor(Tracking) `onPublicFunCodeUse` Is Done");
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
            String eventBody
    ) {
        OmniSDK.getInstance().onEvent(roleInfo, eventId, eventDesc, eventVal, eventBody);

        callback.onSucceeded("Monitor(Tracking) `onEvent` Is Done");
    }

    /* ****************************************************************************************** */

    @Override
    public void addCommonAttributeImpl(@NonNull String key, @NonNull String value) {
        addCommonAttribute(key, value);
    }

    @Override
    public void onCreateRoleImpl(@NonNull RoleInfo roleInfo) {
        new Thread(() ->
                onCreateRole(roleInfo)
        ).start();
    }

    @Override
    public void onEnterGameImpl(@NonNull RoleInfo roleInfo) {
        new Thread(() ->
                onEnterGame(roleInfo)
        ).start();
    }

    @Override
    public void onRoleLevelUpImpl(@NonNull RoleInfo roleInfo) {
        new Thread(() ->
                onRoleLevelUp(roleInfo)
        ).start();
    }

    @Override
    public void onNewUserMissionImpl(@NonNull RoleInfo roleInfo) {
        new Thread(() ->
                onNewUserMission(roleInfo)
        ).start();
    }

    @Override
    public void onGameLoadResourceImpl() {
        new Thread(this::onGameLoadResource).start();
    }

    @Override
    public void onGameLoadConfigImpl() {
        new Thread(this::onGameLoadConfig).start();
    }

    @Override
    public void onOpenAnnouncementImpl() {
        new Thread(this::onOpenAnnouncement).start();
    }

    @Override
    public void onCloseAnnouncementImpl() {
        new Thread(this::onCloseAnnouncement).start();
    }

    @Override
    public void onPayFinishImpl(@NonNull PayInfo payInfo) {
        new Thread(() ->
                onPayFinish(payInfo)
        ).start();
    }

    @Override
    public void onMissionBeginImpl(
            RoleInfo roleInfo,
            String missionId,
            String missionName,
            int doMissionTimes,
            int roleCurrentPower,
            String customParams) {
        new Thread(() ->
                onMissionBegin(roleInfo, missionId, missionName, doMissionTimes, roleCurrentPower, customParams)
        ).start();
    }

    @Override
    public void onMissionSuccessImpl(
            RoleInfo roleInfo,
            String missionId,
            String missionName,
            int doMissionTimes,
            int roleCurrentPower,
            String customParams) {
        new Thread(() ->
                onMissionSuccess(roleInfo, missionId, missionName, doMissionTimes, roleCurrentPower, customParams)
        ).start();
    }

    @Override
    public void onMissionFailImpl(
            RoleInfo roleInfo,
            String missionId,
            String missionName,
            int doMissionTimes,
            int roleCurrentPower,
            String customParams) {
        new Thread(() ->
                onMissionFail(roleInfo, missionId, missionName, doMissionTimes, roleCurrentPower, customParams)
        ).start();
    }

    @Override
    public void onVirtualCurrencyPurchaseImpl(@NonNull RoleInfo roleInfo, int amount, @NonNull String virtualCurrencyType, int virtualCurrencyTotal, @NonNull String gameTradeNo) {
        new Thread(() ->
                onVirtualCurrencyPurchase(roleInfo, amount, virtualCurrencyType, virtualCurrencyTotal, gameTradeNo)
        ).start();
    }

    @Override
    public void onVirtualCurrencyRewardImpl(@NonNull RoleInfo roleInfo, int amount, @NonNull String virtualCurrencyType, int virtualCurrencyTotal, @NonNull String gainChannel, @NonNull String gainChannelType, @NonNull String gameTradeNo) {
        new Thread(() ->
                onVirtualCurrencyReward(roleInfo, amount, virtualCurrencyType, virtualCurrencyTotal, gainChannel, gainChannelType, gameTradeNo)
        ).start();
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
                onVirtualCurrencyConsume(roleInfo, amount, virtualCurrencyType, virtualCurrencyTotal, itemName, itemNum, itemType)
        ).start();
    }

    @Override
    public void onPrivateFunCodeUseImpl(@NonNull RoleInfo roleInfo, @NonNull String funCode, @NonNull String funCodeDesc, @NonNull String funCodeType, @NonNull String funCodeBatchId) {
        new Thread(() ->
                onPrivateFunCodeUse(roleInfo, funCode, funCodeDesc, funCodeType, funCodeBatchId)
        ).start();
    }

    @Override
    public void onPublicFunCodeUseImpl(@NonNull RoleInfo roleInfo, @NonNull String funCode, @NonNull String funCodeDesc, @NonNull String funCodeType, @NonNull String funCodeBatchId) {
        new Thread(() ->
                onPublicFunCodeUse(roleInfo, funCode, funCodeDesc, funCodeType, funCodeBatchId)
        ).start();
    }

    @Override
    public void onEventImpl(
            RoleInfo roleInfo,
            String eventId,
            String eventDesc,
            int eventVal,
            String eventBody) {
        new Thread(() ->
                onEvent(roleInfo, eventId, eventDesc, eventVal, eventBody)
        ).start();
    }

}
