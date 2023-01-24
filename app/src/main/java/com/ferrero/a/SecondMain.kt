package com.ferrero.a

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ferrero.a.util.Util
import com.ferrero.a.util.Util.codeCode
import com.ferrero.a.util.Util.urlMain
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.core.qualifier.named

class SecondMain : Fragment() {
    val viewMainModel by activityViewModel<ViMod>(named("MainModel"))
    lateinit var countryDev: String
    lateinit var wv: String
    lateinit var apps: String
    private lateinit var mContext: Context

    val shareP: SharedPreferences by inject(named("SharedPreferences"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_main, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewMainModel.mainId.observe(viewLifecycleOwner) {
            if (it != null) {
                val main = it.toString()
                shareP.edit().putString("mainId", main).apply()
            }
        }

        viewMainModel.geo.observe(viewLifecycleOwner) {
            if (it != null) {

                countryDev = it.geo
                apps = it.appsChecker
                wv = it.view

                shareP.edit().putString(codeCode, countryDev).apply()
                shareP.edit().putString(Util.apps, apps).apply()
                shareP.edit().putString(urlMain, wv).apply()

                findNavController().navigate(R.id.action_secondMainFragment_to_beforeFilter)
            }
        }
    }
}