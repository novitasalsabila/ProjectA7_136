package com.example.klinik.ui.view.Terapis

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
import com.example.klinik.ui.viewmodel.terapisVM.UpdateTerapisViewModel
import kotlinx.coroutines.launch

object DestinasiEditTerapis : DestinasiNavigasi {
    override val route = "terapis_edit"
    override val titleRes = "Edit Terapis "
    const val ID_TERAPI = "id_terapis"
    val routeWithArgs = "$route/{$ID_TERAPI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTerapisView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTerapisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val insertTerapisUiState = viewModel.updateUiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiEditTerapis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryTerapisBody(
            insertUiState = insertTerapisUiState,
            onTerapisValueChange = { viewModel.updateInsertTerapisState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTerapis()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}