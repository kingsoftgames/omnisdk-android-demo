package com.kingsoft.shiyou.omnisdk.demo.v3.java;

import android.app.Activity;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.kingsoft.shiyou.omnisdk.api.entity.PayInfo;
import com.kingsoft.shiyou.omnisdk.api.entity.RoleInfo;
import com.kingsoft.shiyou.omnisdk.api.v3.OmniSDKv3;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.OmniSDKRoleInfo;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.events.OmniSDKCreateRoleEvent;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.events.OmniSDKEnterGameEvent;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.events.OmniSDKPurchaseEvent;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.events.OmniSDKRevenueEvent;
import com.kingsoft.shiyou.omnisdk.api.v3.entity.events.OmniSDKRoleLevelUpEvent;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IDataMonitorApi;
import com.kingsoft.shiyou.omnisdk.demo.v3.common.interfaces.IDataMonitorCallback;

import org.jetbrains.annotations.NotNull;

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

    public OmniSDKRoleInfo toRoleInfo(RoleInfo roleInfo) {
        return OmniSDKRoleInfo.builder()
                .userId(roleInfo.getUid())
                .roleId(roleInfo.getRoleId())
                .roleLevel(roleInfo.getRoleLevel())
                .roleName(roleInfo.getRoleName())
                .serverId(roleInfo.getServerId())
                .serverName(roleInfo.getServerName())
                .build();
    }

    /**
     * 监控追踪角色创建事件接口示例
     *
     * @param roleInfo 玩家角色信息
     * @see RoleInfo
     */
    public void onCreateRole(RoleInfo roleInfo) {
        OmniSDKCreateRoleEvent event = new OmniSDKCreateRoleEvent(toRoleInfo(roleInfo));
        OmniSDKv3.getInstance().track(event);

        callback.onSucceeded("Monitor(Tracking) `onCreateRole` Is Done");
    }

    /**
     * 监控追踪角色进入游戏事件接口示例
     *
     * @param roleInfo 玩家角色信息
     * @see RoleInfo
     */
    public void onEnterGame(RoleInfo roleInfo) {
        OmniSDKEnterGameEvent event = new OmniSDKEnterGameEvent(toRoleInfo(roleInfo));
        OmniSDKv3.getInstance().track(event);

        callback.onSucceeded("Monitor(Tracking) `onEnterGame` Is Done");
    }

    /**
     * 监控追踪角色等级升级事件接口示例
     *
     * @param roleInfo 玩家角色信息
     * @see RoleInfo
     */
    public void onRoleLevelUp(RoleInfo roleInfo) {
        OmniSDKRoleLevelUpEvent event = new OmniSDKRoleLevelUpEvent(toRoleInfo(roleInfo));
        OmniSDKv3.getInstance().track(event);

        callback.onSucceeded("Monitor(Tracking) `onRoleLevelUp` Is Done");
    }

    /**
     * 用户购买支付成功的时候调用此接口
     *
     * @param payInfo 支付相关数据
     * @see PayInfo
     */
    public void onPayFinish(PayInfo payInfo) {
        OmniSDKPurchaseEvent event = OmniSDKPurchaseEvent.builder()
                .userId(payInfo.getUid())
                .orderId(payInfo.getPlatTradeNo())
                .productId(payInfo.getProductId())
                .productDesc(payInfo.getProductDesc())
                .productUnitPrice(payInfo.getProductUnitPrice())
                .productName(payInfo.getProductName())
                .currency(payInfo.getCurrencyName())
                .gameServerId(payInfo.getServerId())
                .gameRoleVipLevel(payInfo.getRoleVipLevel())
                .gameRoleLevel(payInfo.getRoleLevel())
                .gameRoleName(payInfo.getRoleName())
                .gameRoleId(payInfo.getRoleId())
                .gameOrderId(payInfo.getGameTradeNo())
                .extJson(payInfo.getAdditionalParams())
                .purchaseQuantity(payInfo.getProductQuantity())
                .purchaseAmount(payInfo.getPayAmount())
                .build();

        OmniSDKv3.getInstance().track(event);

        callback.onSucceeded("Monitor(Tracking) `onPayFinish` Is Done");
    }

    /* ****************************************************************************************** */

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
    public void onPayFinishImpl(@NonNull PayInfo payInfo) {
        new Thread(() ->
                onPayFinish(payInfo)
        ).start();
    }

    @Override
    public void dataConsumeEvent(@NotNull RoleInfo roleInfo, int consumeNum) {
        OmniSDKRevenueEvent event = OmniSDKRevenueEvent.builder()
                .roleInfo(toRoleInfo(roleInfo))
                .consumeNum(consumeNum)
                .build();
        OmniSDKv3.getInstance().track(event);
    }
}
