package com.assynu.shoppinglist.ads

import android.view.View
import com.assynu.shoppinglist.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

object Manager {
    lateinit var mAdView: AdView

    fun addConfiguration(view: View) {
        MobileAds.initialize(view.context) {}

        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
}