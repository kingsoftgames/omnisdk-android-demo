[<<返回首页](/sdk-docs)

OmniSDK KSSYOmniPlugin
=====

<!-- TOC -->

- [简介](#简介)
- [描述](#描述)
- [版本支持说明](#版本支持说明)
    - [版本支持](#版本支持)
    - [Java 接入](#java-接入)
    - [Kotlin 接入](#kotlin-接入)
    - [gradle.properties 配置](#gradleproperties-配置)
- [版本变更记录](#版本变更记录)
- [多渠道包编译](#多渠道包编译)
    - [渠道包的标识名](#渠道包的标识名)
    - [功能说明](#功能说明)
        - [多渠道出包配置参数](#多渠道出包配置参数)
        - [渠道包资源替换配置参数](#渠道包资源替换配置参数)
        - [resFile.json 说明](#resfilejson-说明)
        - [代码示例，详细参考下面的完整示例](#代码示例详细参考下面的完整示例)
- [CPS标签包编译](#cps标签包编译)
    - [功能说明](#功能说明-1)
        - [CPS标签包配置参数](#cps标签包配置参数)
        - [代码示例，详细参考下面的完整示例](#代码示例详细参考下面的完整示例-1)
- [如何配置](#如何配置)
    - [1. 多渠道目录创建](#1-多渠道目录创建)
    - [2. 编译配置示例](#2-编译配置示例)
    - [3. 编译指令示例](#3-编译指令示例)
    - [4. 多渠道编译Demo 示例](#4-多渠道编译demo-示例)
- [apksignerTest.sh 说明](#apksignertestsh-说明)
    - [原理](#原理)
    - [示例](#示例)
- [Android 标准目录参考](#android-标准目录参考)

<!-- /TOC -->

# 简介
`KSSYOmniPlugin` 基于 `Android-Gradle` 编译工具链开发的插件。直接集成于游戏工程源码内，方便游戏编译出包。

`KSSYOmniPlugin` 采用动态参数方式配置，游戏可以根据需求，通过动态传参编译需要的最终包。

# 描述
- 依赖配置：`com.kingsoft.shiyou.omnisdk.build:plugin:$latest_version`
- 编译配置文件名：kssyOmniPlugin.gradle
- Android Gradle Plugin 版本对应关系：[官网](https://developer.android.com/studio/releases/gradle-plugin)。
- 建议使用游戏引擎提供的最新版本，至少支持 `AGP 3.6.4 & Gradle 5.6.4`。
- 原则上，低于 `AGP 3.3.3 & Gradle 4.10.3` 不会兼容，主要原因是各渠道包及第三方SDK也有最低Gradle版本的编译要求。
- `KSSYOmniPlugin`插件兼容的技术支持，以`AGP`版本发布后3～4年内支持；超过时限不再支持，兼容会导致新特性无法使用、编译性能无法提升。

# 版本支持说明

## 版本支持

可 :left_right_arrow: 滑动

| AGP & Gradle   | Java | Kotlin | KSSYOmniPlugin | 技术支持截止日期 | 备注                                                         |
| -------------- | ---- | ------ | -------------- | ---------------- | ------------------------------------------------------------ |
| 3.3.3 & 4.10.3 | 支持 | 不支持 | 1.0.3 & 2.x    | 2022年12月       | 1. 除sunit(茄子渠道)外，需要在gradle.properties 里增加：<br/>`android.enableR8=true`<br/>`android.enableR8.fullMode=true`<br/>2. sunit(茄子渠道)配置：需要在gradle.properties 里增加：<br/>`android.enableR8=false`<br/>`android.enableR8.fullMode=true`<br/>并在 proguard-rules.pro 添加 `-ignorewarnings`<br/>3. 如果使用 `KSSYOmniPlugin 2.x`，需要对 sunit(茄子渠道) 单独配置。 |
| 3.4.3 & 5.3.1  | 支持 | 支持   | 1.0.3 & 2.x    | 2022年12月       | 1. 不需要特殊编译配置<br/>2. 注意 `kotlin` 版本限制，[Kotlin-Gradle Version](https://kotlinlang.org/docs/gradle.html) <br/>3. 目前测试`Kotlin 1.4.32 `及以下支持。 |
| 3.5.4 & 5.5.1  | 支持 | 支持   | 1.0.3 & 2.x    | 2022年12月       | 不需要特殊编译配置                                           |
| 3.6.4 & 5.6.4  | 支持 | 支持   | 1.0.3 & 2.x    | 目前长期维护     | 不需要特殊编译配置<br/>建议使用 2.x及后续版本                |
| 4.1.2 & 6.5.1  | 支持 | 支持   | 1.0.3 & 2.x    | 目前长期维护     | 不需要特殊编译配置<br/>建议使用 2.x及后续版本                |

## Java 接入
如果使用`Java`语言接入，兼容 `AGP 3.3.3 & Gradle 4.10.3` 以上。

## Kotlin 接入
如果使用`Kotlin`语言接入，根据 [Kotlin-Gradle Version](https://kotlinlang.org/docs/gradle.html)，兼容 `AGP 3.4.3 & Gradle 5.3.1` 以上。

因`Kotlin`支持限制，`Gradle`无法降级。注意使用的`Kotlin`版本，随着`Kotlin`的快速升级，`Gradle`的版本将越来越高。

目前测试 `Kotlin 1.4.32` 及以下支持`AGP 3.4.3 & Gradle 5.3.1` 以上版本；后续请注意`Kotlin`升级。

推荐使用 `Kotlin 1.4.32` 版本。1.4.x 系列最新稳定版本。

## gradle.properties 配置
```properties
# 建议增加编译内存大小
org.gradle.jvmargs=-Xmx4096m -Dfile.encoding=UTF-8
# 启用 androidx，必须配置
android.useAndroidX=true
# Automatically convert third-party libraries to use AndroidX
android.enableJetifier=true
```

# 版本变更记录

| 版本  | 更新内容                                                     |
| ----- | ------------------------------------------------------------ |
| 1.0.2 | 支持单个渠道包出包，已废弃                                   |
| 1.0.3 | 1. 支持单个渠道包出包<br/>2. 兼容 `AGP 3.3.3 & Gradle 4.10.3` 及以上 |
| 2.0.1 | 1. 增加多个渠道包出包功能<br/>2. 增加渠道包的CPS标签包出包功能<br/>3. 增加渠道包的资源文件编译时动态替换功能 |

# 多渠道包编译

## 渠道包的标识名
- 每个渠道包的标识名是由 Omni 定义好的，不可更改。
- 当前渠道包标识名，**可查看配置文件** `project_config.json#channel.channel_name`
- `/shiyou/**` 目录内的渠道文件夹名，必须与当前渠道包标识名一样。

## 功能说明

### 多渠道出包配置参数

| 参数名         | 功能                                    | 备注                                               |
| -------------- | --------------------------------------- | -------------------------------------------------- |
| debugBuild     | 出包类型：false-release包，true-debug包 | 不配置时，默认false                                |
| customChannels | 指定出哪些渠道包                        | 不配置时或"all"，出所有渠道包。                    |
| output         | 产物输出路径，必须是绝对路径            | 不配置时，输出到工程根目录"/channels-output/"下。  |
| signatureName  | 自定义签名脚本                          | 详细参考 [apksignerTest.sh](#apksignertestsh-说明) |

### 渠道包资源替换配置参数

| 参数名       | 功能               | 备注                                                   |
| ------------ | ------------------ | ------------------------------------------------------ |
| resChannels  | 功能域名           |                                                        |
| 渠道包标识名 | 需要资源替换的渠道 | `project_config.json#channel.channel_name`             |
| resDir       | 按目录copy         | 将按AS标准目录结构，循环copy到对应的app/src/main目录下 |
| resFile      | 按文件copy         | 将resFile里配置的文件`src` 拷贝到 `dst`                |

### resFile.json 说明
文件参考 [resFile.json](./res/resFile.json)

| 参数名 | 功能                                                   |
| ------ | ------------------------------------------------------ |
| src    | 源文件路径名，相对工程根目录的路径；以“/”开头。        |
| dst    | 目标路径，相对工程根目录的路径；以“/”开头，以“/”结尾。 |


```json
[
  {
    "src": "/shiyou/website_en/project_config-test.json",
    "dst": "/app/src/main/assets/"
  }
]
```

### 代码示例，详细参考下面的完整示例

```groovy
OmniSDK {
    debugBuild = "false"
    customChannels = "all"
    output = "${project.rootDir}/channels-output"
    signatureName = ""

    resChannels {
        website_en {
            resDir = "main"
            resFile = "resFile.json"
        }
    }
}
```

# CPS标签包编译

## 功能说明
### CPS标签包配置参数

| 参数名       | 功能                                           | 备注                |
| ------------ | ---------------------------------------------- | ------------------- |
| skipCps      | 是否跳过cps标签包编译，true-跳过，false-不跳过 | 不配置时，默认false |
| cpsChannels  | 功能域名                                       |                     |
| 渠道包标识名 | 需要出cps标签包的渠道                          | 配置哪些就出哪些。  |

### 代码示例，详细参考下面的完整示例
文件参考 [cps](./res/cps)

```groovy
OmniSDK {
    skipCps = "false"

    cpsChannels {
        website_en
        vivo
    }
}
```

# 如何配置

## 1. 多渠道目录创建
- 按[接入文档-配置Gradle](OmniSDKAndroid接入文档.md#2-配置gradle)做好接入工作准备；
- 在工程根目录下创建 `/shiyou/` 文件夹；
- 根据游戏需要出的渠道包，在 `/shiyou/` 目录下使用[渠道包的标识名](#渠道包的标识名)创建对应的文件夹；
- 将各自渠道的文件：`kssyOmni.gradle`(必配)、`kssyOmniRoot.gradle`(必配)、`project_config.json`(必配)、`cps`(如果有)、`替换资源`(如果有)，放置在各自的 “渠道包的标识名” 文件夹下。
- 使用默认配置（即出所有渠道包和cps标签包）或在 `kssyOmniPlugin.gradle` 自定义编译配置。

## 2. 编译配置示例
文件名：kssyOmniPlugin.gradle

```groovy
apply plugin: "KSSYOmniPlugin"

OmniSDK {

    // 出包类型：false-release包，true-debug包。
    // 建议屏蔽其他功能出debug包： ./gradlew -PisDebug=true -PchannelNames=website_en,samsung -PskipCpsBuild=true OmniChannelsBuild

    // 这个 hasProperty-"isDebug" 可以自定义，但要配合"-P"修改；比如改成 "doDebug"，则 "-PdoDebug=true"。下面同理，作用就是传值给gradlew使用。
    if (project.hasProperty("isDebug")) {
        debugBuild = isDebug
    } else {
        debugBuild = "false"
    }

    // 不配置时，默认出所有渠道包
    if (project.hasProperty("channelNames")) {
        customChannels = channelNames
    } else {
        // 为 all 或空时，即出所有渠道包；指定出哪些渠道包，用英文","分割；例："huawei,website_en"。
        // 注意：渠道包的标识名是由Omni定好的，不能自己更改。
        customChannels = "all" // "huawei,website_en"
    }

    // 产物输出路径：必须是绝对路径（${project.rootDir} 表示当前根目录）
    output = "${project.rootDir}/channels-output"

    // 签名：如果在 buildTypes.release.signingConfig 配置了签名，则不需要设置本字段；未配置，填写签名标识或脚本；
    //      如果这两处签名配置都未配置，打出的是未签名release包。
    // 两种签名方式，优先 buildTypes.release.signingConfig，只要配置 buildTypes.release.signingConfig，则 signatureName 签名就不会生效。
    // 签名标识调用的签名脚本：具体参考 apksignerTest.sh，自定义时请详细阅读文档说明。
//    signatureName = "${project.rootDir}/apksignerTest.sh"

    // 可替换资源
    resChannels {
        website_en {
            // 标准目录
            // -app/src/main/
            // -------------/assets
            // -------------/java
            // -------------/res
            // -------------/AndroidManifest.xml
            // shiyou/*channel*/main：配置目录，将按标准目录，循环copy到对应的app/src/main目录下。
            // 1. resDir 方式，研发必须保证需要copy的资源存放的目录与最终位置一一对应。适合需要大量资源替换，或编译前动态拉取资源。
            // 2. resFile 方式，研发直接配置需要替换的资源路径 [src-srcFilePath, dst-dstDirPath]，相对路径 [工程rootDir]。适合少量资源替换。
            // resFile 以json形式配置，具体结构参数示例文件。规范：都以“/”开头，dst以“/”结尾。
            // 两种方式可同时使用。执行顺序 resDir -> resFiles，即 resFiles 可以覆盖 resDir的资源操作。
            // 注意：不要用这个方法copy代码，除非这些代码是所有渠道共同代换的。脚本只是做覆盖功能，并不会进行删除操作。
            resDir = "main" // 路径不可修改，此文件名可自定义。
            resFile = "resFile.json" // 路径不可修改，此文件名可自定义。比如你可以改为 "aaResFile.json"
        }
      
        oppo {
           resDir = "main" // resFile也可以单独使用
        }
        vivo // 这样写是无效的
        vivo{} // 这样写是无效的
    }

    // 是否跳过cps标签包打包：true-跳过，false-出相应的标签包
    // 不配置时，默认值为 false
    if (project.hasProperty("skipCpsBuild")) {
        skipCps = skipCpsBuild
    } else {
        skipCps = "false"
    }

    // 仅更改标签包的标签包，标签包列表文件名“cps”：一行一个标签包，整个文件不要有空格行。比如 [cpsName,outAlias] 为一行
    // 标签包出包默认命名，channel-cps-cpsName.apk；如果配置了outAlias，则是 outAlias.apk
    // 标签包标签（cpsName）命名规则(建议)：英文、数字、下划线。

    // 不配置时，不出标签包；配置哪些渠道包的标识名，就出哪些渠道包的标签包。
    cpsChannels {
        website_en
        vivo
//        huawei
//        samsung
    }
}
```

## 3. 编译指令示例
- `KSSYOmniPlugin` 多渠道编译指令：`gradlew OmniChannelsBuild`

```bash
# Mac 环境
# 多渠道出包，默认release出包：所有渠道包、所有渠道包的CPS标签包。
./gradlew OmniChannelsBuild [your shell]
# 多渠道出包（debug包）
./gradlew -PisDebug=true OmniChannelsBuild [your shell]
# 指定渠道包出包（release包） 
./gradlew -PchannelNames=website_en,samsung OmniChannelsBuild [your shell]
# 跳过CPS标签包出包（release包） 
./gradlew -PskipCpsBuild=true OmniChannelsBuild [your shell]
```

## 4. 多渠道编译Demo 示例
参考[示例](https://github.com/kingsoftgames/omnisdk-android-demo/blob/master/kssyOmniPlugin.gradle)

# apksignerTest.sh 说明

## 原理
- 签名相关文档 https://developer.android.com/studio/command-line/apksigner
- 本签名脚本做为参考。具体如何实现签名脚本，由游戏自定义。只要通过下面的Java代码，验证签名成功即可。

```java
// Omni 调用签名脚本原理：Java
public static void main(String[] args) {
    try {
        String workspace = "build-dir";
        String signatureName = "signatureName";
        String apkNameBefore = "apkNameBefore";
        String apkNameAfter = "apkNameAfter";

        String docmd = signatureName + " " + apkNameBefore + " " + apkNameAfter;
        Runtime.getRuntime().exec(docmd, null, new File(workspace));
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

## 示例
文件参考 [apksignerTest.sh](./res/apksignerTest.sh)

# Android 标准目录参考

```groovy
// 主module，默认是app
-GameName
---app/libs 
---app/src/main/
---------------/assets
---------------/java
---------------/res
---------------/AndroidManifest.xml
---lib_module
---lib_module/libs
---lib_module/src/main/
----------------------/assets
----------------------/java
----------------------/res
----------------------/AndroidManifest.xml
---folder
---folder/lib_module
---folder/lib_module/libs
---folder/lib_module/src/main/
-----------------------------/assets
-----------------------------/java
-----------------------------/res
-----------------------------/AndroidManifest.xml
```