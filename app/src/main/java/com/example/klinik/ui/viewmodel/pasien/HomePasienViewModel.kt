package com.example.klinik.ui.viewmodel.pasien

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinik.model.Pasien
import com.example.klinik.repository.PasienRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeUiState {
    data class Success(val pasien: List<Pasien>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomePasienViewModel(private val pasienRepository: PasienRepository) : ViewModel() {

    var pasienUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getPasienList()
    }

//    fun getPasienList() {
//        viewModelScope.launch {
//            pasienUiState = HomeUiState.Loading
//            pasienUiState = try {
//                HomeUiState.Success(pasienRepository.getPasien())
//            } catch (e: IOException) {
//                HomeUiState.Error
//            } catch (e: HttpException) {
//                HomeUiState.Error
//            }
//        }
//    }

    fun getPasienList() {
        viewModelScope.launch {
            pasienUiState = HomeUiState.Loading
            try {
                val pasienList = pasienRepository.getPasien()
                Log.d("PasienData", pasienList.toString())
                pasienUiState = HomeUiState.Success(pasienList)
            } catch (e: IOException) {
                Log.e("PasienError", "IOException: ${e.message}")
                pasienUiState = HomeUiState.Error
            } catch (e: HttpException) {
                Log.e("PasienError", "HttpException: ${e.message}")
                pasienUiState = HomeUiState.Error
            }
        }
    }


    fun deletePasien(id_pasien: String) {
        viewModelScope.launch {
            try {
                pasienRepository.deletePasien(id_pasien)
                getPasienList()
            } catch (e: IOException) {
                pasienUiState = HomeUiState.Error
            } catch (e: HttpException) {
                pasienUiState = HomeUiState.Error
            }
        }
    }
}
