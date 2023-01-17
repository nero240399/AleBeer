package com.example.alebeer.beer.presentation.bearinfo.component

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import com.example.alebeer.beer.domain.model.Beer
import com.example.alebeer.databinding.ItemBeerBinding
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder

class BeerBinder(private val onSaveButtonClick: (Beer, String, Bitmap, Int) -> Unit) :
    ItemBinder<Beer, BeerBinder.BeerViewHolder>() {

    override fun createViewHolder(parent: ViewGroup): BeerViewHolder {
        return BeerViewHolder(
            ItemBeerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onSaveButtonClick
        )
    }

    override fun canBindData(item: Any): Boolean {
        return item is Beer
    }

    override fun bindViewHolder(holder: BeerViewHolder, item: Beer) {
        holder.bind(item)
    }

    class BeerViewHolder(
        private val binding: ItemBeerBinding,
        onSaveButtonClick: (Beer, String, Bitmap, Int) -> Unit
    ) : ItemViewHolder<Beer>(binding.root) {

        init {
            binding.apply {
                btnSave.setOnClickListener {
                    onSaveButtonClick(
                        beer as Beer,
                        etNote.text.toString(),
                        ivImage.drawToBitmap(),
                        layoutPosition
                    )
                }
            }
        }

        fun bind(beer: Beer) {
            binding.beer = beer
            binding.executePendingBindings()
        }
    }
}
