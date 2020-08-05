package cc.runa.rsupport.widget.agent

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import cc.runa.rsupport.R

/**
 * Toast shown in the middle
 *
 *  @author JokerCats on 2020.03.02
 */
class CenterToast {

    private var toast: Toast
    private var textView: TextView

    constructor(context: Context?) : this(context, "")

    constructor(context: Context?, message: String) : this(context, message, Toast.LENGTH_SHORT)

    constructor(context: Context?, message: String, duration: Int) {
        toast = Toast(context)
        toast.duration = duration
        val view = View.inflate(context, R.layout.view_center_toast, null)
        textView = view.findViewById(R.id.tv_prompt)
        textView.text = message
        toast.view = view
        toast.setGravity(Gravity.CENTER, 0, 0)
    }

    fun updateTips(message: String): CenterToast {
        textView.text = message
        return this
    }

    fun launch() {
        toast.show()
    }

    fun intercept() {
        toast.cancel()
    }

}