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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private lateinit var myWebView: WebView

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // 권한이 허용된 경우
                myWebView.reload()
            } else {
                // 권한이 거부된 경우 처리할 작업 수행
                showCameraPermissionDeniedAlert()

            }

        }
    private fun showCameraPermissionDeniedAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("카메라 권한 설정")
        builder.setMessage("카메라 권한에 접근할 수 없습니다. 설정 앱에서 카메라 권한을 확인해주세요.")
        builder.setPositiveButton("확인") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        builder.show()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myWebView = findViewById(R.id.webView)
        val webSettings: WebSettings = myWebView.settings
        webSettings.javaScriptEnabled = true

        // webChromeClient setting
        myWebView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                if (request.origin?.scheme == "https" || request.origin?.scheme == "http") {
                    if (request.resources.contains(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                        // 카메라 액세스 권한 요청을 처리
                        handleCameraPermissionRequest(request)
                    }
                } else {
                    super.onPermissionRequest(request)
                }
            }
        }

        myWebView.loadUrl("https://webview-gamma.vercel.app/")
    }

    private fun handleCameraPermissionRequest(request: PermissionRequest) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            // 이미 권한이 부여된 경우
            request.grant(arrayOf(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
        } else {
            // 권한이 없는 경우 권한 요청
            requestCameraPermission()
        }
    }

    private fun requestCameraPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    override fun onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack()
        } else {
            super.onBackPressed()
            finish()
        }
    }
}