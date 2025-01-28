package com.example.klinik.repository

import com.example.klinik.model.JenisTerapi
import com.example.klinik.service_api.JenisTerapiService
import java.io.IOException

interface JenisTerapiRepository {
    suspend fun getAllJenisTerapi(): List<JenisTerapi>
    suspend fun getJenisTerapiById(idJenisTerapi: String): JenisTerapi
    suspend fun insertJenisTerapi(jenisTerapi: JenisTerapi)
    suspend fun updateJenisTerapi(idJenisTerapi: String, jenisTerapi: JenisTerapi)
    suspend fun deleteJenisTerapi(idJenisTerapi: String)
}

class NetworkJenisTerapiRepository(
    private val jenisTerapiApiService: JenisTerapiService
) : JenisTerapiRepository {
    override suspend fun getAllJenisTerapi(): List<JenisTerapi> =
        jenisTerapiApiService.getAllJenisTerapi()

    override suspend fun getJenisTerapiById(id_jenis_terapi: String): JenisTerapi =
        jenisTerapiApiService.getJenisTerapiById(id_jenis_terapi)

    override suspend fun insertJenisTerapi(jenisTerapi: JenisTerapi) {
        jenisTerapiApiService.insertJenisTerapi(jenisTerapi)
        println(jenisTerapi)
    }

    override suspend fun updateJenisTerapi(id_jenis_terapi: String, jenisTerapi: JenisTerapi) {
        jenisTerapiApiService.updateJenisTerapi(id_jenis_terapi, jenisTerapi)
    }

    override suspend fun deleteJenisTerapi(id_jenis_terapi: String) {
        try {
            val response = jenisTerapiApiService.deleteJenisTerapi(id_jenis_terapi)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete jenis terapi. HTTP status code: ${response.code()}")
            } else {
                println("Deleted jenis terapi: ${response.message()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
