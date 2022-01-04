package com.big_graphics.eshop

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.startActivity

class MyWebViewClient : WebViewClient() {

  override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
    // Your hostname
    val hostname: String = "www.dribbble.com"

    if (Uri.parse(url).host == hostname) {
      // This is my web site, so do not override; let my WebView load the page
      return false
    }
    // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
    // Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
    // startActivity( this)
    // }
    return true
  }

  override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
    super.onPageStarted(view, url, favicon)
  }

  override fun onPageFinished(view: WebView?, url: String?) {
    super.onPageFinished(view, url)
  }


}


