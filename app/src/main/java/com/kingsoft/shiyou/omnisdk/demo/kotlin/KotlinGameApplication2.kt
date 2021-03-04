package com.kingsoft.shiyou.omnisdk.demo.kotlin

import com.kingsoft.shiyou.omnisdk.project.OmniApplication

/**
 * 若游戏应用已经有自身定义的Application并继承自android.app.Application,
 * 则CP对接方只需将继承类改为 com.kingsoft.shiyou.omnisdk.project.OmniApplication即可
 */
class KotlinGameApplication2 : OmniApplication() {

    //...

}