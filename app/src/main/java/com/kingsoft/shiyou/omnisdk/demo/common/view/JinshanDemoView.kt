package com.kingsoft.shiyou.omnisdk.demo.common.view

import android.content.Context
import android.util.AttributeSet
import com.kingsoft.shiyou.omnisdk.api.OmniSDK
import com.kingsoft.shiyou.omnisdk.api.callback.ResponseCallback
import com.kingsoft.shiyou.omnisdk.demo.R
import com.kingsoft.shiyou.omnisdk.demo.common.utils.DemoLogger

/**
 * Description:
 *
 * @author: LuXing created on 2022/1/21 15:36
 *
 */
class JinshanDemoView : DemoView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context, attributeSet, defStyleAttr
    )

    override fun initView() {

        R.id.jinshan_demo_view_btn1.addClickListener {
            OmniSDK.instance.invokeMethod("updateUserInfo", context)
        }
        R.id.jinshan_demo_view_btn2.addClickListener {
            OmniSDK.instance.invokeMethod("accountDelete", context)
        }
        R.id.jinshan_demo_view_btn3.addClickListener {
            OmniSDK.instance.invokeMethod("openAccountCenter", context)
        }
        R.id.jinshan_demo_view_btn4.addClickListener {
            OmniSDK.instance.invokeMethod("deleteAccount", context, object : ResponseCallback {
                override fun onSuccess() {
                    appView.showToastMessage("deleteAccount success")
                    DemoLogger.d("deleteAccount onSuccess")
                }

                override fun onFailure(responseCode: Pair<Int, String>) {
                    appView.showToastMessage(responseCode.second)
                    DemoLogger.d("deleteAccount onError:sdkCode:${responseCode.first},msg:${responseCode.second}")
                }
            })
        }

        R.id.jinshan_demo_view_btn5.addClickListener {
            OmniSDK.instance.invokeMethod("restoreAccount", context, object : ResponseCallback {
                override fun onSuccess() {
                    appView.showToastMessage("restoreAccount success")
                    DemoLogger.d("restoreAccount onSuccess")
                }

                override fun onFailure(responseCode: Pair<Int, String>) {
                    appView.showToastMessage(responseCode.second)
                    DemoLogger.d("restoreAccount onError:sdkCode:${responseCode.first},msg:${responseCode.second}")
                }
            })
        }

        R.id.jinshan_demo_view_btn6.addClickListener {
            OmniSDK.instance.invokeMethod("deleteAccountWithUI", context, object : ResponseCallback {
                override fun onSuccess() {
                    appView.showToastMessage("deleteAccountWithUI success")
                    DemoLogger.d("deleteAccountWithUI onSuccess")
                }

                override fun onFailure(responseCode: Pair<Int, String>) {
                    appView.showToastMessage(responseCode.second)
                    DemoLogger.d("deleteAccountWithUI onError:sdkCode:${responseCode.first},msg:${responseCode.second}")
                }
            })
        }

        R.id.jinshan_demo_view_btn7.addClickListener {
            OmniSDK.instance.invokeMethod("restoreAccountWithUI", context, object : ResponseCallback {
                override fun onSuccess() {
                    appView.showToastMessage("restoreAccountWithUI success")
                    DemoLogger.d("restoreAccountWithUI onSuccess")
                }

                override fun onFailure(responseCode: Pair<Int, String>) {
                    appView.showToastMessage(responseCode.second)
                    DemoLogger.d("restoreAccountWithUI onError:sdkCode:${responseCode.first},msg:${responseCode.second}")
                }
            })
        }


    }


}