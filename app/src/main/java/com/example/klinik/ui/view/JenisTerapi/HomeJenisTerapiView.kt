package com.example.klinik.ui.view.JenisTerapi

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
import com.example.klinik.model.JenisTerapi
import com.example.klinik.ui.PenyediaViewModel
import com.example.klinik.ui.navigation.DestinasiNavigasi
import com.example.klinik.ui.viewmodel.JenisTerapi.HomeJenisTerapiViewModel
import com.example.klinik.ui.viewmodel.JenisTerapi.JenisTerapiUiState

object DestinasiHomeJenis : DestinasiNavigasi {
    override val route = "Jenis"
    override val titleRes = "Daftar Jenis Terapi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JenisTerapiScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navController: NavController,
    onBackClick: () -> Unit = {},
    viewModel: HomeJenisTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                            text = "Daftar Jenis Terapi",
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
                    IconButton(onClick = { viewModel.getJenisTerapiList() }) {
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Jenis Terapi")
            }
        },
    ) { innerPadding ->
        HomeStatus(
            jenisTerapiUiState = viewModel.jenisTerapiUiState,
            retryAction = { viewModel.getJenisTerapiList() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteJenisTerapi(it.id_jenis_terapi)
                viewModel.getJenisTerapiList()
            }
        )
    }
}

@Composable
fun HomeStatus(
    jenisTerapiUiState: JenisTerapiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (JenisTerapi) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (jenisTerapiUiState) {
        is JenisTerapiUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is JenisTerapiUiState.Success -> {
            if (jenisTerapiUiState.jenisTerapi.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Jenis Terapi", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                JenisTerapiLayout(
                    jenisTerapi = jenisTerapiUiState.jenisTerapi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_jenis_terapi) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is JenisTerapiUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun JenisTerapiLayout(
    jenisTerapi: List<JenisTerapi>,
    modifier: Modifier = Modifier,
    onDetailClick: (JenisTerapi) -> Unit,
    onDeleteClick: (JenisTerapi) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(jenisTerapi) { jenisTerapi ->
            JenisTerapiCard(
                jenisTerapi = jenisTerapi,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(jenisTerapi) },
                onDeleteClick = { onDeleteClick(jenisTerapi) }
            )
        }
    }
}

@Composable
fun JenisTerapiCard(
    jenisTerapi: JenisTerapi,
    modifier: Modifier = Modifier,
    onDeleteClick: (JenisTerapi) -> Unit = {},
    onEditClick: (JenisTerapi) -> Unit = {}
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
                    text = jenisTerapi.nama_jenis_terapi,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))

                IconButton(onClick = { onDeleteClick(jenisTerapi) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Jenis Terapi",
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                IconButton(onClick = { onEditClick(jenisTerapi) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Jenis Terapi",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Divider()

            Column {
                Text(
                    text = "ID Jenis Terapi: ${jenisTerapi.id_jenis_terapi}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Nama Jenis Terapi: ${jenisTerapi.nama_jenis_terapi}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Deskripsi: ${jenisTerapi.deskripsi_terapi}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
