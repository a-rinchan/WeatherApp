package com.github.a_rin.weatherapp

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.github.a_rin.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*

/*
 * タイトル : MainActivity
 * 説明 : 現在地情報と取得した天気をViewに表示する
 *
 * @author : 吉澤彩花
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private lateinit var mainViewModelFactory: MainViewModelFactory

    ///位置情報を取得するためのパーミッションリクエストコード
    private val PERMISSIONS_REQUEST_CODE: Int = 100

    ///位置情報取得用のクラス
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var location: Location? = null

    ///現在位置の緯度と経度
    private var latitude: Double = 35.6895
    private var longitude: Double = 139.6917

    //初回起動関連
    private lateinit var preference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val locationRequest = LocationRequest()
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)

        //プリファレンスの準備
        preference = getSharedPreferences("Preference Name", MODE_PRIVATE)
        editor = preference.edit()


        if (preference.getBoolean("Launched", false) == false) {
            //初回起動時の処理
            confirmPermission()

            //プリファレンスの書き変え
            editor.putBoolean("Launched", true)
            editor.commit()
        } else {
            //二回目以降の処理
            getLastLocation()
        }

        mainViewModelFactory = MainViewModelFactory(application, latitude, longitude)
        viewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)

        // DataBindingのViewModelを設定
        binding.mainViewModel = viewModel

        binding.lifecycleOwner = this

        inputButton.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.permission_alert),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                getLastLocation()
            }
        }
    }

    /**
     *  @Description 端末の位置情報のパーミッションが許可されているか確認
     */
    private fun confirmPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            //位置情報取得が許可されている時
            getLastLocation()
        } else {
            //位置情報取得が許可されていない時
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                PERMISSIONS_REQUEST_CODE
            )
        }
    }

    /**
     *  @Description 端末が最後に取得した位置情報を取得
     */
    private fun getLastLocation() {
        fusedLocationClient?.lastLocation?.addOnCompleteListener(
            this
        ) { task ->
            if (task.isSuccessful && task.result != null) {
                location = task.result

                latitude = location?.latitude ?: 35.6895
                longitude = location?.latitude ?: 139.6917
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.failed_getlocation),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getWeather()
    }
}