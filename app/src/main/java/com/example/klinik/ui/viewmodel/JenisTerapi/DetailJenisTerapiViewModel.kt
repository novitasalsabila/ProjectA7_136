package com.example.klinik.ui.viewmodel.JenisTerapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinik.model.JenisTerapi
import com.example.klinik.repository.JenisTerapiRepository
import com.example.klinik.ui.view.JenisTerapi.DestinasiDetailJenisTerapi
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailJenisTerapiUiState {
    data class Success(val jenisTerapi: JenisTerapi) : DetailJenisTerapiUiState()
    object Error : DetailJenisTerapiUiState()
    object Loading : DetailJenisTerapiUiState()
}

class DetailJenisTerapiViewModel(
    savedStateHandle: SavedStateHandle,
    private val jenisTerapiRepository: JenisTerapiRepository
) : ViewModel() {

    private val id_jenis_terapi: String = checkNotNull(savedStateHandle[DestinasiDetailJenisTerapi.ID_JENIS_TERAPI])
    var detailJenisTerapiUiState: DetailJenisTerapiUiState by mutableStateOf(DetailJenisTerapiUiState.Loading)
        private set

    init {
        getJenisTerapiById()
    }

    fun getJenisTerapiById() {
        viewModelScope.launch {
            detailJenisTerapiUiState = DetailJenisTerapiUiState.Loading
            detailJenisTerapiUiState = try {
                DetailJenisTerapiUiState.Success(jenisTerapi = jenisTerapiRepository.getJenisTerapiById(id_jenis_terapi))
            } catch (e: IOException) {
                DetailJenisTerapiUiState.Error
            }
        }
    }
}
