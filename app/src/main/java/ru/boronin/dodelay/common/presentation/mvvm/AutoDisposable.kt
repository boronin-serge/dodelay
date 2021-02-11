package ru.boronin.dodelay.common.presentation.mvvm

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.boronin.common.utils.DEFAULT_BOOLEAN
import ru.boronin.common.utils.delegate.weakReference

class AutoDisposable : LifecycleObserver {
  lateinit var compositeDisposable: CompositeDisposable

  private var fragment by weakReference<Fragment>()

  fun bindTo(baseFragment: Fragment) {
    fragment = baseFragment
    baseFragment.lifecycle.addObserver(this)
    compositeDisposable = CompositeDisposable()
  }

  fun add(disposable: Disposable) {
    if (::compositeDisposable.isInitialized) {
      compositeDisposable.add(disposable)
    } else {
      throw NotImplementedError("must bind AutoDisposable to a Lifecycle first")
    }
  }

  fun canEmitValue() = fragment?.isAdded ?: DEFAULT_BOOLEAN

  fun clear() {
    compositeDisposable.clear()
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
  fun onDestroy() {
    compositeDisposable.clear()
    fragment = null
  }
}
