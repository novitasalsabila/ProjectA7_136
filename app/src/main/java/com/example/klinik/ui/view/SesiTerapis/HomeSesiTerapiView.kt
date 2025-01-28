package com.example.klinik.ui.view.SesiTerapis

import com.example.klinik.ui.viewmodel.sesi.HomeSesiTerapiUiState
import com.example.klinik.ui.viewmodel.sesi.HomeSesiTerapiViewModel
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
import com.example.klinik.model.SesiTerapi
import com.example.klinik.ui.PenyediaViewModel
import com.example.klinik.ui.navigation.DestinasiNavigasi

object DestinasiHomeSesi : DestinasiNavigasi {
    override val route = "Home Sesi"
    override val titleRes = "Daftar Sesi Terapi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SesiTerapiScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navController: NavController,
    viewModel: HomeSesiTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                            text = "Daftar Sesi Terapi",
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
                    IconButton(onClick = { viewModel.getSesiTerapiList() }) {
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Sesi Terapi")
            }
        },
    ) { innerPadding ->
        SesiTerapiStatus(
            homeUiState = viewModel.sesiTerapiUiState,
            retryAction = { viewModel.getSesiTerapiList() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteSesiTerapi(it.id_sesi)
                viewModel.getSesiTerapiList()
            }
        )
    }
}

@Composable
fun SesiTerapiStatus(
    homeUiState: HomeSesiTerapiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (SesiTerapi) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomeSesiTerapiUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeSesiTerapiUiState.Success -> {
            if (homeUiState.sesiTerapi.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Sesi Terapi", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                SesiTerapiLayout(
                    sesiTerapi = homeUiState.sesiTerapi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_sesi) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeSesiTerapiUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun SesiTerapiLayout(
    sesiTerapi: List<SesiTerapi>,
    modifier: Modifier = Modifier,
    onDetailClick: (SesiTerapi) -> Unit,
    onDeleteClick: (SesiTerapi) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(sesiTerapi) { sesiTerapi ->
            SesiTerapiCard(
                sesiTerapi = sesiTerapi,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(sesiTerapi) },
                onDeleteClick = { onDeleteClick(sesiTerapi) }
            )
        }
    }
}

@Composable
fun SesiTerapiCard(
    sesiTerapi: SesiTerapi,
    modifier: Modifier = Modifier,
    onDeleteClick: (SesiTerapi) -> Unit = {},
    onEditClick: (SesiTerapi) -> Unit = {}
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
                    text = "ID Sesi: ${sesiTerapi.id_sesi}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))

                IconButton(onClick = { onDeleteClick(sesiTerapi) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Sesi Terapi",
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                IconButton(onClick = { onEditClick(sesiTerapi) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Sesi Terapi",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Divider()

            Column {
                Text(
                    text = "ID Pasien: ${sesiTerapi.id_pasien}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "ID Terapis: ${sesiTerapi.id_terapis}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Jenis Terapi: ${sesiTerapi.id_jenis_terapi}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Tanggal Sesi: ${sesiTerapi.tanggal_sesi}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Catatan Sesi: ${sesiTerapi.catatan_sesi}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
