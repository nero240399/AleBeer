package com.example.alebeer.beer.presentation.bearinfo.component

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.drawToBitmap
import com.example.alebeer.R
import com.example.alebeer.beer.domain.model.Beer
import com.example.alebeer.databinding.ItemBeerBinding
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder

class BeerBinder(private val onSaveButtonClick: (Beer, Bitmap, String) -> Unit) :
    ItemBinder<Beer, BeerBinder.BeerViewHolder>() {

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

        val button = holder.itemView.findViewById<Button>(R.id.btn_save)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.iv_image)
        val editText = holder.itemView.findViewById<EditText>(R.id.et_note)

        button.setOnClickListener {
            button.setText(R.string.saving)

            onSaveButtonClick(
                item,
                imageView.drawToBitmap(),
                editText.text.toString()
            )
        }
    }

    class BeerViewHolder(private val binding: ItemBeerBinding) :
        ItemViewHolder<Beer>(binding.root) {

        fun bind(beer: Beer) {
            binding.beer = beer
            binding.executePendingBindings()
        }
    }
}
