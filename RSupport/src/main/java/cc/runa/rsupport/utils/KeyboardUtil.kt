package cc.runa.rsupport.utils

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 软键盘控制工具类
 *
 * @author JokerCats
 */
object KeyboardUtil {

    /**
     * 检测当前焦点是否在 EditText 区域内
     */
    fun isHideKeyboard(view: View?, event: MotionEvent): Boolean {
        if (view != null && view is EditText) {
            val location = intArrayOf(0, 0)
            // 计算控件位置坐标
            val left = location[0]
            val top = location[1]
            val right = left + view.width
            val bottom = top + view.height
            // 判断点击是否在有效区域中,存在则返回false,否则返回true.
            val effective = (event.x > left && event.x < right && event.y > top && event.y < bottom)
            return !effective
        }
        return false
    }

    /**
     * 关闭软键盘
     */
    fun closeKBoard(ctx: Context, view: View?) {
        val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 切换软键盘
     */
    fun toggleKBoard(ctx: Context, view: EditText) {
        val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 点击空白处，隐藏输入框
     */
    fun hideSoftInput(activity: Activity, ev: MotionEvent) {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = activity.currentFocus
            if (isShouldHide(v, ev)) {
                doHide(activity, v!!.windowToken)
            }
        }
    }

    private fun isShouldHide(v: View?, event: MotionEvent): Boolean {
        // 这里是用常用的EditText作判断参照的，可根据情况替换成其它View
        if (v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            val b = event.x > left && event.x < right && event.y > top && event.y < bottom
            return !b
        }
        return false
    }

    private fun doHide(context: Context, token: IBinder?) {
        if (token != null) {
            val im = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}