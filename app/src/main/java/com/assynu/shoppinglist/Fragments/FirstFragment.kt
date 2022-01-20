package com.assynu.shoppinglist.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.assynu.shoppinglist.ads.Manager
import com.assynu.shoppinglist.database.Manager.getProducts
import com.assynu.shoppinglist.databinding.FragmentFirstBinding
import com.assynu.shoppinglist.users.Manager.getUserId
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.runBlocking


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        refreshProducts()
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        products_list.removeAllViews()
    }

    @SuppressLint("ServiceCast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refreshProducts()

        Manager.addConfiguration(view)

        binding.fab.setOnClickListener {
            slideUp()
        }

        AddProductButton.setOnClickListener()
        {
            slideDown()
            val productName = product_name_input.text.toString()
            if (productName != "") {
                getUserId(context)?.let { it1 ->
                    com.assynu.shoppinglist.database.Manager.addProduct(
                        productName,
                        it1
                    )
                    context?.let {
                        getProducts(
                            activity,
                            products_list,
                            it1,
                            it
                        )
                    }
                }

            } else {
                Snackbar.make(it, "Invalid input, try again", Snackbar.LENGTH_LONG).show()
            }
            product_name_input.hideKeyboard()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    private fun refreshProducts() = runBlocking {
        getUserId(context)?.let { it1 ->
            context?.let {
                getProducts(
                    activity,
                    products_list,
                    it1,
                    it
                )
            }
        }
    }

    // slide the view from below itself to the current position
    private fun slideUp() {
        val fabanimate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            0F,  // fromYDelta
            adView.height.toFloat() + fab.height.toFloat() * 2
        ) // toYDelta
        fabanimate.duration = 500
        fabanimate.fillAfter = true

        val adViewanimate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            0F,  // fromYDelta
            adView.height.toFloat()
        ) // toYDelta
        adViewanimate.duration = 500
        adViewanimate.fillAfter = true

        AddProductMenu.visibility = View.VISIBLE
        val AddProductMenuanimate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            AddProductMenu.height.toFloat(),  // fromYDelta
            0F
        ) // toYDelta
        AddProductMenuanimate.duration = 500
        AddProductMenuanimate.fillAfter = true

        adView.startAnimation(adViewanimate)
        fab.startAnimation(fabanimate)
        AddProductMenu.startAnimation(AddProductMenuanimate)
        fab.visibility = View.INVISIBLE
        adView.visibility = View.INVISIBLE
    }

    // slide the view from its current position to below itself
    fun slideDown() {
        val AddProductMenuanimate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            0F,  // fromYDelta
            AddProductMenu.height.toFloat()
        ) // toYDelta
        AddProductMenuanimate.duration = 500
        AddProductMenuanimate.fillAfter = true

        fab.visibility = View.VISIBLE
        val fabanimate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            adView.height.toFloat() + fab.height.toFloat() * 2,  // fromYDelta
            0F
        ) // toYDelta
        fabanimate.duration = 500
        fabanimate.fillAfter = true

        adView.visibility = View.VISIBLE
        val adViewanimate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            AddProductMenu.height.toFloat(),  // fromYDelta
            0F
        ) // toYDelta
        adViewanimate.duration = 500
        adViewanimate.fillAfter = true

        adView.startAnimation(adViewanimate)
        fab.startAnimation(fabanimate)
        AddProductMenu.startAnimation(AddProductMenuanimate)
        AddProductMenu.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}