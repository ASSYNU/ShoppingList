package com.assynu.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.assynu.shoppinglist.databinding.FragmentFirstBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        getProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getProducts() {

        val db = Firebase.firestore

        db.collection("Lists").document("4cccoGG7ELWjUMbwZ3sF").collection("List")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data.toList().toTypedArray()
                    val product = Product(document.id, data[0].second as Boolean, data[1].second as String)
                    val productTextView = Button(activity)

                    println(product.Name + " | " + product.Purchased + " | " + product.ID)

                    productTextView.textSize = 16f
                    productTextView.text = product.Name

                    products_list.addView(productTextView)
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents. $exception")
            }
    }
}