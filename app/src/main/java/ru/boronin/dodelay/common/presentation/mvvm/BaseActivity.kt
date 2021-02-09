package ru.boronin.dodelay.common.presentation.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import ru.boronin.common.navigation.jetpack.JetpackNavHandlerImpl
import ru.boronin.core.api.navigator.JetpackNavigatorHandler
import ru.boronin.dodelay.R

abstract class BaseActivity : AppCompatActivity() {
  protected abstract val binding: ViewBinding

  val navigator: JetpackNavigatorHandler? by lazy {
    val fragment = supportFragmentManager.findFragmentById(R.id.navFragment)
    fragment?.let { JetpackNavHandlerImpl(it) }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }
}
