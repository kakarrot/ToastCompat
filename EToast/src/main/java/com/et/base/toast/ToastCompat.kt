package com.et.base.toast

import android.widget.Toast

import android.annotation.SuppressLint
import android.content.Context
import kotlin.Throws
import android.content.res.Resources.NotFoundException
import androidx.annotation.StringRes
import android.os.Build
import android.view.View

/**
 * @author drakeet
 */
class ToastCompat
/**
 * Construct an empty Toast object.  You must call [.setView] before you
 * can call [.show].
 *
 * @param context The context to use.  Usually your [Application]
 * or [Activity] object.
 * @param base The base toast
 */ private constructor(context: Context, private val toast: Toast) : Toast(context) {

    fun setBadTokenListener(listener: BadTokenListener): ToastCompat {
        val context = view?.context
        if (context is SafeToastContext) {
            context.setBadTokenListener(listener)
        }
        return this
    }

    override fun show() {
        toast.show()
    }

    override fun setDuration(duration: Int) {
        toast.duration = duration
    }

    override fun setGravity(gravity: Int, xOffset: Int, yOffset: Int) {
        toast.setGravity(gravity, xOffset, yOffset)
    }

    override fun setMargin(horizontalMargin: Float, verticalMargin: Float) {
        toast.setMargin(horizontalMargin, verticalMargin)
    }

    override fun setText(resId: Int) {
        toast.setText(resId)
    }

    override fun setText(s: CharSequence) {
        toast.setText(s)
    }

    override fun setView(view: View) {
        toast.view = view
        setContextCompat(view, SafeToastContext(view.context, this))
    }

    override fun getHorizontalMargin(): Float {
        return toast.horizontalMargin
    }

    override fun getVerticalMargin(): Float {
        return toast.verticalMargin
    }

    override fun getDuration(): Int {
        return toast.duration
    }

    override fun getGravity(): Int {
        return toast.gravity
    }

    override fun getXOffset(): Int {
        return toast.xOffset
    }

    override fun getYOffset(): Int {
        return toast.yOffset
    }

    override fun getView(): View? {
        return toast.view
    }

    companion object {
        /**
         * Make a standard toast that just contains a text view.
         *
         * @param context The context to use.  Usually your [Application]
         * or [Activity] object.
         * @param text The text to show.  Can be formatted text.
         * @param duration How long to display the message.  Either [.LENGTH_SHORT] or
         * [.LENGTH_LONG]
         */
        fun makeText(context: Context, text: CharSequence?, duration: Int): ToastCompat {
            // We cannot pass the SafeToastContext to Toast.makeText() because
            // the View will unwrap the base context and we are in vain.
            val toast = Toast.makeText(context, text, duration)
            toast.view?.let { view ->
                setContextCompat(view, SafeToastContext(context, toast))
            }
            return ToastCompat(context, toast)
        }

        /**
         * Make a standard toast that just contains a text view with the text from a resource.
         *
         * @param context The context to use.  Usually your [Application]
         * or [Activity] object.
         * @param resId The resource id of the string resource to use.  Can be formatted text.
         * @param duration How long to display the message.  Either [.LENGTH_SHORT] or
         * [.LENGTH_LONG]
         * @throws Resources.NotFoundException if the resource can't be found.
         */
        @Throws(NotFoundException::class)
        fun makeText(context: Context, @StringRes resId: Int, duration: Int): Toast {
            return makeText(context, context.resources.getText(resId), duration)
        }

        @SuppressLint("DiscouragedPrivateApi")
        private fun setContextCompat(view: View, context: Context) {
            if (Build.VERSION.SDK_INT == 25) {
                try {
                    val field = View::class.java.getDeclaredField("mContext")
                    field.isAccessible = true
                    field[view] = context
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                }
            }
        }
    }
}