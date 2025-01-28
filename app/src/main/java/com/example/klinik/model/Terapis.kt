package com.example.klinik.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Terapis(
    val id_terapis: String,             // ID untuk setiap terapis
    val nama_terapis: String,           // Nama lengkap terapis
    val spesialisasi: String,          // Area spesialisasi terapis (misal: terapi fisik, terapi okupasi)
    val nomor_izin_praktik: String       // Nomor izin praktik terapis
)
