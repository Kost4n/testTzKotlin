package com.kost4n.testandroidapp

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowManager
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.kost4n.testandroidapp.databinding.ActivityInternetBinding

class InternetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInternetBinding
    private lateinit var webView: WebView
    private val FILE_NAME = "Link_Test_Android_App"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInternetBinding.inflate(layoutInflater)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(binding.root)

        webView = findViewById(R.id.webView2)

        val extras = intent.extras

        webView.webViewClient = WebViewClient()
        val webSettings = webView.settings
        webSettings.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            domStorageEnabled = true
            databaseEnabled = true
            setSupportZoom(true)
            allowFileAccess = true
            allowContentAccess = true
            javaScriptCanOpenWindowsAutomatically = true
        }
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)


        val url = extras?.getString("url")
        url?.let {
            saveLink(it)
            webView.loadUrl(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView.restoreState(savedInstanceState)
    }

    override fun onBackPressed() {
        if (webView.canGoBack())
            webView.goBack()
    }

    private fun saveLink(url: String) {
        applicationContext.openFileOutput(
            FILE_NAME, Context.MODE_PRIVATE
        ).use {
            it.write(url.toByteArray())
        }
    }
}

private class MyWebViewClient : WebViewClient() {

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        CookieManager.getInstance().flush()
    }

    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        view.loadUrl(request.url.toString())
        return true
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        view.loadUrl(url)
        return true
    }
}