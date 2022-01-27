package com.assynu.shoppinglist.Fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
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

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        findPreference<Preference>("pref_version")?.summary = BuildConfig.VERSION_NAME
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        val key = preference!!.key

        if(key == "ShareUserID")
        {
            val uid = getUserId(context)
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "List id: $uid")
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
        Log.v("Settings Debug", key)
        if (key == "UserID") {
            var newUID = sharedPreferences.getString("UserID", "")

            Log.v("Settings Debug", "Not checked: $newUID")

            if (newUID == null || newUID == "") newUID = ""

            Log.v("Settings Debug", "Checked: $newUID")

            setUserId(context, newUID)

            if (newUID == "") {
                val editor = sharedPreferences.edit()
                editor.putString("UserID", getUserId(context))
                editor.apply()

                findNavController().navigate(R.id.action_settingsFragment_to_FirstFragment)
            }
        }
    }
}