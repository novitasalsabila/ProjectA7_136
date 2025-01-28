package com.example.klinik.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SesiTerapi(
    val id_sesi: String,                // ID untuk setiap sesi terapi
    val id_pasien: String,              // ID pasien yang mengikuti sesi terapi
    val id_terapis: String,             // ID terapis yang menyediakan terapi
    val id_jenis_terapi: String,         // ID jenis terapi yang diberikan
    val tanggal_sesi: String,           // Tanggal dan waktu sesi terapi
    val catatan_sesi: String            // Catatan tentang sesi terapi, termasuk respons pasien dan kemajuan
)

