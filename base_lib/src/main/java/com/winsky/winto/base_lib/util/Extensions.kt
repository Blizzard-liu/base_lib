package cn.winsky.travel.airporttravel.utils

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


/**
 * <br/>================================================
 * <br/> @author：Blizzard-liu
 * <br/> @date：2018/7/18 10:35
 * <br/> @describe：拓展函数
 * <br/>================================================
 */
const val TAG = "Winsky"

/**
 * Extension method to get LayoutInflater
 */
inline val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)
/**
 * InflateLayout
 */
fun Context.inflateLayout(@LayoutRes layoutId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = false): View
        = LayoutInflater.from(this).inflate(layoutId, parent, attachToRoot)

/**
 * Extension method to get a new Intent for an Activity class
 */
inline fun <reified T : Any> Context.intent() = Intent(this, T::class.java)
/**
 * Create an intent for [T] and apply a lambda on it
 */
inline fun <reified T : Any> Context.intent(body: Intent.() -> Unit): Intent {
    val intent = Intent(this, T::class.java)
    intent.body()
    return intent
}
/**
 * Extension method to startActivity for Context.
 */
inline fun <reified T : Activity> Context?.startActivity() = this?.startActivity(Intent(this, T::class.java))
/**
 * Extension method to start Service for Context.
 */
inline fun <reified T : Service> Context?.startService() = this?.startService(Intent(this, T::class.java))
/**
 * Extension method to startActivity with Animation for Context.
 */
inline fun <reified T : Activity> Context.startActivityWithAnimation(enterResId: Int = 0, exitResId: Int = 0) {
    val intent = Intent(this, T::class.java)
    val bundle = ActivityOptionsCompat.makeCustomAnimation(this, enterResId, exitResId).toBundle()
    ContextCompat.startActivity(this, intent, bundle)
}
/**
 * Extension method to startActivity with Animation for Context.
 */
inline fun <reified T : Activity> Context.startActivityWithAnimation(enterResId: Int = 0, exitResId: Int = 0, intentBody: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.intentBody()
    val bundle = ActivityOptionsCompat.makeCustomAnimation(this, enterResId, exitResId).toBundle()
    ContextCompat.startActivity(this, intent, bundle)
}


/**
 * Extension method to show toast for Context.
 */
fun Context?.showToast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) = this?.let { Toast.makeText(it, text, duration).show() }
/**
 * Extension method to display toast text for Fragment.
 */
fun Fragment?.showToast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) = this?.let { activity.showToast(text, duration) }


/**
 * 格式化手机号
 */
fun Fragment.formatPhone(phone:String): String {
    return StringBuilder().append(phone.substring(0, 3)).append("****").append(phone.substring(7, 11)).toString()
}

fun Activity.formatPhone(phone:String): String {
    return StringBuilder().append(phone.substring(0, 3)).append("****").append(phone.substring(7, 11)).toString()
}

/**
 * 拨打客服电话
 */
//fun Activity.requestCallPhone() {
//    PermissionUtil().requestPermissions(this,object :PermissionUtil.OnGrantedListener{
//        override fun onGranted() {
//            AppUtils.tell(this@requestCallPhone, AppConfig.ServiceTel)
//        }
//
//    }, Permission.CALL_PHONE)
//
//}
//
//fun Activity.requestCallPhone(number: String) {
//    PermissionUtil().requestPermissions(this,object :PermissionUtil.OnGrantedListener{
//        override fun onGranted() {
//            AppUtils.tell(this@requestCallPhone, number)
//        }
//
//    }, Permission.CALL_PHONE)
//
//}
/**
 * Extension method to provide hide keyboard for [Activity].
 */
fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(Context
                .INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}


//fun <T> Observable<T>.io_main(): Observable<T> {
//    return subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .unsubscribeOn(Schedulers.io())
//}

/**
 * Extension method to provide simpler access to {@link ContextCompat#getColor(int)}.
 */
fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)

/**
 * Extension method to Get Color for resource for Context.
 */
fun Context.getColor(@ColorRes id: Int) = ContextCompat.getColor(this, id)
/**
 * Extension method to find a device width in pixels
 */
inline val Context.displayWidth: Int
    get() = resources.displayMetrics.widthPixels
/**
 * Extension method to find a device height in pixels
 */
inline val Context.displayHeight: Int
    get() = resources.displayMetrics.heightPixels



fun View.durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    if (minute <= 9) {
        if (second <= 9) {
            return "0${minute}' 0${second}''"
        } else {
            return "0${minute}' ${second}''"
        }
    } else {
        if (second <= 9) {
            return "${minute}' 0${second}''"
        } else {
            return "${minute}' ${second}''"
        }
    }
}


/**
 * 几天前  几小时前
 */
fun View.timePreFormat(time: Long): String {

    val now =System.currentTimeMillis()
    val pre = now - time//多久前


    val min = pre / 1000 / 60
    if (min<1){
        return "刚刚"
    }else if(min<60){
        return ""+min+"分钟前"
    }else if(min<60*24){
        return ""+min/60+"小时前"
    }else {
        return ""+min/60/24+"天前"
    }
}

fun Context.dataFormat(total: Long): String {
    val result: String
    val speedReal: Int = (total / (1024)).toInt()
    if (speedReal < 512) {
        result = speedReal.toString() + " KB"
    } else {
        val mSpeed = speedReal / 1024.0
        result = (Math.round(mSpeed * 100) / 100.0).toString() + " MB"
    }
    return result
}


fun Context.dp2px(dpValue: Float): Int {
    val scale = resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Context.sp2px(spValue: Float): Int {
    val scale = resources.displayMetrics.scaledDensity
    return (spValue * scale + 0.5f).toInt()
}

//////////////防止重复点击///////////////////////////////////////////
/***
 * 设置延迟时间的View扩展
 * @param delay Long 延迟时间，默认600毫秒
 * @return T
 */
fun <T : View> T.withTrigger(delay: Long = 600): T {
    triggerDelay = delay
    return this
}

/***
 * 点击事件的View扩展
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {

    if (clickEnable()) {
        block(it as T)
    }
}

/***
 * 带延迟过滤的点击事件View扩展
 * @param delay Long 延迟时间，默认600毫秒
 * @param block: (T) -> Unit 函数
 * @return Unit
 */
fun <T : View> T.clickWithTrigger(time: Long = 600, block: (T) -> Unit){
    triggerDelay = time
    setOnClickListener {
        if (clickEnable()) {
            block(it as T)
        }
    }
}

private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(1123460103) != null) getTag(1123460103) as Long else 0
    set(value) {
        setTag(1123460103, value)
    }

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(1123461123) != null) getTag(1123461123) as Long else -1
    set(value) {
        setTag(1123461123, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}
//////////////防止重复点击///////////////////////////////////////////
