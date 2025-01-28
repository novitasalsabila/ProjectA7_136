package com.example.klinik.ui.view.Terapis

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.klinik.R
import com.example.klinik.model.Terapis
import com.example.klinik.ui.PenyediaViewModel
import com.example.klinik.ui.navigation.DestinasiNavigasi
import com.example.klinik.ui.viewmodel.terapisVM.HomeTerapisViewModel
import com.example.klinik.ui.viewmodel.terapisVM.TerapisUiState

object DestinasiHomeTerapis : DestinasiNavigasi {
    override val route = "home terapis"
    override val titleRes = "Daftar Terapis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerapisScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navController: NavController,
    onBackClick: () -> Unit = {},
    viewModel: HomeTerapisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Daftar Terapis",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.getTerapisList() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Lokasi")
            }
        },
    ) { innerPadding ->
        HomeStatus(
            terapisUiState = viewModel.terapisUiState,
            retryAction = { viewModel.getTerapisList() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteTerapis(it.id_terapis)
                viewModel.getTerapisList()
            }
        )
    }
}

@Composable
fun HomeStatus(
    terapisUiState: TerapisUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Terapis) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (terapisUiState) {
        is TerapisUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is TerapisUiState.Success -> {
            if (terapisUiState.terapis.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Pasien", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                PasienLayout(
                    terapis = terapisUiState.terapis,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_terapis) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is TerapisUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.loading_img), contentDescription = "")
            Text(
                text = stringResource(R.string.loading_failed),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Button(onClick = retryAction, modifier = Modifier.padding(top = 16.dp)) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}

@Composable
fun PasienLayout(
    terapis: List<Terapis>,
    modifier: Modifier = Modifier,
    onDetailClick: (Terapis) -> Unit,
    onDeleteClick: (Terapis) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(terapis) { terapis ->
            PasienCard(
                terapis = terapis,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(terapis) },
                onDeleteClick = { onDeleteClick(terapis) }
            )
        }
    }
}

@Composable
fun PasienCard(
    terapis: Terapis,
    modifier: Modifier = Modifier,
    onDeleteClick: (Terapis) -> Unit = {},
    onEditClick: (Terapis) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = terapis.nama_terapis,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))

                IconButton(onClick = { onDeleteClick(terapis) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Pasien",
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                IconButton(onClick = { onEditClick(terapis) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Pasien",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Divider()

            Column {
                Text(
                    text = "ID Terapis: ${terapis.id_terapis}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Nama Terapis: ${terapis.nama_terapis}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Spesialisasi: ${terapis.spesialisasi}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Nomor Izin: ${terapis.nomor_izin_praktik}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
