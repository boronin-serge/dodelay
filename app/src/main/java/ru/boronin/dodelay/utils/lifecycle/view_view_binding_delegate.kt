package ru.boronin.dodelay.utils.lifecycle

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.MainThread
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewViewBindingProperty<T : ViewBinding>(
  private val initializer: (LayoutInflater) -> T
) : ReadOnlyProperty<View, T> {
  internal var viewBinding: T? = null

  @MainThread
  override fun getValue(thisRef: View, property: KProperty<*>): T {
    this.viewBinding?.let { return it }
    return initializer(LayoutInflater.from(thisRef.context)).also { vb -> this.viewBinding = vb }
  }
}

inline fun <reified T : ViewBinding> View.viewBinding(
  noinline initializer: (LayoutInflater) -> T
): ReadOnlyProperty<View, T> = ViewViewBindingProperty(initializer)

inline fun <reified T : ViewBinding> View.viewBindingCall(
  noinline initializer: (LayoutInflater) -> T
) = initializer.invoke(LayoutInflater.from(context))
