package com.elizav.fooddelivery.ui.menu.list.promo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elizav.fooddelivery.databinding.ItemPromoBinding

class PromoAdapter(
) : RecyclerView.Adapter<PromoHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PromoHolder = PromoHolder(
        binding = ItemPromoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
    )

    override fun onBindViewHolder(
        holder: PromoHolder,
        position: Int
    ) {
    }

    override fun getItemCount(): Int {
        return 10
    }
}