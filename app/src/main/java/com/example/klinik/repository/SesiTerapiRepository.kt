package com.example.klinik.repository

import com.example.klinik.model.SesiTerapi
import com.example.klinik.service_api.SesiTerapiService
import java.io.IOException


interface SesiTerapiRepository {
    suspend fun getSesi(): List<SesiTerapi>
    suspend fun insertSesi(sesiTerapi: SesiTerapi)
    suspend fun updateSesi(id_Sesi: String, sesiTerapi: SesiTerapi)
    suspend fun deleteSesi(id_Sesi: String)
    suspend fun getSesiById(id_Sesi: String): SesiTerapi
}

class NetworkSesiTerapiRepository(
    private val sesiTerapiService: SesiTerapiService
) : SesiTerapiRepository {
    override suspend fun insertSesi(sesiTerapi: SesiTerapi) {
        sesiTerapiService.insertSesi(sesiTerapi)
        println(sesiTerapi)
    }

    override suspend fun updateSesi(id_sesi: String, sesiTerapi: SesiTerapi) {
        sesiTerapiService.updateSesi(id_sesi, sesiTerapi)
    }

    override suspend fun deleteSesi(id_sesi: String) {
        try {
            val response = sesiTerapiService.deleteSesi(id_sesi)
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

    override suspend fun getSesi(): List<SesiTerapi> = sesiTerapiService.getSesi()

    override suspend fun getSesiById(id_sesi: String): SesiTerapi {
        return sesiTerapiService.getSesiById(id_sesi)
    }
}