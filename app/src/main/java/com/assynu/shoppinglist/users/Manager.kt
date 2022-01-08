package com.assynu.shoppinglist.users

import android.content.Context
import java.util.*

object Manager {
    fun getUserId(context: Context?): String? {
        val sharedPreference = context?.getSharedPreferences("MainActivity", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()

        var uid = sharedPreference?.getString("UserID", "")
        if (uid == "" && editor != null) {
            uid = UUID.randomUUID().toString()
            editor.putString("UserID", uid)
            editor.apply()
        }
        return uid
    }

    fun setUserId(context: Context?, newUID: String) {
        val sharedPreference = context?.getSharedPreferences("MainActivity", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()

        if (editor != null) {
            editor.putString("UserID", newUID)
            editor.apply()
        }
    }
}