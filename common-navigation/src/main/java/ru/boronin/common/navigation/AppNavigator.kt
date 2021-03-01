package ru.boronin.common.navigation

import ru.boronin.core.api.navigator.Navigator
import ru.boronin.core.api.navigator.NavigatorHandler

/**
 * Created by Sergey Boronin on 26.12.2019.
 */
open class AppNavigator : Navigator {
  final override var localHandler: NavigatorHandler? = null
  final override var childHandler: NavigatorHandler? = null
  final override var globalHandler: NavigatorHandler? = null
}
