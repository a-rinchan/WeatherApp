package com.github.a_rin.weatherapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application,lat: Double, lon: Double) : AndroidViewModel(application) {

    val weatherLatLonRepository = WeatherLatLonRepository(lat, lon, baseUrl(), apiKey())

    companion object {
        private const val KELVIN = -273.15
    }

    //都市名
    private val _location = MutableLiveData<String>()
    val location: LiveData<String>
        get() = _location

    //現在の天気
    private val _weatherCondition = MutableLiveData<String>()
    val weatherCondition: LiveData<String>
        get() = _weatherCondition

    //現在の天気アイコン
    private val _weatherIcon = MutableLiveData<WeatherMark>()
    val weatherIcon: LiveData<WeatherMark>
        get() = _weatherIcon

    //現在の気温
    private val _temp = MutableLiveData<Int>()
    val temp: LiveData<Int>
        get() = _temp

    //直近24時間の最高気温
    private val _highTemp = MutableLiveData<Int>()
    val highTemp: LiveData<Int>
        get() = _highTemp

    //直近24時間の最低気温
    private val _lowTemp = MutableLiveData<Int>()
    val lowTemp: LiveData<Int>
        get() = _lowTemp

    //0時間後の天気
    private val _later0 = MutableLiveData<WeatherMark>()
    val later0: LiveData<WeatherMark>
        get() = _later0

    private val _later0String = MutableLiveData<String>()
    val later0Sting: LiveData<String>
        get() = _later0String

    //3時間後の天気
    private val _later3 = MutableLiveData<WeatherMark>()
    val later3: LiveData<WeatherMark>
        get() = _later3

    private val _later3String = MutableLiveData<String>()
    val later3Sting: LiveData<String>
        get() = _later3String

    //6時間後の天気
    private val _later6 = MutableLiveData<WeatherMark>()
    val later6: LiveData<WeatherMark>
        get() = _later6

    private val _later6String = MutableLiveData<String>()
    val later6Sting: LiveData<String>
        get() = _later6String

    //9時間後の天気
    private val _later9 = MutableLiveData<WeatherMark>()
    val later9: LiveData<WeatherMark>
        get() = _later9

    private val _later9String = MutableLiveData<String>()
    val later9Sting: LiveData<String>
        get() = _later9String

    //12時間後の天気
    private val _later12 = MutableLiveData<WeatherMark>()
    val later12: LiveData<WeatherMark>
        get() = _later12

    private val _later12String = MutableLiveData<String>()
    val later12Sting: LiveData<String>
        get() = _later12String

    //15時間後の天気
    private val _later15 = MutableLiveData<WeatherMark>()
    val later15: LiveData<WeatherMark>
        get() = _later15

    private val _later15String = MutableLiveData<String>()
    val later15Sting: LiveData<String>
        get() = _later15String

    //18時間後の天気
    private val _later18 = MutableLiveData<WeatherMark>()
    val later18: LiveData<WeatherMark>
        get() = _later18

    private val _later18String = MutableLiveData<String>()
    val later18Sting: LiveData<String>
        get() = _later18String

    //21時間後の天気
    private val _later21 = MutableLiveData<WeatherMark>()
    val later21: LiveData<WeatherMark>
        get() = _later21

    private val _later21String = MutableLiveData<String>()
    val later21Sting: LiveData<String>
        get() = _later21String

    //気圧
    private val _pressure = MutableLiveData<Int>()
    val pressure: LiveData<Int>
        get() = _pressure

    //湿度
    private val _humidity = MutableLiveData<Int>()
    val humidity: LiveData<Int>
        get() = _humidity

    //風速
    private val _wind = MutableLiveData<Int>()
    val wind: LiveData<Int>
        get() = _wind

    //取得日時の1日分の最高気温を格納するリスト
    var highList: MutableList<Double> = arrayListOf()

    //取得日時の1日分の最低気温を格納するリスト
    var lowList: MutableList<Double> = arrayListOf()

    init {
        _temp.postValue(0)
        _highTemp.postValue(0)
        _lowTemp.postValue(0)
        _pressure.postValue(0)
        _humidity.postValue(0)
        _wind.postValue(0)

    }


    fun getWeather() {
        weatherLatLonRepository.apiWeatherLatLonFetch()
            .subscribe() {
                _location.postValue(it.city.name)
                _weatherIcon.postValue(chooseIcon((it.list[2].weather[0].icon)))
                _weatherCondition.postValue(it.list[2].weather[0].main)
                _temp.postValue(preIndicateTemp(it.list[2].main.temp))

                for (i in 2..9) {
                    highList.add(it.list[i].main.temp_max)
                    lowList.add(it.list[i].main.temp_min)
                }

                _highTemp.postValue(preIndicateTemp(highList.max()))
                _lowTemp.postValue(preIndicateTemp(lowList.min()))

                _pressure.postValue(it.list[2].main.pressure)
                _humidity.postValue(it.list[2].main.humidity)
                _wind.postValue(it.list[2].wind.speed.toInt())

                _later0.postValue(chooseIcon(it.list[2].weather[0].icon))
                _later0String.postValue(transformHour(it.list[2].dt_txt))

                _later3.postValue(chooseIcon(it.list[3].weather[0].icon))
                _later3String.postValue(transformHour(it.list[3].dt_txt))

                _later6.postValue(chooseIcon(it.list[4].weather[0].icon))
                _later6String.postValue(transformHour(it.list[4].dt_txt))

                _later9.postValue(chooseIcon(it.list[5].weather[0].icon))
                _later9String.postValue(transformHour(it.list[5].dt_txt))

                _later12.postValue(chooseIcon(it.list[6].weather[0].icon))
                _later12String.postValue(transformHour(it.list[6].dt_txt))

                _later15.postValue(chooseIcon(it.list[7].weather[0].icon))
                _later15String.postValue(transformHour(it.list[7].dt_txt))

                _later18.postValue(chooseIcon(it.list[8].weather[0].icon))
                _later18String.postValue(transformHour(it.list[8].dt_txt))

                _later21.postValue(chooseIcon(it.list[9].weather[0].icon))
                _later21String.postValue(transformHour(it.list[9].dt_txt))

            }
    }

    /**
     * @Description 単位がケルビンの気温をを、セルシウス度に変換し表示できるようする
     * @return セルシウス度で表される気温
     */
    fun preIndicateTemp(temp: Double?): Int? {
        return temp?.plus(KELVIN)?.toInt()
    }

    /**
     * @return OpenWeatherMapのURL
     */
    fun baseUrl(): String {
        return getApplication<Application>().resources.getString(R.string.base_url)
    }

    /**
     * @return APIキー
     */
    fun apiKey(): String {
        return getApplication<Application>().resources.getString(R.string.api_key)
    }

    /**
     * @Description APIからテキスト形式で返ってきた日時の時間の箇所だけに変形
     * @return 天気表示画面に描画するために変形した、2桁で表される時間
     */
    fun transformHour(date: String): String{
        return date.substring(11,13)
    }

    fun chooseIcon(icon: String): WeatherMark {
        return when (icon) {
            "01d" -> WeatherMark.TENKI_01
            "02d" -> WeatherMark.TENKI_02
            "03d" -> WeatherMark.TENKI_03
            "04d" -> WeatherMark.TENKI_04
            "09d" -> WeatherMark.TENKI_09
            "10d" -> WeatherMark.TENKI_10
            "11d" -> WeatherMark.TENKI_11
            "13d" -> WeatherMark.TENKI_13
            "50d" -> WeatherMark.TENKI_50
            else -> WeatherMark.TENKI_03
        }
    }
}

enum class WeatherMark {
    TENKI_01,
    TENKI_02,
    TENKI_03,
    TENKI_04,
    TENKI_09,
    TENKI_10,
    TENKI_11,
    TENKI_13,
    TENKI_50
}