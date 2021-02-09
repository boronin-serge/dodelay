package ru.boronin.dodelay.utils.lifecycle

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityViewBindingProperty<T : ViewBinding>(
  private val initializer: (LayoutInflater) -> T
) : ReadOnlyProperty<AppCompatActivity, T> {
  internal var viewBinding: T? = null
  private val lifecycleObserver = BindingLifecycleObserver()

  @MainThread
  override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
    this.viewBinding?.let { return it }

    val lifecycle = thisRef.lifecycle
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

inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding(
  noinline initializer: (LayoutInflater) -> T
): ReadOnlyProperty<AppCompatActivity, T> = ActivityViewBindingProperty(initializer)
