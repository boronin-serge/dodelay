package ru.boronin.dodelay.common.presentation.mvvm

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import ru.boronin.common.navigation.jetpack.AppJetpackNavigator
import ru.boronin.common.navigation.jetpack.JetpackNavHandlerImpl
import ru.boronin.dodelay.R

private const val DEFAULT_LOCAL_NAV_HOST = R.id.navFragment

abstract class BaseBottomSheetView<N> : BaseBottomSheetDialogFragment() {

  protected abstract var navigator: N

  abstract fun onViewBound(view: View)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NORMAL, R.style.bottomSheetDialogStyle)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initNavigator()

    onViewBound(view)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog {
    return object : BottomSheetDialog(requireContext(), theme) {
      override fun onBackPressed() {
        (navigator as AppJetpackNavigator).localHandler?.run {
          if (childStackSize() > 0) {
            back()
          } else {
            super.onBackPressed()
          }
        }
      }
    }
  }

  // region private

  private fun initNavigator() {
    (navigator as AppJetpackNavigator).apply {
      globalHandler = (requireActivity() as BaseActivity).navigator
      val navFragment =
        childFragmentManager.findFragmentById(DEFAULT_LOCAL_NAV_HOST) ?: this@BaseBottomSheetView
      localHandler = JetpackNavHandlerImpl(navFragment)
    }
  }

  // endregion
}
