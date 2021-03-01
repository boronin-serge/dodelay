package ru.boronin.common.navigation.jetpack

import ru.boronin.core.api.navigator.JetpackNavigator
import ru.boronin.core.api.navigator.JetpackNavigatorHandler

open class AppJetpackNavigator : JetpackNavigator {
  override var localHandler: JetpackNavigatorHandler? = null
  override var globalHandler: JetpackNavigatorHandler? = null
}
