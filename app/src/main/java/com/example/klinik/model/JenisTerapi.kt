package com.example.klinik.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JenisTerapi(
    val id_jenis_terapi: String,         // ID untuk setiap jenis terapi
    val nama_jenis_terapi: String,       // Nama jenis terapi
    val deskripsi_terapi: String        // Deskripsi tentang jenis terapi
)

