package com.example.klinik.ui.viewmodel.JenisTerapi

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinik.model.JenisTerapi
import com.example.klinik.repository.JenisTerapiRepository
import kotlinx.coroutines.launch

class InsertJenisTerapiViewModel(private val jenisTerapiRepo: JenisTerapiRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertJenisTerapiUiState())
        private set

    fun updateInsertJenisTerapiState(insertUiEvent: InsertJenisTerapiUiEvent) {
        uiState = InsertJenisTerapiUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertJenisTerapi() {
        viewModelScope.launch {
            try {
                Log.d("Retrofit", "Data dikirim: ${uiState.insertUiEvent.toJenisTerapi()}")
                val response = jenisTerapiRepo.insertJenisTerapi(uiState.insertUiEvent.toJenisTerapi())
                Log.d("Retrofit", "Response: ${response}")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Retrofit", "Error: ${e.message}")
            }
        }
    }
}

fun InsertJenisTerapiUiEvent.toJenisTerapi(): JenisTerapi = JenisTerapi(
    id_jenis_terapi = id_jenis_terapi,
    nama_jenis_terapi = nama_jenis_terapi,
    deskripsi_terapi = deskripsi_terapi
)

fun JenisTerapi.toUiStateJenisTerapi(): InsertJenisTerapiUiState = InsertJenisTerapiUiState(
    insertUiEvent = toInsertUiEvent()
)

fun JenisTerapi.toInsertUiEvent(): InsertJenisTerapiUiEvent = InsertJenisTerapiUiEvent(
    id_jenis_terapi = id_jenis_terapi,
    nama_jenis_terapi = nama_jenis_terapi,
    deskripsi_terapi = deskripsi_terapi
)

data class InsertJenisTerapiUiState(
    val insertUiEvent: InsertJenisTerapiUiEvent = InsertJenisTerapiUiEvent()
)

data class InsertJenisTerapiUiEvent(
    val id_jenis_terapi: String = "",
    val nama_jenis_terapi: String = "",
    val deskripsi_terapi: String = ""
)
