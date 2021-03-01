package ru.boronin.dodelay.common.presentation.plugins

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import ru.boronin.common.navigation.AppNavigator
import ru.boronin.common.navigation.jetpack.JetpackNavHandlerImpl
import ru.boronin.common.utils.DEFAULT_INT
import ru.boronin.common.utils.delegate.weakReference
import ru.boronin.core.android.view.delegate.UIDelegatePlugin
import ru.boronin.core.android.view.delegate.UIDelegatePluginEvent

class NavigationUIDelegatePluginImpl(
  @IdRes private val hostFragmentId: Int,
  @IdRes private val rootHostFragmentId: Int,
) : UIDelegatePlugin<Fragment>(), NavigationUIDelegatePlugin {
  private var navigator by weakReference<AppNavigator>()

  override fun onUIDelegatePluginEvent(event: UIDelegatePluginEvent) {
    super.onUIDelegatePluginEvent(event)
    when (event) {
      UIDelegatePluginEvent.Release -> navigator = null
      else -> {}
    }
  }

  override fun bindNavigator(navigator: AppNavigator) {
    val rootFragment = target?.requireActivity()?.supportFragmentManager?.findFragmentById(
      rootHostFragmentId
    )
    val childFragment = target?.childFragmentManager?.findFragmentById(hostFragmentId)
    navigator.apply {
      globalHandler = JetpackNavHandlerImpl(rootFragment)
      localHandler = JetpackNavHandlerImpl(target)
      childHandler = JetpackNavHandlerImpl(childFragment)
    }

    this.navigator = navigator
  }

  override fun handleBackPress() {
    navigator?.run {
      val childStackSize = childHandler?.childStackSize() ?: DEFAULT_INT
      if (childStackSize < 1 || childHandler?.back() != true) {
        if (localHandler?.back() != true) {
          target?.requireActivity()?.moveTaskToBack(false)
        }
      }
    }
  }
}
