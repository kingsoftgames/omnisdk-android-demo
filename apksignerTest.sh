#!/bin/bash
##!/usr/bin/env bash
#Format:utf-8
#Author:omni

# 自定义时请详细阅读文档说明。

# 签名相关文档 https://developer.android.com/studio/command-line/apksigner

#    Omni 调用签名脚本原理：Java
#    public static void main(String[] args) {
#        try {
#            String workspace = "build-dir";
#            String signatureName = "signatureName";
#            String apkNameBefore = "apkNameBefore";
#            String apkNameAfter = "apkNameAfter";
#
#            String docmd = signatureName + " " + apkNameBefore + " " + apkNameAfter;
#            Runtime.getRuntime().exec(docmd, null, new File(workspace));
#        } catch (IOException e) {
#            e.printStackTrace();
#        }
#    }

# 本签名脚本做为参考。具体如何实现签名脚本，由游戏自定义。只要通过上述注释掉的Java代码，验证签名成功即可。

# 注1，signatureName 配置的是脚本路径或脚本标识。
# 注2，apkNameBefore、apkNameAfter 是脚本必须支持，但无需传递的配置。由Omni内部处理这两个参数值。
# 注3，脚本必须实现 `workspace`下的 mv ${apkNameBefore} ${apkNameAfter} 功能。

# 示例，cmd: ./apksignerTest.sh app-release-unsigned.apk app-release-signed.apk

apksigner=/Users/**/Library/Android/sdk/build-tools/29.0.3/apksigner
storeFile=/Users/**/**/config/store-file
keyAlias=**
keyPassword=**

apkNameBefore=${1}
apkNameAfter=${2}

# 签名具体指令
echo "${keyPassword}" | ${apksigner} sign --ks ${storeFile} --ks-key-alias ${keyAlias} ${apkNameBefore}

# 文件重命名：这是因为 apksigner 签名后并没有改变文件或相关配置
mv ${apkNameBefore} ${apkNameAfter}

# 验证 APK 签名（简单的验证方法，是否可以在手机上安装成功，release包未签名是无法安装成功）
# 示例：${apksigner} verify -v --print-certs ${apkNameAfter}

#参数:
#    -v, --verbose 显示详情(显示是否使用V1和V2签名)
#    --print-certs 显示签名证书信息
#例如:
#    Verifies
#    Verified using v1 scheme (JAR signing): true
#    Verified using v2 scheme (APK Signature Scheme v2): true
#    Verified using v3 scheme (APK Signature Scheme v3): true
#    Number of signers: 1
#    ... (再往下的打印就是详细信息)
