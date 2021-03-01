package ru.boronin.dodelay.common.presentation.mvvm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.boronin.common.navigation.AppNavigator
import ru.boronin.common.plugins.loading.LoadingUIDelegatePlugin
import ru.boronin.common.plugins.loading.LoadingUIDelegatePluginImpl
import ru.boronin.common.plugins.popup.PopupUIDelegatePlugin
import ru.boronin.common.plugins.popup.PopupUIDelegatePluginImpl
import ru.boronin.core.android.view.delegate.UIDelegatePlugin
import ru.boronin.core.android.view.delegate.UIDelegatePluginEvent
import ru.boronin.dodelay.R
import ru.boronin.dodelay.common.presentation.plugins.NavigationUIDelegatePlugin
import ru.boronin.dodelay.common.presentation.plugins.NavigationUIDelegatePluginImpl

private const val DEFAULT_LAYOUT = R.layout.base_bottom_sheet_dialog_fragment

abstract class BaseBottomSheetDialogFragment(
  private val navigationPlugin: NavigationUIDelegatePluginImpl = NavigationUIDelegatePluginImpl(
    hostFragmentId = R.id.navFragment,
    rootHostFragmentId = R.id.navFragment,
  ),
  private val popupPlugin: PopupUIDelegatePluginImpl = PopupUIDelegatePluginImpl(),
  private val loadingPlugin: LoadingUIDelegatePluginImpl = LoadingUIDelegatePluginImpl(
    R.id.vgLoading
  )
) : BottomSheetDialogFragment(),
  PopupUIDelegatePlugin by popupPlugin,
  NavigationUIDelegatePlugin by navigationPlugin,
  LoadingUIDelegatePlugin by loadingPlugin {

  private val plugins = mutableListOf<UIDelegatePlugin<Fragment>>()

  protected abstract val binding: ViewBinding

  protected val subscriptions = AutoDisposable()

  protected val navigator = DodelayNavigator()

  init {
    initUIDelegatePlugins()
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)

    subscriptions.bindTo(this)

    sendUIDelegatePluginEvent(UIDelegatePluginEvent.OnAttach)
  }

  override fun onDetach() {
    super.onDetach()

    sendUIDelegatePluginEvent(UIDelegatePluginEvent.OnDetach)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(DEFAULT_LAYOUT, container, false).apply {
    val userView = binding.root
    findViewById<ViewGroup>(R.id.container).addView(userView)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    bindNavigator(navigator as AppNavigator)

    sendUIDelegatePluginEvent(UIDelegatePluginEvent.OnViewBound(view))

    subscriptions.clear() // Lifecycle doesn't have onViewCreated state
  }

  override fun onDestroy() {
    super.onDestroy()

    sendUIDelegatePluginEvent(UIDelegatePluginEvent.Release)
  }

  // region Private

  private fun sendUIDelegatePluginEvent(event: UIDelegatePluginEvent) {
    plugins.forEach { it.onUIDelegatePluginEvent(event) }
  }

  private fun initUIDelegatePlugins() {
    addUIDelegatePlugin(navigationPlugin)
    addUIDelegatePlugin(loadingPlugin)
    addUIDelegatePlugin(popupPlugin)
  }

  private fun addUIDelegatePlugin(plugin: UIDelegatePlugin<Fragment>) {
    with(plugins) {
      plugin.target = this@BaseBottomSheetDialogFragment
      add(plugin)
    }
  }

  // endregion
}
