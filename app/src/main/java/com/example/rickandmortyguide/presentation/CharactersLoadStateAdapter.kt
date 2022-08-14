package com.example.rickandmortyguide.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyguide.R
import com.example.rickandmortyguide.databinding.LoadingStateItemBinding


fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
    header: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        header.loadState = loadStates.refresh
        footer.loadState = loadStates.append
    }
    return ConcatAdapter(header, this, footer)
}

class CharactersLoadStateAdapter(private val adapter: PagingDataAdapter<*, *>) :
    LoadStateAdapter<CharactersLoadStateAdapter.CharactersLoadStateHolder>() {

    class CharactersLoadStateHolder(
        private val binding: LoadingStateItemBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBarLoading.isVisible = loadState is LoadState.Loading
                textViewError.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            }
        }
    }

    override fun onBindViewHolder(holder: CharactersLoadStateHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CharactersLoadStateHolder =
        CharactersLoadStateHolder(
            LoadingStateItemBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.loading_state_item, parent, false)
            )
        )
        { adapter.retry() }
}
