package com.big_graphics.eshop

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.startActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MyWebViewClient(private val swipeRefreshLayout: SwipeRefreshLayout) : WebViewClient() {
  override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
    if (Uri.parse(url).host == "eshop.big-graphics.com") {
      return false
    }
    return true
  }

  override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
    super.onPageStarted(view, url, favicon)
    swipeRefreshLayout.isRefreshing = true
  }

  override fun onPageFinished(view: WebView?, url: String?) {
    super.onPageFinished(view, url)
    swipeRefreshLayout.isRefreshing = false
  }
}
