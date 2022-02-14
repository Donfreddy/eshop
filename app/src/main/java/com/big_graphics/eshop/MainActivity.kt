package com.big_graphics.eshop

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.WebSettings
import android.webkit.WebView
import com.airbnb.lottie.LottieAnimationView

class MainActivity : AppCompatActivity() {
  private lateinit var webView: WebView
  private lateinit var noInternetView: LottieAnimationView
  private lateinit var webSetting: WebSettings

  private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
      val notConnected = intent.getBooleanExtra(
        ConnectivityManager
          .EXTRA_NO_CONNECTIVITY, false
      )
      if (notConnected) {
        disconnected()
      } else {
        connected()
      }
    }
  }

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    supportActionBar?.hide()

    webView = findViewById(R.id.web_view)
    noInternetView = findViewById(R.id.lottie)
    webSetting = webView.settings

    // Web View settings
    webSetting.javaScriptEnabled = true
    webSetting.setAppCacheEnabled(true)
    webSetting.setSupportZoom(true)
    webSetting.loadsImagesAutomatically = true
    webSetting.setSupportMultipleWindows(true)

    webView.webViewClient = MyWebViewClient()
  }

  override fun onStart() {
    super.onStart()
    registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
  }

  override fun onStop() {
    super.onStop()
    unregisterReceiver(broadcastReceiver)
  }

  override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
    // Check if the key event was the Back button and if there's history
    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
      webView.goBack()
      return true
    }
    return super.onKeyDown(keyCode, event)
  }

  private fun disconnected() {
    webView.visibility = GONE
    noInternetView.visibility = VISIBLE
  }

  private fun connected() {
    webView.visibility = VISIBLE
    noInternetView.visibility = GONE
    webView.loadUrl(Utils.URL)
  }
}