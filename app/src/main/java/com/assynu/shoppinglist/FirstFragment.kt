package com.assynu.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.assynu.shoppinglist.adds.Manager
import com.assynu.shoppinglist.database.Manager.getProducts
import com.assynu.shoppinglist.users.Manager.getUserId
import com.assynu.shoppinglist.database.Manager.removeCompleted
import com.assynu.shoppinglist.databinding.FragmentFirstBinding
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.delay
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
        super.onResume()
        refreshProducts()
    }

    override fun onStop() {
        super.onStop()
        products_list.removeAllViews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Manager.addConfiguration(view)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.referesh.setOnClickListener {
            refreshProducts()
        }

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_settingsFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun refreshProducts() = runBlocking {
        getUserId(context)?.let { removeCompleted(it) }
        fab.isEnabled = false
        delay(100L)
        looksAwfulButWorks()
        fab.isEnabled = true

    }

    private fun looksAwfulButWorks() {
        this.context?.let {
            getUserId(context)?.let { it1 ->
                getProducts(
                    activity,
                    products_list,
                    it,
                    it1
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}