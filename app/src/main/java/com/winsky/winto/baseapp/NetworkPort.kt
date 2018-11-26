package com.winsky.winto.baseapp

import com.winsky.winto.base.util.SPUtil


object NetworkPort {
    //解密密钥
    val EncryptionKey = "win-sky"
    const val SP_KEY_IP = "NetworkPort_ip"
    val Ip: String
    val DEBUG = BuildConfig.LOG_DEBUG


    //
    //  * =================https正式服务器的接口===========================
    //  */


    val Ip_RELEASE = "https://app.win-sky.com.cn:9001"


    //
    // /**
    //  * ================= 阿里测试服务器的接口 ===========================

    val Ip_BETA = "http://wp.win-sky.com.cn:16903"



    /**
     * =================合肥测试服务器对外的接口===========================
     */
    ////////////
    val Ip_DEBUG = "http://hefei.win-sky.com.cn:2180"


    init {
        val url = SPUtil.getInstance().getString(SP_KEY_IP)
        Ip = if (url.isNullOrEmpty()) {
            BuildConfig.API_HOST
        } else {
            url!!
        }


    }
}
