package com.example.klinik.ui.viewmodel.JenisTerapi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinik.model.JenisTerapi
import com.example.klinik.repository.JenisTerapiRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class JenisTerapiUiState {
    data class Success(val jenisTerapi: List<JenisTerapi>) : JenisTerapiUiState()
    object Error : JenisTerapiUiState()
    object Loading : JenisTerapiUiState()
}

class HomeJenisTerapiViewModel(private val jenisTerapiRepository: JenisTerapiRepository) : ViewModel() {

    var jenisTerapiUiState: JenisTerapiUiState by mutableStateOf(JenisTerapiUiState.Loading)
        private set

    init {
        getJenisTerapiList()
    }

    fun getJenisTerapiList() {
        viewModelScope.launch {
            jenisTerapiUiState = JenisTerapiUiState.Loading
            jenisTerapiUiState = try {
                JenisTerapiUiState.Success(jenisTerapiRepository.getAllJenisTerapi())
            } catch (e: IOException) {
                JenisTerapiUiState.Error
            } catch (e: HttpException) {
                JenisTerapiUiState.Error
            }
        }
    }

    fun deleteJenisTerapi(idJenisTerapi: String) {
        viewModelScope.launch {
            try {
                jenisTerapiRepository.deleteJenisTerapi(idJenisTerapi)
                getJenisTerapiList()
            } catch (e: IOException) {
                jenisTerapiUiState = JenisTerapiUiState.Error
            } catch (e: HttpException) {
                jenisTerapiUiState = JenisTerapiUiState.Error
            }
        }
    }
}
