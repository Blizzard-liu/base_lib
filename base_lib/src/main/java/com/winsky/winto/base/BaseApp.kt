package com.winsky.winto.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.winsky.winto.base.util.Density
import com.winsky.winto.base.widget.refreshheader.MaterialHeader
import org.jetbrains.anko.doAsync
import kotlin.properties.Delegates

/**
 * <br/>================================================
 * <br/> @author：Blizzard-liu
 * <br/> @date：2018/9/7 15:48
 * <br/> @describe：
 * <br/>================================================
 */

class BaseApp: Application(){
    companion object {
        var instance: Application by Delegates.notNull()//单例模式

        //static 代码段可以防止内存泄露
        init {
            ClassicsFooter.REFRESH_FOOTER_ALLLOADED = "我是有底线的"
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                //全局设置主题颜色
                //                layout.setPrimaryColorsId(R.color.refresh_bg_color);
                //                 内容不偏移:
                //                mRefreshLayout.setEnableHeaderTranslationContent(false);
                //                内容跟随偏移:
                //                mRefreshLayout.setEnableHeaderTranslationContent(true);
                //                打开背景: 关闭背景
                //                mMaterialHeader.setShowBezierWave(true);
                //设置主题
                //               mRefreshLayout.setPrimaryColorsId(colorPrimary, android.R.color.white);
                //取消内容不满一页时开启上拉加载功能
                layout.setEnableLoadMoreWhenContentNotFull(false)
                // 设置是否在全部加载结束之后Footer跟随内容
                layout.setEnableFooterFollowWhenLoadFinished(true)
                layout.isEnableAutoLoadMore = true
                MaterialHeader(context)
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                //指定为经典Footer，默认是 BallPulseFooter
                val classicsFooter = ClassicsFooter(context)
                classicsFooter.titleText.setTextColor(context.resources.getColor(R.color.FF9B9B9B))
                classicsFooter.setProgressResource(R.drawable.ic_progress_puzzle)
                classicsFooter.setDrawableSize(20f)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Density.setDensity(this)

        doAsync {
            //子线程初始化

        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


}