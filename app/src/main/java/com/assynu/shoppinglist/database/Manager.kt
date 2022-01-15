@file:Suppress(
    "unused", "SpellCheckingInspection", "SpellCheckingInspection",
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
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.FragmentActivity
import com.assynu.shoppinglist.Product
import com.assynu.shoppinglist.R
import com.assynu.shoppinglist.users.Manager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


@SuppressLint("StaticFieldLeak")
internal object Manager {
    private val db = Firebase.firestore

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

    @SuppressLint("SetTextI18n")
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
                        Product(
                            document.id,
                            data[0].second as Boolean,
                            data[1].second as String
                        )

                    addProductToView(activity, context, product, ListID, products_list)
                }
            }
    }

    @SuppressLint("SetTextI18n")
    private fun addProductToView(
        activity: FragmentActivity?,
        context: Context,
        product: Product,
        ListID: String,
        products_list: LinearLayout
    ) {
        val productView = CheckBox(activity)

        val paddingVal = 12.dpToPixelsInt(context)


        productView.textSize = 18f
        productView.text = product.Name
        productView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_delete_24, 0)
        productView.isChecked = product.Purchased
        productView.setPadding(paddingVal)
        productView.buttonDrawable = ContextCompat.getDrawable(context, R.drawable.empty_tall_divider)

        productView.layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        productView.setOnClickListener()
        {
            removeProduct(product.ID, ListID)
            products_list.removeView(productView)
        }

        products_list.addView(productView)
    }

    private fun Int.dpToPixelsInt(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

//    fun completeProduct(product: Product, ListID: String) {
//        product.Purchased = !product.Purchased
//        db.collection("Lists").document(ListID).collection("List").document(product.ID).set(product)
//    }
//
//    fun removeCompleted(ListID: String) {
//        db.collection("Lists").document(ListID).collection("List")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    val data = document.data.toList().toTypedArray()
//                    val product =
//                        Product(
//                            document.id,
//                            data[1].second as Boolean,
//                            data[0].second as String
//                        )
//                    if (product.Purchased) {
//                        removeProduct(document.id, ListID)
//                    }
//                }
//            }
//    }
}
