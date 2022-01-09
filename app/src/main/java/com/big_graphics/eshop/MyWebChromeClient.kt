package com.big_graphics.eshop

import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MyWebChromeClient(private val swipeRefreshLayout: SwipeRefreshLayout): WebChromeClient() {
  override fun onProgressChanged(view: WebView?, newProgress: Int) {
    super.onProgressChanged(view, newProgress)

    if (newProgress >= 50){
      swipeRefreshLayout.isRefreshing = false
    }
  }
}