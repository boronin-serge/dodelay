package ru.boronin.dodelay.features.home

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import ru.boronin.dodelay.common.presentation.mvvm.BaseView
import ru.boronin.dodelay.databinding.FragmentHomeBinding
import ru.boronin.dodelay.features.home.navigator.HomeNavigator
import ru.boronin.dodelay.utils.lifecycle.viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseView<HomeNavigator>() {
  override val binding by viewBinding(FragmentHomeBinding::inflate)

  // private val args: HomeFragmentArgs by navArgs()

  @Inject
  override lateinit var navigator:  HomeNavigator

  override fun onViewBound(view: View) {

  }

  // region private

  private fun initListeners() {

  }

  // endregion

}
