package ru.boronin.dodelay.common.presentation.mvvm

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import ru.boronin.common.navigation.jetpack.AppJetpackNavigator
import ru.boronin.common.navigation.jetpack.JetpackNavHandlerImpl
import ru.boronin.dodelay.R

private const val DEFAULT_LOCAL_NAV_HOST = R.id.navFragment

abstract class BaseView<N> : BaseFragment() {

  private var isFirstViewAttach = true

  private var backPressCallback: OnBackPressedCallback? = null

  protected abstract var navigator: N

  abstract fun onViewBound(view: View)

  open fun onFirstViewAttached() {}
  open fun handledBackPress() = false
  open fun getBaseViewModel(): BaseViewModel? {
    return null
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    backPressCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
      (navigator as AppJetpackNavigator).localHandler?.run {
        if (handledBackPress().not()) {
          requireActivity().moveTaskToBack(false)
        }
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initNavigator()

    onViewBound(view)

    if (isFirstViewAttach) {
      onFirstViewAttached()
    }

    isFirstViewAttach = false
  }

  override fun onDestroy() {
    super.onDestroy()
    backPressCallback?.remove()
    backPressCallback = null
  }

  // region private

  private fun initNavigator() {
    (navigator as AppJetpackNavigator).apply {
      globalHandler = (requireActivity() as BaseActivity).navigator
      val navFragment =
        childFragmentManager.findFragmentById(DEFAULT_LOCAL_NAV_HOST) ?: this@BaseView
      localHandler = JetpackNavHandlerImpl(navFragment)
    }
  }

  // endregion
}
