package com.elizav.fooddelivery.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elizav.fooddelivery.databinding.FragmentMenuBinding
import com.elizav.fooddelivery.ui.main.list.PromoAdapter
import com.elizav.fooddelivery.ui.menu.viewPager.MenuListsAdapter
import com.elizav.fooddelivery.ui.menu.viewPager.Queries.queries
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment() : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var promoAdapter: PromoAdapter
    private lateinit var listsAdapter: MenuListsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPromoAdapter()
        listsAdapter = MenuListsAdapter(this)
        binding.viewPager.adapter = listsAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = queries.getOrNull(position) ?: ""
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initPromoAdapter() {
        promoAdapter = PromoAdapter()
        with(binding.rvPromo) {
            adapter = promoAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }
}