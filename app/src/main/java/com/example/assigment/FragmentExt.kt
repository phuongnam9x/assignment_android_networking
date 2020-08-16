package com.example.assigment

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
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
inline fun View.snack(
    message: String,
    length: SnackBarLength = SnackBarLength.SHORT,
    crossinline f: Snackbar.() -> Unit = {}
) = Snackbar.make(this, message, length.rawValue).apply {
    f()
    show()
}
enum class SnackBarLength(val rawValue: Int) {
    SHORT(Snackbar.LENGTH_SHORT),

    LONG(Snackbar.LENGTH_LONG),

    INDEFINITE(Snackbar.LENGTH_INDEFINITE);
}