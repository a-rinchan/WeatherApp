package com.github.a_rin.weatherapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
 * タイトル : MainViewModelFactory
 * 説明 : MainViewModelのFactoryクラス
 *
 * @author : 吉澤彩花
 */
class MainViewModelFactory(private val application: Application, private val lat: Double, private val lon: Double): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(application,lat,lon) as T
        throw IllegalArgumentException("Unknown MainViewModel class")
    }
}