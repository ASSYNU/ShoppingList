package com.assynu.shoppinglist

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.assynu.shoppinglist.adds.Manager
import com.assynu.shoppinglist.users.Manager.getUserId
import com.assynu.shoppinglist.users.Manager.setUserId


class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == "UserID") {
            var newUID = sharedPreferences.getString("UserID", "")

            if (newUID == null || newUID == "") newUID = ""

            setUserId(context, newUID)

            if(newUID == "")
            {
                val editor = sharedPreferences.edit()
                editor.putString("UserID", getUserId(context))
                editor.apply()

                findNavController().navigate(R.id.action_settingsFragment_to_FirstFragment)
            }
        }
    }
}