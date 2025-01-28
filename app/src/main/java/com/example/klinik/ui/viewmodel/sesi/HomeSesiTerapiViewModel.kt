package com.example.klinik.ui.viewmodel.sesi

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinik.model.SesiTerapi
import com.example.klinik.repository.SesiTerapiRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeSesiTerapiUiState {
    data class Success(val sesiTerapi: List<SesiTerapi>) : HomeSesiTerapiUiState()
    object Error : HomeSesiTerapiUiState()
    object Loading : HomeSesiTerapiUiState()
}

class HomeSesiTerapiViewModel(private val sesiTerapiRepository: SesiTerapiRepository) : ViewModel() {

    var sesiTerapiUiState: HomeSesiTerapiUiState by mutableStateOf(HomeSesiTerapiUiState.Loading)
        private set

    init {
        getSesiTerapiList()
    }

    fun getSesiTerapiList() {
        viewModelScope.launch {
            sesiTerapiUiState = HomeSesiTerapiUiState.Loading
            try {
                val sesiTerapiList = sesiTerapiRepository.getSesi()
                Log.d("SesiTerapiData", sesiTerapiList.toString())
                sesiTerapiUiState = HomeSesiTerapiUiState.Success(sesiTerapiList)
            } catch (e: IOException) {
                Log.e("SesiTerapiError", "IOException: ${e.message}")
                sesiTerapiUiState = HomeSesiTerapiUiState.Error
            } catch (e: HttpException) {
                Log.e("SesiTerapiError", "HttpException: ${e.message}")
                sesiTerapiUiState = HomeSesiTerapiUiState.Error
            }
        }
    }

    fun deleteSesiTerapi(id_sesi: String) {
        viewModelScope.launch {
            try {
                sesiTerapiRepository.deleteSesi(id_sesi)
                getSesiTerapiList()
            } catch (e: IOException) {
                Log.e("DeleteSesiError", "IOException: ${e.message}")
                sesiTerapiUiState = HomeSesiTerapiUiState.Error
            } catch (e: HttpException) {
                Log.e("DeleteSesiError", "HttpException: ${e.message}")
                sesiTerapiUiState = HomeSesiTerapiUiState.Error
            }
        }
    }
}
