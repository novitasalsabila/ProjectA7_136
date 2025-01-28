package com.example.klinik.ui.view.Pasien

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinik.model.Pasien
import com.example.klinik.ui.PenyediaViewModel
import com.example.klinik.ui.customwidget.TopAppBar
import com.example.klinik.ui.navigation.DestinasiNavigasi
import com.example.klinik.ui.viewmodel.pasien.DetailPasienUiState
import com.example.klinik.ui.viewmodel.pasien.DetailPasienViewModel


object DestinasiDetailPasien : DestinasiNavigasi {
    override val route = "pasien_detail"
    override val titleRes = "Detail Pasien"
    const val ID_PASIEN = "id_pasien"
    val routeWithArgs = "$route/{$ID_PASIEN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPasienView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailViewModel: DetailPasienViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navigateToEdit: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiDetailPasien.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val id_pasien = (detailViewModel.detailPasienUiState as? DetailPasienUiState.Success)?.pasien?.id_pasien
                    if (id_pasien != null) onEditClick(id_pasien)
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Pasien",
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding).offset(y = (-70).dp)
        ) {
            DetailPasienStatus(
                pasienUiState = detailViewModel.detailPasienUiState,
                retryAction = { detailViewModel.getPasienById() },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun DetailPasienStatus(
    pasienUiState: DetailPasienUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (pasienUiState) {
        is DetailPasienUiState.Success -> {
            DetailPasienCard(
                pasien = pasienUiState.pasien,
                modifier = modifier.padding(16.dp)
            )
        }

        is DetailPasienUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailPasienUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Terjadi kesalahan.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = retryAction) {
                        Text(text = "Coba Lagi")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailPasienCard(
    pasien: Pasien,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
//        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium,
   //     shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFECD5E7)
        ),

    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ComponentDetailPasien(judul = "ID Pasien", isinya = pasien.id_pasien)
            ComponentDetailPasien(judul = "Nama", isinya = pasien.nama_pasien)
            ComponentDetailPasien(judul = "Alamat", isinya = pasien.alamat)
            ComponentDetailPasien(judul = "Nomor Telepon", isinya = pasien.nomor_telepon)
            ComponentDetailPasien(judul = "Tanggal Lahir", isinya = pasien.tanggal_lahir)
            ComponentDetailPasien(judul = "Riwayat Medikal", isinya = pasien.riwayat_medikal)
        }
    }
}

@Composable
fun ComponentDetailPasien(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$judul:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6A6A6A), // Gray untuk teks judul, bisa disesuaikan
            modifier = Modifier.weight(1f) // Memberikan space di sebelah kiri
        )
        Text(
            text = isinya,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF333333), // Teks konten lebih gelap untuk kontras
            modifier = Modifier.weight(2f), // Memberikan space di sebelah kanan
            maxLines = 2, // Jika teks terlalu panjang, batasi tampilannya
            overflow = TextOverflow.Ellipsis // Jika teks panjang, beri tanda elipsis
        )
    }
}
