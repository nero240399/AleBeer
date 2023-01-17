package com.example.alebeer.beer.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.alebeer.beer.domain.model.Beer
import com.example.alebeer.beer.presentation.favorite.component.FavoriteBinder
import com.example.alebeer.databinding.FragmentBeerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import mva2.adapter.ListSection
import mva2.adapter.MultiViewAdapter

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val binding: FragmentBeerBinding by lazy {
        FragmentBeerBinding.inflate(layoutInflater)
    }

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: MultiViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create Adapter
        adapter = MultiViewAdapter()
        binding.beerList.adapter = adapter

        // Register Binder
        adapter.registerItemBinders(
            FavoriteBinder(
                onDeleteClick = { viewModel.onEvent(FavoriteEvent.OnDeleteButton(it)) },
                onUpdateClick = { beer, note ->
                    viewModel.onEvent(FavoriteEvent.OnUpdateButton(beer, note))
                }
            )
        )

        // Create Section and add items
        val listSection = ListSection<Beer>()

        // Add Section to the adapter
        adapter.addSection(listSection)

        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect { uiState ->
                binding.apply {
                    if (uiState.isLoading) {
                        ivLoading.visibility = View.VISIBLE
                    } else {
                        ivLoading.visibility = View.GONE
                    }
                }

                listSection.set(uiState.listBeer)
            }
        }
    }
}
