@file:Suppress("unused", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection"
)

package com.assynu.shoppinglist.database

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentActivity
import com.assynu.shoppinglist.Product
import com.assynu.shoppinglist.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


@SuppressLint("StaticFieldLeak")
internal object Manager {
    private val db = Firebase.firestore

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

    fun addProduct(Name: String, ListID: String) {
        val product = hashMapOf(
            "Purchased" to false,
            "Name" to Name,
        )
        db.collection("Lists").document(ListID).collection("List")
            .add(product)
    }

    private fun removeProduct(document: String, ListID: String) {
        db.collection("Lists").document(ListID).collection("List").document(document)
            .delete()
    }

    fun removeCompleted(ListID: String) {
        db.collection("Lists").document(ListID).collection("List")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data.toList().toTypedArray()
                    val product =
                        Product(document.id, data[0].second as Boolean, data[1].second as String)
                    if (product.Purchased) {
                        removeProduct(document.id, ListID)
                    }
                }
            }
    }

    fun getProducts(
        activity: FragmentActivity?,
        products_list: LinearLayout,
        context: Context,
        ListID: String
    ) {

        db.collection("Lists").document(ListID).collection("List")
            .get()
            .addOnSuccessListener { result ->
                products_list.removeAllViews()
                for (document in result) {
                    val data = document.data.toList().toTypedArray()
                    val product =
                        Product(document.id, data[0].second as Boolean, data[1].second as String)

                    val productView = CheckBox(activity)

                    val paddingVal = 12.dpToPixelsInt(context)


                    productView.textSize = 18f
                    productView.text = product.Name
                    productView.setBackgroundResource(R.drawable.productbutton)
                    productView.setTextColor(R.attr.colorOnSecondary)
                    productView.buttonTintList = ColorStateList.valueOf(R.attr.colorOnSecondary)

                    productView.isChecked = product.Purchased
                    productView.setPadding(0, paddingVal,0,paddingVal)

                    productView.layoutParams = RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)

                    productView.setOnClickListener()
                    {
                        completeProduct(product, ListID)
                    }

                    products_list.addView(productView)
                }
            }
    }

    private fun Int.dpToPixelsInt(context: Context):Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),context.resources.displayMetrics
    ).toInt()

    private fun completeProduct(product: Product, ListID: String) {
        product.Purchased = !product.Purchased
        db.collection("Lists").document(ListID).collection("List").document(product.ID).set(product)
    }
}
