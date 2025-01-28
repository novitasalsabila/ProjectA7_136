package com.example.klinik.ui.view.JenisTerapi

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klinik.ui.PenyediaViewModel
import com.example.klinik.ui.customwidget.TopAppBar
import com.example.klinik.ui.navigation.DestinasiNavigasi
import com.example.klinik.ui.viewmodel.JenisTerapi.UpdateJenisTerapiViewModel
import kotlinx.coroutines.launch

object DestinasiEditJenisTerapi : DestinasiNavigasi {
    override val route = "jenis_edit"
    override val titleRes = "Edit Jenis Terapi"
    const val ID_JENIS_TERAPI = "id_jenis_terapi"
    val routeWithArgs = "$route/{$ID_JENIS_TERAPI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateJenisTerapiView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateJenisTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val insertJenisTerapiUiState = viewModel.updateUiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiEditJenisTerapi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryJenisTerapiBody(
            insertUiState = insertJenisTerapiUiState,
            onJenisTerapiValueChange = { viewModel.updateInsertJenisTerapiState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateJenisTerapi()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}