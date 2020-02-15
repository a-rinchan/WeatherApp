package com.github.a_rin.weatherapp

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.schedulers.Schedulers

/*
 * タイトル : WeatherRepository
 * 説明 : 緯度経度を使ったURLで取得したJsonデータをパースする
 *
 * @author 吉澤彩花
 *
 */

/**
 * @Description json形式で受け取った値をパースし、以下のパラメータを与えて実行スレッドを指定する
 * @param lat : 現在位置の緯度, lon : 現在位置の経度, baseUrl :　リクエスト先のURL, apiKey : OpenWeatherMapのAPIKey
 * @return APIから受け取った値を処理する実行スレッド
 */
class WeatherLatLonRepository(
    private val lat: Double,
    private val lon: Double,
    private val baseUrl: String,
    private val apiKey: String
) {
    companion object {
        private val TAG = GetWeatherApi::class.java
    }

    /**
     * @Description 緯度経度を使ったURLで取得したJsonデータをパース、実行スレッドの指定
     */
    fun apiWeatherLatLonFetch(): Observable<WeatherData> {

        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
        val apiClient = retrofit.create(TAG)
        return apiClient.getWeatherWithLatLon(lat, lon, apiKey).subscribeOn(Schedulers.io())
    }
}
