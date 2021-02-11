package ru.boronin.common.navigation.jetpack

import ru.boronin.core.api.navigator.JetpackNavigator
import ru.boronin.core.api.navigator.JetpackNavigatorHandler
import ru.boronin.core.api.navigator.Navigator
import ru.boronin.core.api.navigator.NavigatorHandler

open class AppJetpackNavigator : JetpackNavigator {
  override var localHandler: JetpackNavigatorHandler? = null
  override var globalHandler: JetpackNavigatorHandler? = null
}
