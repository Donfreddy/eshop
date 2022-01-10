package com.big_graphics.eshop

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.startActivity

class MyWebViewClient : WebViewClient() {
  override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
    if (Uri.parse(url).host == "eshop.big-graphics.com") {
      return false
    }
    return true
  }
}