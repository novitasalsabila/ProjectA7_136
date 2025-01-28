package com.example.klinik.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.klinik.R

@Composable
fun HomeApp(
    modifier: Modifier = Modifier,
    onHalamanHomePasien: () -> Unit,
    onHalamanHomeSesiTerapis: () -> Unit,
    onHalamanHomeJenisTerapi: () -> Unit,
    onHalamanHomeMK: () -> Unit,
    onHalamanHomeVDR: () -> Unit,
    onHalamanHomeLKS: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
            .background(color = Color.White),
        verticalArrangement = Arrangement.Top
    ) {
        HeaderSection()
        BodySection(
            onHalamanHomePasien = onHalamanHomePasien,
            onHalamanHomeMK = onHalamanHomeMK,
            onHalamanHomeLKS = onHalamanHomeLKS,
            onHalamanHomeSesiTerapi = onHalamanHomeSesiTerapis,
            onHalamanHomeJenisTerapi = onHalamanHomeJenisTerapi
        )
    }
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
            .padding(bottom = 25.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 1.dp, top = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logoklinik), // Ganti "logo_klinik" dengan nama file logo di drawable
                    contentDescription = "Logo Klinik",
                    modifier = Modifier
                        .size(200.dp) // Ukuran logo
                        .padding(bottom = 16.dp)
                )

                // Header Text
                Text(
                    text = "Pilih Menu Yang Ingin di Kekola",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun BodySection(
    onHalamanHomePasien: () -> Unit,
    onHalamanHomeMK: () -> Unit,
    onHalamanHomeSesiTerapi: () -> Unit,
    onHalamanHomeJenisTerapi: () -> Unit,
    onHalamanHomeLKS: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
//        Text(
//            text = "Silahkan pilih menu yang ingin anda kelola",
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 8.dp)
//                .wrapContentWidth(Alignment.CenterHorizontally)
//        )

        // "PASIEN" Button
        ManageBox(
            title = "Pasien",
            description = "Kelola Pasien",
            backgroundColor = Color(0xFFFF74A3),
            iconleft = Icons.Default.Person, // Ikon orang
            onClick = { onHalamanHomePasien() }
        )

        // "Lokasi" Button
        ManageBox(
            title = "Terapis",
            description = "Kelola Terapis",
            backgroundColor = Color(0xFFFF74A3),
            iconleft = Icons.Default.Add,
            onClick = { onHalamanHomeMK() }
        )

        ManageBox(
            title = "Jenis Terapi",
            description = "Kelola Jenis Terapi",
            backgroundColor = Color(0xFFFF74A3),
            iconleft = Icons.Default.Favorite, // Ikon hati
            onClick = { onHalamanHomeJenisTerapi() }
        )

        ManageBox(
            title = "Sesi Terapi",
            description = "Kelola Sesi Terapi",
            backgroundColor = Color(0xFFFF74A3),
            iconleft = Icons.Default.DateRange, // Ikon jadwal
            onClick = { onHalamanHomeSesiTerapi() }
        )
    }
}

@Composable
fun ManageBox(
    title: String,
    description: String,
    backgroundColor: Color = Color(0xFFFFC0CB), // Warna pink (Pink Light)
    iconleft: ImageVector, // Ikon kecil di kanan
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center), // Posisi teks di tengah
            horizontalAlignment = Alignment.CenterHorizontally, // Teks sejajar horizontal
            verticalArrangement = Arrangement.Center // Teks sejajar vertikal
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.White
            )
        }
        // Icon di kanan
        Image(
            imageVector = iconleft,
            contentDescription = "$title Icon",
            modifier = Modifier.size(32.dp) // Ukuran ikon
                .padding(end = 12.dp) // Jarak antara ikon dan teks // Ukuran ikon kanan
        )
    }
}