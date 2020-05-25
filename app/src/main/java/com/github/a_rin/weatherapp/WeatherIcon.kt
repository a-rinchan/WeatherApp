package com.github.a_rin.weatherapp

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

/**
 * @Description 天気アイコンの種類を表すenumに従い、表示したいImageViewにアイコンを表示出来るようにするアダプター
 * @param view : 天気アイコンを表示するImageView, icon : 天気アイコンの種類を表すenum
 */
@BindingAdapter("app:weatherIcon")
fun weatherIcon(view: ImageView, icon: WeatherMark?) {

    when (icon) {
        WeatherMark.TENKI_01 -> {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.tenki01))
        }
        WeatherMark.TENKI_02 -> {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.tenki02))
        }
        WeatherMark.TENKI_03 -> {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.tenki03))
        }
        WeatherMark.TENKI_04 -> {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.tenki04))
        }
        WeatherMark.TENKI_09 -> {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.tenki09))
        }
        WeatherMark.TENKI_10 -> {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.tenki10))
        }
        WeatherMark.TENKI_11 -> {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.tenki11))
        }
        WeatherMark.TENKI_13 -> {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.tenki13))
        }
        WeatherMark.TENKI_50 -> {
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.tenki50))
        }
    }
}