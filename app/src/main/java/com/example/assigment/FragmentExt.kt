package com.example.assigment

import android.content.Context
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import kotlin.math.roundToInt

fun Fragment.goBackFragment(): Boolean {
    activity?.supportFragmentManager?.notNull {
        val isShowPreviousPage = it.backStackEntryCount > 0
        if (isShowPreviousPage) {
            it.popBackStackImmediate()
        }
        return isShowPreviousPage
    }
    return false
}
inline fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}
fun Context.dpToPx(dp: Int): Int {
    val displayMetrics = resources.displayMetrics
    return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}