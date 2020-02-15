package com.github.a_rin.weatherapp

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/*
 * タイトル: GetWeatherApi
 * 説明 : APIインターフェース
 *
 * @author 吉澤彩花
 *
 */

/**
 * @Description 天気情報を取得するAPI
 */
interface GetWeatherApi {

    /**
     * @Description 緯度経度を基に、現在から5日分の天気情報を取得する。
     * @param lat 緯度
     * @param lon 経度
     * @param APPID APIKey
     * @return 取得時から5日分の天気情報
     */
    @GET("data/2.5/forecast")
    fun getWeatherWithLatLon(
        @Query("lat") lat: Double, @Query("lon") lon: Double, @Query("APPID") APPID: String
    ): Observable<WeatherData>
}
