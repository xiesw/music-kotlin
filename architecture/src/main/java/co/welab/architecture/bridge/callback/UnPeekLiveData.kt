package co.welab.architecture.bridge.callback

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.NullPointerException


/**
 * Created by pain.xie on 2020/6/5
 */
class UnPeekLiveData<T> : MutableLiveData<T>() {

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        hook(observer)
    }

    private fun hook(observer: Observer<in T>) {
        val liveDataClass = LiveData::class.java
        val mObservers = liveDataClass.getDeclaredField("mObservers")
        mObservers.isAccessible = true

        val observers = mObservers.get(this)
        val observersClass = observers.javaClass

        val methodGet = observersClass.getDeclaredMethod("get", Any::class.java)
        methodGet.isAccessible = true

        val objectWrapperEntry = methodGet.invoke(observers, observer)
        var objectWrapper: Any? = Any()

        if (objectWrapperEntry is Map.Entry<*, *>) {
            objectWrapper = (objectWrapperEntry as Map.Entry<*, *>).value
        }

        if (objectWrapper == null) {
            throw NullPointerException("ObserverWrapper can not be null")
        }

        val wrapperClass = objectWrapper.javaClass.superclass

        val mLastVersion = wrapperClass?.getDeclaredField("mLastVersion")
        mLastVersion?.isAccessible = true

        val mVersion = liveDataClass.getDeclaredField("mVersion")
        mVersion.isAccessible = true
        val mV = mVersion.get(this)

        mLastVersion?.set(objectWrapper, mV)

        mObservers.isAccessible = false
        methodGet.isAccessible = false
        mLastVersion!!.isAccessible = false
        mVersion.isAccessible = false
    }
}