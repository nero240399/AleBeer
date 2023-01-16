package com.example.alebeer.beer.presentation.bearinfo.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.alebeer.beer.data.remote.dto.BeerDto
import com.example.alebeer.databinding.ItemBeerBinding
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder

class BeerBinder : ItemBinder<BeerDto, BeerBinder.BeerViewHolder>() {

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
        return item is BeerDto
    }

    override fun bindViewHolder(holder: BeerViewHolder, item: BeerDto) {
        holder.bind(item)
    }

    class BeerViewHolder(private val binding: ItemBeerBinding) :
        ItemViewHolder<BeerDto>(binding.root) {

        fun bind(beer: BeerDto) {
            binding.beer = beer
            binding.executePendingBindings()
        }
    }
}
