package ru.boronin.dodelay.common.presentation.mvvm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.boronin.common.rx.extension.rxPublishSubj
import ru.boronin.core.api.error.ViewModelErrorHandler

abstract class BaseViewModel : ViewModel(), LifecycleObserver, ViewModelErrorHandler {
  protected open val subscriptions = CompositeDisposable()

  private val _forceLogout = rxPublishSubj<Boolean>()
  private val _errorMessage = rxPublishSubj<String>()
  private val _errorToast = rxPublishSubj<String>()

  val forceLogout: Observable<Boolean> get() = _forceLogout
  val errorMessage: Observable<String> get() = _errorMessage
  val errorToast: Observable<String> get() = _errorToast

  override fun onCleared() {
    super.onCleared()
    subscriptions.clear()
  }

  override fun forceLogout() = _forceLogout.onNext(true)

  override fun errorMessage(text: String) = _errorMessage.onNext(text)

  override fun errorToast(text: String) = _errorToast.onNext(text)

  fun clearSubscriptions() = subscriptions.clear()

  infix fun CompositeDisposable.add(disposable: Disposable) {
    add(disposable)
  }
}
