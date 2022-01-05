package com.big_graphics.eshop

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
  private lateinit var swipeRefreshLayout: SwipeRefreshLayout
  private lateinit var webView: WebView
  private lateinit var webSetting: WebSettings


  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    swipeRefreshLayout = findViewById(R.id.swipe_refresh)
    webView = findViewById(R.id.web_view)
    webSetting = webView.settings

    // Web View settings
    webSetting.javaScriptEnabled = true
    webSetting.setAppCacheEnabled(true)
    webSetting.setSupportZoom(true)
    webSetting.loadsImagesAutomatically = true
    webSetting.setSupportMultipleWindows(true)

    webView.webViewClient = MyWebViewClient()
    webView.webChromeClient = MyWebChromeClient()

//    if(hasConnection(this)){
//      webView.loadUrl("https://dribbble.com")
//    } else run {
//      val internetErrorDialog = Dialog(this)
//
//      internetErrorDialog.window!!.requestFeature(Window.FEATURE_ACTION_BAR)
//      internetErrorDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//      internetErrorDialog.setCancelable(true)
//      // internetErrorDialog.setContentView()
//      internetErrorDialog.show()
//    }

    // webView.loadUrl("https://dribbble.com")
    webView.loadUrl("https://eShop.big-graphics.com")
    // webView.loadUrl("https://www.amazon.fr")
  }

  override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
    // Check if the key event was the Back button and if there's history
    if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
      webView.goBack()
      return true
    }
    return super.onKeyDown(keyCode, event)
  }

  private fun hasConnection(context: Context): Boolean {
    val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
      connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    if (capabilities != null) {
      when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
          Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
          return true
        }
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
          Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
          return true
        }
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
          Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
          return true
        }
      }
    }
    return false
  }
}