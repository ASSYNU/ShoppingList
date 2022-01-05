@file:Suppress("unused", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection"
)

package com.assynu.shoppinglist

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

internal object Database {
    @SuppressLint("StaticFieldLeak")
    private val db = Firebase.firestore

    fun addProduct(Name: String, State: Boolean = false){

        val product = hashMapOf(
            "Purchased" to State,
            "Name" to Name,
        )

        db.collection("Lists").document("4cccoGG7ELWjUMbwZ3sF").collection("List")
            .add(product)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    private fun removeProduct(document: String)
    {
        db.collection("Lists").document("4cccoGG7ELWjUMbwZ3sF").collection("List").document(document)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    fun removeCompleted()
    {
        db.collection("Lists").document("4cccoGG7ELWjUMbwZ3sF").collection("List")
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

    fun refreshProducts()
    {
        //TODO
    }

    fun getProducts(activity: FragmentActivity?, products_list: LinearLayout, context: Context) {

        val db = Firebase.firestore

        db.collection("Lists").document("4cccoGG7ELWjUMbwZ3sF").collection("List")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data.toList().toTypedArray()
                    val product = Product(document.id, data[0].second as Boolean, data[1].second as String)

                    val productView = CheckBox(activity)

                    productView.textSize = 18f
                    productView.text = product.Name.uppercase()

                    val height = 50
                    productView.layoutParams = RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        height.dpToPixelsInt(context))

                    productView.setOnClickListener()
                        {
                            completeProduct(product)
                        }

                    productView.isChecked = product.Purchased


                    products_list.addView(productView)
                }
            }
    }

    private fun Int.dpToPixelsInt(context: Context):Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),context.resources.displayMetrics
    ).toInt()

    private fun completeProduct(product: Product) {
        removeProduct(product.ID)
        addProduct(product.Name, !product.Purchased)
    }

//    TODO -> Real time listening for database updates
//    fun listenForUpdates()
//    {
//        val docRef = db.collection("Lists").document("4cccoGG7ELWjUMbwZ3sF").collection("List")
//        docRef.addSnapshotListener { snapshot, e ->
//            if (e != null) {
//                Log.w(TAG, "Listen failed.", e)
//                return@addSnapshotListener
//            }
//
//            if (snapshot != null && snapshot.exists()) {
//                Log.d(TAG, "Current data: ${snapshot.data}")
//            } else {
//                Log.d(TAG, "Current data: null")
//            }
//        }
//    }
}
