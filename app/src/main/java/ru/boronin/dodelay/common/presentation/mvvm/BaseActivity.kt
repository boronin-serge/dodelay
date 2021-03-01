package ru.boronin.dodelay.common.presentation.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import ru.boronin.dodelay.R

abstract class BaseActivity : AppCompatActivity() {
  protected abstract val binding: ViewBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }
}
