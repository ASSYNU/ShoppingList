package com.assynu.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.assynu.shoppinglist.adds.Manager
import com.assynu.shoppinglist.database.Manager.addProduct
import com.assynu.shoppinglist.users.Manager.getUserId
import com.assynu.shoppinglist.databinding.FragmentSecondBinding
import com.google.android.gms.ads.AdView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    lateinit var mAdView : AdView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Manager.addConfiguration(view)

        binding.addButton.setOnClickListener {
            val productName = product_name_input.text.toString()
            val productAmount = product_amount_input.text.toString()
            if (productName != "")
            {
                getUserId(context)?.let { it1 -> addProduct(productName, productAmount, it1) }
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
            else
            {
                Snackbar.make(it, "Invalid input, try again", Snackbar.LENGTH_LONG).show()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}