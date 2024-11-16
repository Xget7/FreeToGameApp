package dev.xget.freetogame.presentation.games.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.xget.freetogame.domain.repository.FreeGamesRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val gamesRepository: FreeGamesRepositoryInterface
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenUiState())
    val state = _state.asStateFlow()

    init {
        observeGames()
    }

    private fun observeGames() {
        viewModelScope.launch {
            try {
                state.map { it.nameQuery to it.filterCategory to it.filterPlatform }
                    .distinctUntilChanged()
                    .flatMapLatest { (name) ->
                        gamesRepository.getFreeGames(
                            byName = name.first,
                            sortBy = state.value.filterOrderBy,
                            category = state.value.filterCategory,
                            platform = state.value.filterPlatform
                        )
                    }
                    .collect { games ->
                        _state.value = _state.value.copy(
                            games = games,
                            filterCounter = getFilterCounter(),
                            isLoading = false,
                            error = null
                        )
                    }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message.toString())
            }

        }
    }

    private fun getFilterCounter(): Int {
        var count = 0
        if (_state.value.filterPlatform.isNotEmpty()) {
            count++
        }
        if (_state.value.filterCategory.isNotEmpty()) {
            count++
        }
        if (_state.value.filterOrderBy.isNotEmpty()) {
            count++
        }
        return count
    }

    fun onSearchQueryChange(query: String) {
        _state.value = _state.value.copy(nameQuery = query.trim())
    }

    fun onFilterByCategory(category: String) {
        Log.d("HomeScreenViewModel", "onSearchQueryChange: $category")

        _state.value = _state.value.copy(filterCategory = category)
    }

    fun onFilterByOrder(order: String) {
        _state.value = _state.value.copy(filterOrderBy = order)
    }

    fun onFilterByPlatform(platform: String) {
        Log.d("HomeScreenViewModel", "onFilterByPlatform: $platform")
        _state.value = _state.value.copy(filterPlatform = platform)
    }

    fun resetFilters() {
        _state.value = _state.value.copy(
            filterCategory = "",
            filterOrderBy = "",
            filterPlatform = ""
        )
    }
}