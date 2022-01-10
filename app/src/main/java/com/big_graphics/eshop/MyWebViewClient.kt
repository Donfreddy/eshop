package com.big_graphics.eshop

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient


class MyWebViewClient : WebViewClient() {
  override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
    if (Uri.parse(url).host == "eshop.big-graphics.com") {
      return false
    }
    return true
  }
}