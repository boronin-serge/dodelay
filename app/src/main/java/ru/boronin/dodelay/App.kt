package ru.boronin.dodelay

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class App : Application() {

  override fun onCreate() {
    super.onCreate()
  }
}
