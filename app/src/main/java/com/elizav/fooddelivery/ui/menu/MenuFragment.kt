package com.elizav.fooddelivery.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elizav.fooddelivery.R
import com.elizav.fooddelivery.databinding.FragmentMenuBinding
import com.elizav.fooddelivery.domain.model.Meal
import com.elizav.fooddelivery.ui.main.list.PromoAdapter
import com.elizav.fooddelivery.ui.menu.list.MealAdapter
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var promoAdapter: PromoAdapter

    private val menuViewModel: MenuViewModel by viewModels()
    lateinit var mealsAdapter: MealAdapter

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
        initAdapter()
        initObservers()
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

    private fun initAdapter() {
        mealsAdapter = MealAdapter()
        with(binding.recyclerMeals) {
            adapter = mealsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            val dividerItemDecoration = MaterialDividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun initObservers() {
        menuViewModel.meals.observe(viewLifecycleOwner) { result ->
            result.fold(onSuccess = { meals ->
                updateList(meals)
                showLoading(false)
            }, onFailure = {
                showSnackbar(it.message.toString())
            })
        }
    }

    private fun updateList(meals: List<Meal>) = view?.let {
        mealsAdapter.submitList(meals)
        binding.tvEmpty.isVisible = meals.isEmpty()
    }

    private fun showSnackbar(text: String) = view?.let {
        Snackbar.make(
            it,
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun showLoading(isLoading: Boolean = true) {
        binding.recyclerMeals.isVisible = !isLoading
        binding.progressBar.isVisible = isLoading
    }
}