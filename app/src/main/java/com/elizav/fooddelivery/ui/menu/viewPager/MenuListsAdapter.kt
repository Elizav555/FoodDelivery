package com.elizav.fooddelivery.ui.menu.viewPager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.elizav.fooddelivery.ui.menu.MenuListFragment
import com.elizav.fooddelivery.ui.menu.viewPager.Queries.queries

class MenuListsAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = queries.size

    override fun createFragment(position: Int): Fragment {
        return queries.getOrNull(position)?.let { MenuListFragment(it) }
            ?: throw IllegalArgumentException()
    }
}