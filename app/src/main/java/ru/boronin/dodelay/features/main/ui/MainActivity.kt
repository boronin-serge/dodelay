package ru.boronin.dodelay.features.main.ui

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ru.boronin.dodelay.common.presentation.mvvm.BaseActivity
import ru.boronin.dodelay.databinding.ActivityMainBinding
import ru.boronin.dodelay.utils.lifecycle.viewBinding

@AndroidEntryPoint
class MainActivity : BaseActivity() {
  override val binding by viewBinding(ActivityMainBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }
}
