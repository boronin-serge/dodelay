package ru.boronin.dodelay.common.presentation.mvvm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import ru.boronin.common.navigation.AppNavigator
import ru.boronin.common.plugins.alert.AlertUIDelegatePlugin
import ru.boronin.common.plugins.alert.AlertUIDelegatePluginImpl
import ru.boronin.common.plugins.backbutton.BackButtonUIDelegatePlugin
import ru.boronin.common.plugins.backbutton.BackButtonUIDelegatePluginImpl
import ru.boronin.common.plugins.loading.LoadingUIDelegatePlugin
import ru.boronin.common.plugins.loading.LoadingUIDelegatePluginImpl
import ru.boronin.common.plugins.title.TitleUIDelegatePlugin
import ru.boronin.common.plugins.title.TitleUIDelegatePluginImpl
import ru.boronin.core.android.view.delegate.UIDelegatePlugin
import ru.boronin.core.android.view.delegate.UIDelegatePluginEvent
import ru.boronin.dodelay.R
import ru.boronin.dodelay.common.presentation.plugins.NavigationUIDelegatePlugin
import ru.boronin.dodelay.common.presentation.plugins.NavigationUIDelegatePluginImpl

private const val DEFAULT_LAYOUT = R.layout.base_fragment

abstract class BaseFragment(
  private val navigationPlugin: NavigationUIDelegatePluginImpl = NavigationUIDelegatePluginImpl(
    hostFragmentId = R.id.navFragment,
    rootHostFragmentId = R.id.navFragment,
  ),
  private val alertPlugin: AlertUIDelegatePluginImpl = AlertUIDelegatePluginImpl(
    R.style.AlertDialogTheme
  ),
  private val titlePlugin: TitleUIDelegatePluginImpl = TitleUIDelegatePluginImpl(
    R.id.tvTitle
  ),
  private val backButtonPlugin: BackButtonUIDelegatePluginImpl = BackButtonUIDelegatePluginImpl(
    R.id.btn_back
  ),
  private val loadingPlugin: LoadingUIDelegatePluginImpl = LoadingUIDelegatePluginImpl(
    R.id.vgLoading
  )
) : Fragment(),
  AlertUIDelegatePlugin by alertPlugin,
  TitleUIDelegatePlugin by titlePlugin,
  NavigationUIDelegatePlugin by navigationPlugin,
  LoadingUIDelegatePlugin by loadingPlugin,
  BackButtonUIDelegatePlugin by backButtonPlugin {

  private val plugins = mutableListOf<UIDelegatePlugin<Fragment>>()

  protected val subscriptions = AutoDisposable()

  protected abstract val binding: ViewBinding

  protected val navigator = DodelayNavigator()

  override fun onAttach(context: Context) {
    super.onAttach(context)

    subscriptions.bindTo(this)

    sendUIDelegatePluginEvent(UIDelegatePluginEvent.OnAttach)
  }

  override fun onDetach() {
    super.onDetach()

    sendUIDelegatePluginEvent(UIDelegatePluginEvent.OnDetach)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    plugins.clear()
    initUIDelegatePlugins()
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
    sendUIDelegatePluginEvent(UIDelegatePluginEvent.OnViewBound(view))
    bindNavigator(navigator as AppNavigator)
    subscriptions.clear()
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
    addUIDelegatePlugin(alertPlugin)
    addUIDelegatePlugin(backButtonPlugin)
    addUIDelegatePlugin(titlePlugin)
    addUIDelegatePlugin(loadingPlugin)
    addUIDelegatePlugin(navigationPlugin)
  }

  private fun addUIDelegatePlugin(plugin: UIDelegatePlugin<Fragment>) {
    with(plugins) {
      plugin.target = this@BaseFragment
      add(plugin)
    }
  }

  // endregion
}
