package ru.boronin.core.api.navigator

interface JetpackNavigator {
  var localHandler: JetpackNavigatorHandler?
  var globalHandler: JetpackNavigatorHandler?
}
