package ru.boronin.core.api.navigator

interface Navigator {
  var localHandler: NavigatorHandler?
  var childHandler: NavigatorHandler?
  var globalHandler: NavigatorHandler?
}
