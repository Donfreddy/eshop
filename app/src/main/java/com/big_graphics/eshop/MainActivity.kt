package com.big_graphics.eshop

import android.annotation.SuppressLint
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
  private lateinit var internetLayout: RelativeLayout
  private lateinit var noInternetLayout: ConstraintLayout
  private lateinit var swipeRefreshLayout: SwipeRefreshLayout
  private lateinit var webView: WebView
  private lateinit var webSetting: WebSettings
  private lateinit var internetErrorDialog: Dialog


  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Init variables
    internetLayout = findViewById(R.id.internetLayout)
    noInternetLayout = findViewById(R.id.noInternetLayout)
    swipeRefreshLayout = findViewById(R.id.swipe_refresh)
    webView = findViewById(R.id.web_view)
    webSetting = webView.settings
    internetErrorDialog = Dialog(this)

    // Web View settings
    webSetting.javaScriptEnabled = true
    webSetting.cacheMode
    webSetting.setSupportZoom(true)
    webSetting.loadsImagesAutomatically = true
    webSetting.setSupportMultipleWindows(true)

    // Use custom webClient and wevChromeClient
    webView.webViewClient = MyWebViewClient(swipeRefreshLayout)
    webView.webChromeClient = MyWebChromeClient(swipeRefreshLayout)

    // Check connection state before load url
    // if no connection we show internet error dialog
    if (hasConnection(this)) {
      webView.loadUrl("https://dribbble.com")
      // webView.loadUrl("https://eShop.big-graphics.com")
    } else run {
//      internetErrorDialog.window?.requestFeature(Window.FEATURE_ACTION_BAR)
//      internetErrorDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//      internetErrorDialog.setCancelable(false)
//      internetErrorDialog.setContentView(R.layout.internet_error_message_layout)
//      internetErrorDialog.show()
    }

    // Set an on refresh listener for swipe refresh layout
    swipeRefreshLayout.setOnRefreshListener {
      println("############################################## Refresh done")
      webView.reload()
      drawLayout()

      swipeRefreshLayout.isRefreshing = false
    }
    drawLayout()
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
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)

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

  private fun drawLayout() {
    if (hasConnection(this)) {
      internetLayout.visibility = VISIBLE
      noInternetLayout.visibility = GONE
    } else {
      noInternetLayout.visibility = VISIBLE
      internetLayout.visibility = GONE
    }
  }
}