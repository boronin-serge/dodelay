package ru.boronin.dodelay.utils.lifecycle

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingProperty<T : ViewBinding>(
  private val initializer: (LayoutInflater) -> T
) : ReadOnlyProperty<Fragment, T> {
  internal var viewBinding: T? = null
  private val lifecycleObserver = BindingLifecycleObserver()

  @MainThread
  override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
    this.viewBinding?.let { return it }

    val lifecycle = thisRef.viewLifecycleOwner.lifecycle
    if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
      mainHandler.post {
        viewBinding = null
      }
    } else {
      lifecycle.addObserver(lifecycleObserver)
    }
    return initializer(thisRef.layoutInflater).also { vb -> this.viewBinding = vb }
  }

  private inner class BindingLifecycleObserver : DefaultLifecycleObserver {
    @MainThread
    override fun onDestroy(owner: LifecycleOwner) {
      owner.lifecycle.removeObserver(this)
      mainHandler.post {
        viewBinding = null
      }
    }
  }

  private companion object {
    private val mainHandler = Handler(Looper.getMainLooper())
  }
}

inline fun <reified T : ViewBinding> Fragment.viewBinding(
  noinline initializer: (LayoutInflater) -> T
): ReadOnlyProperty<Fragment, T> {
  return FragmentViewBindingProperty(initializer)
}
