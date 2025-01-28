package com.example.klinik.ui.view.JenisTerapi

import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.klinik.ui.viewmodel.JenisTerapi.DetailJenisTerapiUiState
import com.example.klinik.ui.viewmodel.JenisTerapi.DetailJenisTerapiViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinik.model.JenisTerapi
import com.example.klinik.ui.PenyediaViewModel
import com.example.klinik.ui.customwidget.TopAppBar
import com.example.klinik.ui.navigation.DestinasiNavigasi

object DestinasiDetailJenisTerapi : DestinasiNavigasi {
    override val route = "jenis_terapi_detail"
    override val titleRes = "Detail Jenis Terapi"
    const val ID_JENIS_TERAPI = "id_jenis_terapi"
    val routeWithArgs = "$route/{$ID_JENIS_TERAPI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailJenisTerapiView(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onEditClick: (String) -> Unit,
    detailViewModel: DetailJenisTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiDetailJenisTerapi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val id_jenis_terapi = (detailViewModel.detailJenisTerapiUiState as? DetailJenisTerapiUiState.Success)?.jenisTerapi?.id_jenis_terapi
                    if (id_jenis_terapi != null) onEditClick(id_jenis_terapi)
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Jenis Terapi",
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding).offset(y = (-70).dp)
        ) {
            DetailJenisTerapiStatus(
                jenisTerapiUiState = detailViewModel.detailJenisTerapiUiState,
                retryAction = { detailViewModel.getJenisTerapiById() },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun DetailJenisTerapiStatus(
    jenisTerapiUiState: DetailJenisTerapiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (jenisTerapiUiState) {
        is DetailJenisTerapiUiState.Success -> {
            DetailJenisTerapiCard(
                jenisTerapi = jenisTerapiUiState.jenisTerapi,
                modifier = modifier.padding(16.dp)
            )
        }

        is DetailJenisTerapiUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DetailJenisTerapiUiState.Error -> {
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
fun DetailJenisTerapiCard(
    jenisTerapi: JenisTerapi,
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ComponentDetailJenisTerapi(judul = "ID Jenis Terapi", isinya = jenisTerapi.id_jenis_terapi)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailJenisTerapi(judul = "Nama Jenis Terapi", isinya = jenisTerapi.nama_jenis_terapi)
            Spacer(modifier = Modifier.height(8.dp))
            ComponentDetailJenisTerapi(judul = "Deskripsi Terapi", isinya = jenisTerapi.deskripsi_terapi)
        }
    }
}

@Composable
fun ComponentDetailJenisTerapi(
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
