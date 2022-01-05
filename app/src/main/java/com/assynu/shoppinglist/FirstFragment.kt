package com.assynu.shoppinglist

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.assynu.shoppinglist.Database.getProducts
import com.assynu.shoppinglist.Database.removeCompleted
import com.assynu.shoppinglist.databinding.FragmentFirstBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    lateinit var mAdView : AdView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        refreshProducts()
    }

    override fun onStop() {
        super.onStop()
        products_list.removeAllViews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.referesh.setOnClickListener {
            refreshProducts()
        }

        MobileAds.initialize(view.context) {}

        mAdView = view.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    private fun refreshProducts() = runBlocking {
        removeCompleted()
        products_list.removeAllViews()
        fab.isClickable = false
        delay(500L)
        looksAwfulButWorks()
        delay(500L)
        fab.isClickable = true

    }

    private fun looksAwfulButWorks() {
        this.context?.let {
            getProducts(
                activity,
                products_list,
                it
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}