package com.example.alebeer.beer.presentation.favorite.component

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.alebeer.R
import com.example.alebeer.beer.domain.model.Beer
import com.example.alebeer.databinding.ItemFavoriteBinding
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder

class FavoriteBinder(
    private val onUpdateClick: (Beer, String) -> Unit,
    private val onDeleteClick: (Beer) -> Unit
) :
    ItemBinder<Beer, FavoriteBinder.BeerViewHolder>() {

    override fun createViewHolder(parent: ViewGroup): BeerViewHolder {
        return BeerViewHolder(
            ItemFavoriteBinding.inflate(
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

        val buttonDelete = holder.itemView.findViewById<Button>(R.id.btn_delete)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.iv_image)
        val editText = holder.itemView.findViewById<EditText>(R.id.et_note)
        val buttonUpdate = holder.itemView.findViewById<Button>(R.id.btn_update)

        imageView.setImageBitmap(item.bitmap)

        buttonUpdate.setOnClickListener {
            onUpdateClick(item, editText.text.toString())
        }

        buttonDelete.setOnClickListener { onDeleteClick(item) }
    }

    class BeerViewHolder(private val binding: ItemFavoriteBinding) :
        ItemViewHolder<Beer>(binding.root) {

        fun bind(beer: Beer) {
            binding.beer = beer
            binding.executePendingBindings()
        }
    }
}
