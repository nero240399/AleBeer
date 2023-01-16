package com.example.alebeer.beer.presentation.bearinfo.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.alebeer.beer.data.local.entity.Beer
import com.example.alebeer.databinding.ItemBeerBinding
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder

class BeerBinder : ItemBinder<Beer, BeerBinder.BeerViewHolder>() {

    override fun createViewHolder(parent: ViewGroup): BeerViewHolder {
        return BeerViewHolder(
            ItemBeerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun canBindData(item: Any): Boolean {
        return item is Beer
    }

    override fun bindViewHolder(holder: BeerViewHolder, item: Beer) {
        holder.bind(item)
    }

    class BeerViewHolder(private val binding: ItemBeerBinding) :
        ItemViewHolder<Beer>(binding.root) {

        fun bind(beer: Beer) {
            binding.beer = beer
            binding.executePendingBindings()
        }
    }
}
