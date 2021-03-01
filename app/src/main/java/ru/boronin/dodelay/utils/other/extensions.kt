package ru.boronin.dodelay.utils.other

import io.reactivex.Observable
import ru.boronin.common.rx.utils.SimpleDisposableObserver
import ru.boronin.dodelay.common.presentation.mvvm.AutoDisposable

fun <T> Observable<T>.listenWith(
  autoDisposable: AutoDisposable,
  onErrorFun: ((Throwable) -> Unit)? = null,
  onCompleteFun: (() -> Unit)? = null,
  onNextFun: ((T) -> Unit)? = null
) = subscribeWith(
  object : SimpleDisposableObserver<T>() {
    override fun onError(e: Throwable) {
      e.printStackTrace()
      if (autoDisposable.canEmitValue()) {
        e.printStackTrace()
        onErrorFun?.invoke(e)
      }
    }

    override fun onNext(data: T) {
      if (autoDisposable.canEmitValue()) {
        onNextFun?.invoke(data)
      }
    }

    override fun onComplete() {
      if (autoDisposable.canEmitValue()) {
        onCompleteFun?.invoke()
      }
    }
  }
).also { autoDisposable.add(it) }
