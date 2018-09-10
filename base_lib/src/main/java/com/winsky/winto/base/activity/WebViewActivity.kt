package com.winsky.winto.base.activity

import android.annotation.TargetApi
import android.app.Activity
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import com.winsky.winto.base.R
import com.winsky.winto.base.widget.ProgressWebView
import kotlinx.android.synthetic.main.activity_webview.*
import org.jetbrains.anko.startActivity


/**
 * <br/>================================================
 * <br/> @author：Blizzard-liu
 * <br/> @date：2018/7/18 10:35
 * <br/> @describe：
 * <br/>================================================
 */
class WebViewActivity : BaseActivity(), View.OnClickListener {
    override val layoutResId: Int
        get() = R.layout.activity_webview

    companion object {
        private const val KEY_URL = "Url"
        private const val KEY_TITLE = "title"
        fun newInstance(context:Activity, url: String , title: String) {
            context.startActivity<WebViewActivity>(KEY_URL to url, KEY_TITLE to title)
        }
    }

    private val url by lazy { intent.getStringExtra(KEY_URL) }
    private val title by lazy { intent.getStringExtra(KEY_TITLE) }
    private lateinit var mWebView:WebView

    override fun initView(savedInstanceState: Bundle?) {
        setToolBarTitle(title)

    }

    override fun initData(savedInstanceState: Bundle?) {

        //添加webView到布局中
        addWebViewToLayout()

        //set webView Setting
        setWebView()

        //set webView Client
        setWebClient()

        //load web
        loadUrl()
    }

    override fun initListener(savedInstanceState: Bundle?) {

    }


    override fun onClick(v: View?) {
        when (v?.id) {


        }


    }

    /**
     * 1、不在xml中定义Webview，而是在需要的时候在Activity中创建
     * 使用getApplicationgContext()，避免内存泄漏。
     *
     *
     * 2、当然，你也可以配置webView所在Activity，
     * 在AndroidManifest中的进程为：android:process=":remote"
     * 避免泄漏影响主进程
     */
    private fun addWebViewToLayout() {

        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mWebView = ProgressWebView(applicationContext)
        mWebView.layoutParams = params
        root_layout.addView(mWebView)
    }


    /**
     * 配置webView
     */
    private fun setWebView() {
        //声明WebSettings子类
        val webSettings = mWebView.settings

        //支持Javascript交互
        webSettings.javaScriptEnabled = true


        //设置自适应屏幕，两者合用
        webSettings.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小
        webSettings.domStorageEnabled = true//设置适应HTML5的一些方法

        //缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.displayZoomControls = false //隐藏原生的缩放控件

        //其他细节操作
        //webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.allowFileAccess = true //设置可以访问文件

        //对于不需要使用 file 协议的应用，禁用 file 协议；防止文件泄密，file协议即是file://
        //webSettings.setAllowFileAccess(false);
        //webSettings.setAllowFileAccessFromFileURLs(false);
        //webSettings.setAllowUniversalAccessFromFileURLs(false);


        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        webSettings.loadsImagesAutomatically = true //支持自动加载图片
        webSettings.defaultTextEncodingName = "utf-8"//设置编码格式

        mWebView.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            //网页中触发下载动作
        })


    }

    /**
     * 设置webView的Client，如页面加载开始，错误，拦截请求，接受Error等
     */
    private fun setWebClient() {
        mWebView.webViewClient = object : WebViewClient() {

            //拦截页面中的url加载,21以下的
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }


            //页面加载每一个资源，如图片
            override fun onLoadResource(view: WebView, url: String) {
                super.onLoadResource(view, url)
            }

            //监听WebView发出的请求并做相应的处理
            //浏览器的渲染以及资源加载都是在一个线程中，如果在shouldInterceptRequest处理时间过长，WebView界面就会阻塞
            //21以下的
            override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
                return super.shouldInterceptRequest(view, url)
            }

            //监听WebView发出的请求并做相应的处理
            //浏览器的渲染以及资源加载都是在一个线程中，如果在shouldInterceptRequest处理时间过长，WebView界面就会阻塞
            //21以上的
            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
            }

            //页面加载出现错误,23以下的
            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                when (errorCode) {
                    404 -> {
                    }
                    else -> {
                    }
                }//view.loadUrl("加载一个错误页面提示，优化体验");
            }

            //页面加载出现错误,23以上的
            @TargetApi(23)
            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                when (error.errorCode) {
                    404 -> {
                    }
                    else -> {
                    }
                }//view.loadUrl("加载一个错误页面提示，优化体验");

            }

            //https错误
            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                //                super.onReceivedSslError(view, handler, error);
                //handler.cancel(); 默认的处理方式，WebView变成空白页
                //              //接受证书
                handler.proceed()
            }
        }
    }


    /**
     * 加载url
     */
    private fun loadUrl() {
        // 格式规定为:file:///android_asset/文件名.html
        //        mWebView.loadUrl("file:///android_asset/localHtml.html");
        //方式1. 加载远程网页：
        //mWebView.loadUrl("http://www.google.com/");
        //方式2：加载asset的html页面
        //mWebView.loadUrl("file:///android_asset/localHtml.html");
        //方式3：加载手机SD的html页面
        //mWebView.loadUrl("file:///mnt/sdcard/database/taobao.html");

        mWebView.loadUrl(url)

    }

    /**
     * 主动清空销毁来避免内存泄漏
     */
    override fun onDestroy() {
        mWebView.run {
            loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            clearHistory()
            (parent as ViewGroup).removeView(mWebView)
            stopLoading()
            webChromeClient = null
            webViewClient = null
            destroy()
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack()

        } else {
            super.onBackPressed()
        }
    }

}
