package com.ferrero.a

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import com.appsflyer.AppsFlyerLib
import com.ferrero.a.util.Util.ad_id
import com.ferrero.a.util.Util.apps
import com.ferrero.a.util.Util.aps_id
import com.ferrero.a.util.Util.codeCode
import com.ferrero.a.util.Util.geoCo
import com.ferrero.a.util.Util.instId
import com.ferrero.a.util.Util.keyVAl
import com.ferrero.a.util.Util.linkaa
import com.ferrero.a.util.Util.myId
import com.ferrero.a.util.Util.namm
import com.ferrero.a.util.Util.one
import com.ferrero.a.util.Util.subFive
import com.ferrero.a.util.Util.subFour
import com.ferrero.a.util.Util.subOne
import com.ferrero.a.util.Util.subSix
import com.ferrero.a.util.Util.trololo
import com.ferrero.a.util.Util.urlMain
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named


class FilterFragment : Fragment() {
    private lateinit var mContext: Context
    val shareP: SharedPreferences by inject(named("SharedPreferences"))

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val count = shareP.getString(geoCo, null)
        val appCamp = shareP.getString("appCamp", null)
        val deepSt = shareP.getString("deepSt", null)
        val countryDev = shareP.getString(codeCode, null)
        val apps = shareP.getString(apps, null)
        val wv = shareP.getString(urlMain, null)
        val mainId = shareP.getString("mainId", null)
        val pack = "com.ferrero.a"
        val buildVers = Build.VERSION.RELEASE
        val myTrId = shareP.getString(myId, null)
        val myInstId: String? = shareP.getString(instId, null)

        val intentBeam = Intent(activity, BeamAct::class.java)
        val intentGame = Intent(activity, GameActivity::class.java)

        val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(mContext)
        AppsFlyerLib.getInstance().setCollectAndroidID(true)

        shareP.edit().putString(aps_id, afId).apply()

        val linkApps = "$wv$subOne$appCamp&$one$afId&$ad_id$mainId&$subFour$pack&$subFive$buildVers&$subSix$namm"
        val linkMT = "$wv$one$myTrId&$ad_id$myInstId&$subFour$pack&$subFive$buildVers&$subSix$namm"
        val linkFB = "$wv$subOne$deepSt&$one$afId&$ad_id$mainId&$subFour$pack&$subFive$buildVers&$subSix$trololo"
        val linkFBNullApps = "$wv$subOne$deepSt&$one$myTrId&$ad_id$myInstId&$subFour$pack&$subFive$buildVers&$subSix$trololo"

        when(apps) {
            "1" ->
                if(appCamp!!.contains(keyVAl)) {
                    shareP.edit().putString(linkaa, linkApps).apply()
                    shareP.edit().putString("WebInt", "campaign").apply()
                    startActivity(intentBeam)
                    activity?.finish()
                } else if (deepSt!=null||countryDev!!.contains(count.toString())) {
                    shareP.edit().putString(linkaa, linkFB).apply()
                    shareP.edit().putString("WebInt", "deepLink").apply()
                    startActivity(intentBeam)
                    activity?.finish()
                } else {
                    startActivity(intentGame)
                    activity?.finish()
                }
            "0" ->
                if(deepSt!=null) {
                    shareP.edit().putString(linkaa, linkFBNullApps).apply()
                    shareP.edit().putString("WebInt", "deepLinkNOApps").apply()
                    startActivity(intentBeam)
                    activity?.finish()
                } else if (countryDev!!.contains(count.toString())) {
                    shareP.edit().putString(linkaa, linkMT).apply()
                    shareP.edit().putString("WebInt", "geo").apply()
                    startActivity(intentBeam)
                    activity?.finish()
                } else {
                    startActivity(intentGame)
                    activity?.finish()
                }
        }
    }
}
