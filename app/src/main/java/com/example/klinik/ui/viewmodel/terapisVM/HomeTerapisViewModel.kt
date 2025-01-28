package com.example.klinik.ui.viewmodel.terapisVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinik.model.Terapis
import com.example.klinik.repository.TerapisRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class TerapisUiState {
    data class Success(val terapis: List<Terapis>) : TerapisUiState()
    object Error : TerapisUiState()
    object Loading : TerapisUiState()
}

class HomeTerapisViewModel(private val terapisRepository: TerapisRepository) : ViewModel() {

    var terapisUiState: TerapisUiState by mutableStateOf(TerapisUiState.Loading)
        private set

    init {
        getTerapisList()
    }

    fun getTerapisList() {
        viewModelScope.launch {
            terapisUiState = TerapisUiState.Loading
            terapisUiState = try {
                TerapisUiState.Success(terapisRepository.getTerapis())
            } catch (e: IOException) {
                TerapisUiState.Error
            } catch (e: HttpException) {
                TerapisUiState.Error
            }
        }
    }

    fun deleteTerapis(id_terapis: String) {
        viewModelScope.launch {
            try {
                terapisRepository.deleteTerapis(id_terapis)
                getTerapisList()
            } catch (e: IOException) {
                terapisUiState = TerapisUiState.Error
            } catch (e: HttpException) {
                terapisUiState = TerapisUiState.Error
            }
        }
    }
}