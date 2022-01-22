@file:Suppress(
    "unused", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection"
)

package com.assynu.shoppinglist.database

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.setPadding
import androidx.fragment.app.FragmentActivity
import com.assynu.shoppinglist.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


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
        ListID: String,
        context: Context
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

                    addProductToView(activity, product, ListID, products_list, context)
                }
            }
    }

    @SuppressLint("SetTextI18n")
    fun addProductToView(
        activity: FragmentActivity?,
        product: Product,
        ListID: String,
        products_list: LinearLayout,
        context: Context
    ) {
        fun Int.dpToPixelsInt(context: Context): Int = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
        ).toInt()

        fun TextView.showStrikeThrough(show: Boolean) {
            paintFlags =
                if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        @ColorInt
        fun Context.getColorFromAttr(
            @AttrRes attrColor: Int,
            typedValue: TypedValue = TypedValue(),
            resolveRefs: Boolean = true
        ): Int {
            theme.resolveAttribute(attrColor, typedValue, resolveRefs)
            return typedValue.data
        }

        val productView = ConstraintLayout(context)
        val productCheckBox = CheckBox(activity)
        val productRemove = ImageButton(activity)

        productView.id = View.generateViewId()
        productCheckBox.id = View.generateViewId()
        productRemove.id = View.generateViewId()

        val paddingVal = 12.dpToPixelsInt(context)

        productView.setPadding(paddingVal)


        productCheckBox.textSize = 18f
        productCheckBox.text = product.Name
        productCheckBox.isChecked = product.Purchased
        if (productCheckBox.isChecked) {
            productCheckBox.showStrikeThrough(true)
            productCheckBox.setTextColor(context.getColorFromAttr(R.attr.colorHint))
        }
        productCheckBox.layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        productCheckBox.setOnClickListener()
        {
            completeProduct(product, ListID)
            if (productCheckBox.isChecked) {
                productCheckBox.setTextColor(context.getColorFromAttr(R.attr.colorHint))
                productCheckBox.showStrikeThrough(true)
            } else {
                productCheckBox.setTextColor(context.getColorFromAttr(R.attr.colorOnContainer))
                productCheckBox.showStrikeThrough(false)
            }
        }

        println(productRemove.width + productCheckBox.width)

        productRemove.setBackgroundResource(R.drawable.ic_baseline_delete_24)
        productRemove.setOnClickListener()
        {
            removeProduct(product.ID, ListID)
            products_list.removeView(productView)
        }

        productView.addView(productCheckBox)
        productView.addView(productRemove)

        val set = ConstraintSet()
        set.clone(productView)
        set.connect(
            productRemove.id,
            ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.RIGHT
        )
        set.applyTo(productView)

        products_list.addView(productView)
    }

    fun completeProduct(product: Product, ListID: String) {
        product.Purchased = !product.Purchased
        db.collection("Lists").document(ListID).collection("List").document(product.ID).set(product)
    }
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
