package com.ferrero.a

import android.app.Application
import android.content.pm.PackageManager
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.lifecycle.ViewModel
import com.onesignal.OneSignal
import org.json.JSONException
import org.json.JSONObject

class BeamModel(application: Application): ViewModel() {
    val pm = application.packageManager
    fun webSet(webView: WebView): WebSettings{
       val webViewSet = webView.settings
        webViewSet.javaScriptEnabled = true
        webViewSet.useWideViewPort = true
        webViewSet.loadWithOverviewMode = true
        webViewSet.allowFileAccess = true
        webViewSet.domStorageEnabled = true
        webViewSet.userAgentString = webViewSet.userAgentString.replace("; wv", "")
        webViewSet.javaScriptCanOpenWindowsAutomatically = true
        webViewSet.setSupportMultipleWindows(false)
        webViewSet.displayZoomControls = false
        webViewSet.builtInZoomControls = true
        webViewSet.allowFileAccess = true
        webViewSet.allowContentAccess = true
        webViewSet.setSupportZoom(true)
        webViewSet.pluginState = WebSettings.PluginState.ON
        webViewSet.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webViewSet.cacheMode = WebSettings.LOAD_DEFAULT
        webViewSet.allowContentAccess = true
        webViewSet.mediaPlaybackRequiresUserGesture = false
        return webViewSet
    }
    fun pushToOS(id: String) {
        OneSignal.setExternalUserId(
            id,
            object : OneSignal.OSExternalUserIdUpdateCompletionHandler {
                override fun onSuccess(results: JSONObject) {
                    try {
                        if (results.has("push") && results.getJSONObject("push").has("success")) {
                            val isPushSuccess = results.getJSONObject("push").getBoolean("success")
                            OneSignal.onesignalLog(
                                OneSignal.LOG_LEVEL.VERBOSE,
                                "Set external user id for push status: $isPushSuccess"
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    try {
                        if (results.has("email") && results.getJSONObject("email").has("success")) {
                            val isEmailSuccess =
                                results.getJSONObject("email").getBoolean("success")
                            OneSignal.onesignalLog(
                                OneSignal.LOG_LEVEL.VERBOSE,
                                "Set external user id for email status: $isEmailSuccess"
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    try {
                        if (results.has("sms") && results.getJSONObject("sms").has("success")) {
                            val isSmsSuccess = results.getJSONObject("sms").getBoolean("success")
                            OneSignal.onesignalLog(
                                OneSignal.LOG_LEVEL.VERBOSE,
                                "Set external user id for sms status: $isSmsSuccess"
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(error: OneSignal.ExternalIdError) {
                    OneSignal.onesignalLog(
                        OneSignal.LOG_LEVEL.VERBOSE,
                        "Set external user id done with error: $error"
                    )
                }
            })
    }



    fun appInstalledOrNot(uri: String): Boolean {
        try {
            pm.getPackageInfo("org.telegram.messenger", PackageManager.GET_ACTIVITIES)
            return true
        } catch (_: PackageManager.NameNotFoundException) {

        }
        return false
    }
}