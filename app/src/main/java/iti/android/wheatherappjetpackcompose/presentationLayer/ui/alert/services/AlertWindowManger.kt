package iti.android.wheatherappjetpackcompose.presentationLayer.ui.alert.services

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.databinding.AlertWindowMangerBinding
import iti.android.wheatherappjetpackcompose.presentationLayer.MainActivity
import iti.android.wheatherappjetpackcompose.presentationLayer.utils.getIcon


class AlertWindowManger(
    private val alertService: AlertService,
    private val context: Context,
    private val description: String,
    private val icon: String,
) {

    private var windowManager: WindowManager? = null
    private var customNotificationDialogView: View? = null
    private var binding: AlertWindowMangerBinding? = null

    fun setMyWindowManger() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        customNotificationDialogView =
            inflater.inflate(R.layout.alert_window_manger, null)
        binding = AlertWindowMangerBinding.bind(customNotificationDialogView!!)
        bindView()
        val LAYOUT_FLAG: Int
        LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = (context.resources.displayMetrics.widthPixels * 0.85).toInt()
        val params = WindowManager.LayoutParams(
            width,
            WindowManager.LayoutParams.WRAP_CONTENT,
            LAYOUT_FLAG,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
            PixelFormat.TRANSLUCENT
        )


        windowManager!!.addView(customNotificationDialogView, params)
    }

    fun intentToApp() {
        val dialogIntent = Intent(context, MainActivity::class.java)
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        alertService.startActivity(dialogIntent)
    }

    private fun bindView() {
        binding?.imageIcon?.setImageResource(getIcon(icon))
        binding?.textDescription?.text = description
        binding?.btnOk?.text = context.getString(R.string.btn_ok)
        binding?.btnCancel?.text = context.getString(R.string.btn_cancel)
        val mediaPlayer = MediaPlayer.create(context, R.raw.oh_no)
        mediaPlayer.setOnCompletionListener {
            it.stop()
        }
        binding?.btnOk?.setOnClickListener {
            intentToApp()
            close()
            stopMyService()
            mediaPlayer.stop()
        }

        binding?.btnCancel?.setOnClickListener {
            close()
            stopMyService()
            mediaPlayer.stop()

        }


    }

    private fun close() {
        try {
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).removeView(
                customNotificationDialogView
            )
            customNotificationDialogView!!.invalidate()
            (customNotificationDialogView!!.parent as ViewGroup).removeAllViews()
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }

    private fun stopMyService() {
        context.stopService(Intent(context, AlertService::class.java))
    }
}