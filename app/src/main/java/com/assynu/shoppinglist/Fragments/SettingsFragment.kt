package com.assynu.shoppinglist.Fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.assynu.shoppinglist.R
import com.assynu.shoppinglist.users.Manager.setUserId
import com.assynu.shoppinglist.BuildConfig
import com.assynu.shoppinglist.users.Manager.getUserId


class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findPreference<Preference>("pref_version")?.summary = BuildConfig.VERSION_NAME

//        findPreference<Preference>("ShareUserID")?.onPreferenceChangeListener = shareListId()

    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        val key = preference!!.key

        if(key == "ShareUserID")
        {
            val uid = getUserId(context)
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Someone shared you their shopping list id: " + uid)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        if(key == "pref_remove_ads")
        {
            Log.v("Shopping List", "Removed Ads")
        }

        return super.onPreferenceTreeClick(preference)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == "UserID") {
            var newUID = sharedPreferences.getString("UserID", "")

            if (newUID == null || newUID == "") newUID = ""

            setUserId(context, newUID)
        }
    }
}