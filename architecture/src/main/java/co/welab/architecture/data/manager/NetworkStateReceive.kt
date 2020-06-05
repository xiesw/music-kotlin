package co.welab.architecture.data.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import co.welab.architecture.R
import co.welab.architecture.utils.NetworkUtils
import java.util.*

/**
 * Created by pain.xie on 2020/6/5
 */
class NetworkStateReceive : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Objects.equals(intent?.action, ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (!NetworkUtils.isConnected()) {
                Toast.makeText(context, R.string.network_not_good, Toast.LENGTH_LONG).show()
            }
        }
    }
}