package com.example.klinik.repository

import com.example.klinik.model.Terapis
import com.example.klinik.service_api.PasienService
import com.example.klinik.service_api.TerapisService
import java.io.IOException

interface TerapisRepository {
    suspend fun getTerapis(): List<Terapis>
    suspend fun insertTerapis(terapis: Terapis)
    suspend fun updateTerapis(id_terapis: String, terapis: Terapis)
    suspend fun deleteTerapis(id_terapis: String)
    suspend fun getTerapisById(id_terapis: String): Terapis
}

class NetworkTerapisRepository(
    private val terapisService: TerapisService
) : TerapisRepository {
    override suspend fun getTerapis(): List<Terapis>  = terapisService.getTerapis()

    override suspend fun insertTerapis(terapis: Terapis) {
        terapisService.insertTerapis(terapis)
    }

    override suspend fun updateTerapis(id_terapis: String, terapis: Terapis) {
        terapisService.updateTerapis(id_terapis, terapis)
    }

    override suspend fun deleteTerapis(id_terapis: String) {
        try {
            val response = terapisService.deleteTerapis(id_terapis)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete pasien. HTTP status code : ${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTerapisById(id_terapis: String): Terapis {
        return terapisService.getTerapisById(id_terapis)
    }

}
