package ru.boronin.dodelay.features.main.ui

import android.os.Bundle
import ru.boronin.dodelay.R
import ru.boronin.dodelay.common.presentation.mvvm.BaseActivity
import ru.boronin.dodelay.databinding.ActivityMainBinding
import ru.boronin.dodelay.utils.lifecycle.viewBinding

class MainActivity : BaseActivity() {
  override val binding by viewBinding(ActivityMainBinding::inflate)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}
