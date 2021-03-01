package ru.boronin.dodelay.common.presentation.plugins

import ru.boronin.common.navigation.AppNavigator

interface NavigationUIDelegatePlugin {
  fun bindNavigator(navigator: AppNavigator)
  fun handleBackPress()
}
