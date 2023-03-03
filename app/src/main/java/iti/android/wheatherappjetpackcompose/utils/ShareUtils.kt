package iti.android.wheatherappjetpackcompose.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri

class ShareUtils {
    companion object {
        const val SMS_SHARE = 0
        fun sendSMS(smsBody: String?, tel: String, activity: Activity) {
            val uri: Uri = Uri.parse("smsto:$tel")
            Intent(Intent.ACTION_SENDTO, uri).apply {
                putExtra("sms_body", smsBody)
                activity.startActivity(this)
            }
        }

    }
}