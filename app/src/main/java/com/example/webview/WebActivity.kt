package com.example.webview

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class WebActivity : AppCompatActivity() {
    private val CAMERA_PERMISSION_REQUEST_CODE = 100

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val myWebView: WebView = findViewById(R.id.webView1)
        val webSettings: WebSettings = myWebView.settings
        webSettings.javaScriptEnabled = true

        // webChromeClient setting
        myWebView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                if (request.origin?.scheme == "https" || request.origin?.scheme == "http") {
                    if (request.resources.contains(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                        // 카메라 액세스 권한 요청을 처리
                        handleCameraPermissionRequest(request)
                    } else {
                        // 다른 권한 요청에 대한 처리 추가
                        // ex) 마이크, 파일 액세스 등
                    }
                } else {
                    super.onPermissionRequest(request)
                }
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없는 경우 권한 요청 팝업 띄우기
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            // 권한이 이미 있는 경우 커스텀 카메라 뷰 표시 등의 작업 수행
            showCameraPermissionGrantedPopup()
        }

        myWebView.loadUrl("https://webview-gamma.vercel.app/")
    }

    private fun handleCameraPermissionRequest(request: PermissionRequest) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            // 이미 권한이 부여된 경우
            request.grant(arrayOf(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
        } else {
            // 권한이 없는 경우 권한 요청 팝업 띄우기
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 사용자가 권한을 허용한 경우 처리할 작업 수행
                // 커스텀 카메라 뷰 표시 등의 작업 수행
            } else {
                // 사용자가 권한을 거부한 경우 처리할 작업 수행
                // 사용자에게 권한이 필요하다는 메시지를 표시하거나 다른 대안을 제공할 수 있음
            }
        }
    }

    private fun showCameraPermissionGrantedPopup() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("카메라 권한 허용")
        builder.setMessage("카메라 권한이 허용되었습니다.")
        builder.setPositiveButton("확인") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        builder.show()
    }
}

