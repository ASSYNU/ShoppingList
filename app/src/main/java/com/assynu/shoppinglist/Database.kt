@file:Suppress("unused", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection"
)

package com.assynu.shoppinglist

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.marginTop
import androidx.fragment.app.FragmentActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

internal object Database {
    @SuppressLint("StaticFieldLeak")
    private val db = Firebase.firestore

    private const val ListID = "4cccoGG7ELWjUMbwZ3sF" // Main
//    private const val ListID = "dev_4cccoGG7ELWjUMbwZ3sF" // Dev

    fun addProduct(Name: String){
        val product = hashMapOf(
            "Purchased" to false,
            "Name" to Name,
        )
        db.collection("Lists").document(ListID).collection("List")
            .add(product)
    }

    private fun removeProduct(document: String)
    {
        db.collection("Lists").document(ListID).collection("List").document(document)
            .delete()
    }

    fun removeCompleted()
    {
        db.collection("Lists").document(ListID).collection("List")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data.toList().toTypedArray()
                    val product = Product(document.id, data[0].second as Boolean, data[1].second as String)
                    if (product.Purchased)
                    {
                        removeProduct(document.id)
                    }
                }
            }
    }

    fun getProducts(activity: FragmentActivity?, products_list: LinearLayout, context: Context) {

        val db = Firebase.firestore

        db.collection("Lists").document(ListID).collection("List")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data.toList().toTypedArray()
                    val product = Product(document.id, data[0].second as Boolean, data[1].second as String)

                    val productView = CheckBox(activity)

                    val paddingVal = 10.dpToPixelsInt(context)


                    productView.textSize = 18f
                    productView.text = product.Name
                    productView.setBackgroundResource(R.drawable.productbutton)
                    productView.setTextColor(R.attr.colorOnSecondary)
                    productView.buttonTintList = ColorStateList.valueOf(R.attr.colorOnSecondary)

                    productView.isChecked = product.Purchased
                    productView.setPadding(0, paddingVal,0,paddingVal);

                    productView.layoutParams = RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)

                    productView.setOnClickListener()
                        {
                            completeProduct(product)
                        }

                    products_list.addView(productView)
                }
            }
    }

    private fun Int.dpToPixelsInt(context: Context):Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),context.resources.displayMetrics
    ).toInt()

    private fun completeProduct(product: Product) {
        product.Purchased = !product.Purchased
        db.collection("Lists").document(ListID).collection("List").document(product.ID).set(product)
    }
}
