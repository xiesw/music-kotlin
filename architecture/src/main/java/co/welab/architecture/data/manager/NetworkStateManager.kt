package co.welab.architecture.data.manager

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import co.welab.architecture.bridge.callback.UnPeekLiveData

/**
 * Created by pain.xie on 2020/6/5
 */
object NetworkStateManager: DefaultLifecycleObserver{
    val networkStateCallback = UnPeekLiveData<NetState>()
    lateinit var mNetworkStateReceive: NetworkStateReceive

    override fun onResume(owner: LifecycleOwner) {
        mNetworkStateReceive = NetworkStateReceive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        if (owner is AppCompatActivity) {
            owner.registerReceiver(mNetworkStateReceive, filter)
        } else if (owner is Fragment) {
            owner.activity?.registerReceiver(mNetworkStateReceive, filter)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        if (owner is AppCompatActivity) {
            owner.unregisterReceiver(mNetworkStateReceive)
        } else if (owner is Fragment) {
            owner.activity?.unregisterReceiver(mNetworkStateReceive)
        }
    }
}