package com.github.a_rin.weatherapp

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    ///位置情報を取得するためのパーミッションリクエストコード
    private val PERMISSIONS_REQUEST_CODE: Int = 100

    ///位置情報取得用のクラス
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var location: Location? = null

    ///現在位置の緯度と経度
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val locationRequest = LocationRequest()
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)

        confirmPermission()

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

                latitude = location?.latitude
                longitude = location?.latitude
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.failed_getlocation),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}