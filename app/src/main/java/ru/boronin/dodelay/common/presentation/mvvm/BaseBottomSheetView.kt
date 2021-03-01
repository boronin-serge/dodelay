package ru.boronin.dodelay.common.presentation.mvvm

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import ru.boronin.dodelay.R

abstract class BaseBottomSheetView : BaseBottomSheetDialogFragment() {

  abstract fun onViewBound(view: View)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NORMAL, R.style.bottomSheetDialogStyle)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    onViewBound(view)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog {
    return object : BottomSheetDialog(requireContext(), theme) {
      override fun onBackPressed() {
        handleBackPress()
      }
    }
  }
}
