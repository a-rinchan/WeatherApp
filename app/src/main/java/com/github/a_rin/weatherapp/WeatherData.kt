package com.github.a_rin.weatherapp

/*
 * タイトル : WeatherData
 * 説明 : APIを呼び出した時のレスポンスを格納するモデル
 *
 * @author 吉澤彩花
 *
 */

data class WeatherData(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Climate>,
    val message: Int
)

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Climate(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val rain: Rain,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
)

data class Clouds(
    val all: Int
)

data class Main(
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_kf: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Rain(
    val `3h`: Double
)

data class Sys(
    val pod: String
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Wind(
    val deg: Int,
    val speed: Double
)
