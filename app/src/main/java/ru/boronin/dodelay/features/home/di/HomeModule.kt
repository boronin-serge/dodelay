package ru.boronin.dodelay.features.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import ru.boronin.dodelay.features.home.navigator.HomeNavigator
import ru.boronin.dodelay.features.home.navigator.HomeNavigatorImpl

@InstallIn(FragmentComponent::class)
@Module
class HomeModule {

  @Provides
  fun provideNavigator(): HomeNavigator = HomeNavigatorImpl()
}
