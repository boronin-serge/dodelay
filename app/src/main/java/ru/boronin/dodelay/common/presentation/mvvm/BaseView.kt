package ru.boronin.dodelay.common.presentation.mvvm

import android.os.Bundle
import android.view.View

abstract class BaseView : BaseFragment() {

  private var isFirstViewAttach = true

  abstract fun onViewBound(view: View)

  open fun onFirstViewAttached() {}

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    onViewBound(view)

    if (isFirstViewAttach) {
      onFirstViewAttached()
    }

    isFirstViewAttach = false
  }
}
