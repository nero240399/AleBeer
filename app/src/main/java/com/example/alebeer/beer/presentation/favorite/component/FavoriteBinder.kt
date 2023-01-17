package com.example.alebeer.beer.presentation.favorite.component

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.alebeer.beer.domain.model.Beer
import com.example.alebeer.databinding.ItemFavoriteBinding
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder

class FavoriteBinder(
    private val onUpdateClick: (Beer, String) -> Unit,
    private val onDeleteClick: (Beer) -> Unit
) : ItemBinder<Beer, FavoriteBinder.BeerViewHolder>() {

    override fun createViewHolder(parent: ViewGroup): BeerViewHolder {
        return BeerViewHolder(
            ItemFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onUpdateClick,
            onDeleteClick
        )
    }

    override fun canBindData(item: Any): Boolean {
        return item is Beer
    }

    override fun bindViewHolder(holder: BeerViewHolder, item: Beer) {
        holder.bind(item)
    }

    class BeerViewHolder(
        private val binding: ItemFavoriteBinding,
        private val onUpdateClick: (Beer, String) -> Unit,
        private val onDeleteClick: (Beer) -> Unit
    ) : ItemViewHolder<Beer>(binding.root) {

        init {
            binding.apply {
                btnUpdate.setOnClickListener { onUpdateClick(beer as Beer, etNote.text.toString()) }
                btnDelete.setOnClickListener { onDeleteClick(beer as Beer) }
            }
        }

        fun bind(beer: Beer) {
            binding.apply {
                this.beer = beer
                ivImage.setImageBitmap(beer.bitmap)
                this.executePendingBindings()
            }
        }
    }
}
