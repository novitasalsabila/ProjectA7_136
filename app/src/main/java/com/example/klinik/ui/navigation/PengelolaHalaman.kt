package com.example.klinik.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.klinik.ui.view.*
import com.example.klinik.ui.view.JenisTerapi.DestinasiDetailJenisTerapi
import com.example.klinik.ui.view.JenisTerapi.DestinasiEditJenisTerapi
import com.example.klinik.ui.view.JenisTerapi.DestinasiHomeJenis
import com.example.klinik.ui.view.JenisTerapi.DestinasiJenisTerapiEntry
import com.example.klinik.ui.view.JenisTerapi.DetailJenisTerapiView
import com.example.klinik.ui.view.JenisTerapi.EntryJenisTerapiScreen
import com.example.klinik.ui.view.JenisTerapi.JenisTerapiScreen
import com.example.klinik.ui.view.JenisTerapi.UpdateJenisTerapiView
import com.example.klinik.ui.view.Pasien.DestinasiDetailPasien
import com.example.klinik.ui.view.Pasien.DestinasiEditPasien
import com.example.klinik.ui.view.Pasien.DestinasiHome
import com.example.klinik.ui.view.Pasien.DestinasiPasienEntry
import com.example.klinik.ui.view.Pasien.DetailPasienView
import com.example.klinik.ui.view.Pasien.EntryPasienScreen
import com.example.klinik.ui.view.Pasien.PasienScreen
import com.example.klinik.ui.view.Pasien.UpdatePasienView
import com.example.klinik.ui.view.SesiTerapis.DestinasiDetailSesiTerapi
import com.example.klinik.ui.view.SesiTerapis.DestinasiHomeSesi
import com.example.klinik.ui.view.SesiTerapis.DestinasiSesiEntry
import com.example.klinik.ui.view.SesiTerapis.DestinasiUpdateSesiTerapi
import com.example.klinik.ui.view.SesiTerapis.DetailSesiTerapiView
import com.example.klinik.ui.view.SesiTerapis.EntrySesiScreen
import com.example.klinik.ui.view.SesiTerapis.SesiTerapiScreen
import com.example.klinik.ui.view.SesiTerapis.SesiTerapiUpdateView
import com.example.klinik.ui.view.Terapis.DestinasiDetailTerapis
import com.example.klinik.ui.view.Terapis.DestinasiEditTerapis
import com.example.klinik.ui.view.Terapis.DestinasiHomeTerapis
import com.example.klinik.ui.view.Terapis.DestinasiTerapisEntry
import com.example.klinik.ui.view.Terapis.DetailTerapisView
import com.example.klinik.ui.view.Terapis.EntryTerapisScreen
import com.example.klinik.ui.view.Terapis.TerapisScreen
import com.example.klinik.ui.view.Terapis.UpdateTerapisView


@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiMain.route,
        modifier = Modifier
    ) {
        composable(route = DestinasiMain.route) {
            HomeApp(
                //DestinasiHome.route adalah layar tujuan
                onHalamanHomePasien = {
                    navController.navigate(DestinasiHome.route)
                },
                onHalamanHomeMK = {
                    navController.navigate(DestinasiHomeTerapis.route)
                },
                onHalamanHomeLKS = {
                    navController.navigate(DestinasiHome.route)
                },
                onHalamanHomeVDR = {
                    navController.navigate(DestinasiHome.route)
                },
                onHalamanHomeSesiTerapis = {
                    navController.navigate(DestinasiHomeSesi.route)
                },
                onHalamanHomeJenisTerapi = { // Tambahkan ini
                    navController.navigate(DestinasiHomeJenis.route)
                }
            )
        }
        composable(route = DestinasiHome.route) {
            PasienScreen(
                navController = navController,
                navigateToItemEntry = { navController.navigate(DestinasiPasienEntry.route) },
                onDetailClick = { id_pasien ->
                    navController.navigate("${DestinasiDetailPasien.route}/$id_pasien")}
            )
        }
        composable(
            route = DestinasiPasienEntry.route
        ) {
            EntryPasienScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                },
                navController = navController // Tambahkan ini
            )
        }
        composable(
            route = DestinasiDetailPasien.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailPasien.ID_PASIEN){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id_pasien = backStackEntry.arguments?.getString(DestinasiDetailPasien.ID_PASIEN)
            id_pasien?.let {
                DetailPasienView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { id_pasien ->
                        navController.navigate("${DestinasiEditPasien.route}/$id_pasien")
                        println(id_pasien)
                    },
                    navigateToEdit = { navController.navigate("${DestinasiEditPasien.route}/$it")}
                )
            }
        }

        composable(
            route = DestinasiEditPasien.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiEditPasien.ID_PASIEN) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_pasien = it.arguments?.getString(DestinasiEditPasien.ID_PASIEN)
            id_pasien?.let {id_pasien ->
                UpdatePasienView(
                    navigateBack = { navController.popBackStack() },
                )
            }
        }


        composable(route = DestinasiHomeTerapis.route) {
            TerapisScreen(
                navController = navController,
                navigateToItemEntry = { navController.navigate(DestinasiTerapisEntry.route) },
                onDetailClick = { id_terapis ->
                    navController.navigate("${DestinasiDetailTerapis.route}/$id_terapis")}
            )
        }
        //TERAPIS
        composable(
            route = DestinasiDetailTerapis.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailTerapis.ID_TERAPIS){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id_terapis = backStackEntry.arguments?.getString(DestinasiDetailTerapis.ID_TERAPIS)
            id_terapis?.let {
                DetailTerapisView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { id_terapis ->
                        navController.navigate("${DestinasiEditTerapis.route}/$id_terapis")
                        println(id_terapis)
                    }
                )
            }
        }
        composable(
            route = DestinasiEditTerapis.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiEditTerapis.ID_TERAPI) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_terapis = it.arguments?.getString(DestinasiEditTerapis.ID_TERAPI)
            id_terapis?.let {id_terapis ->
                UpdateTerapisView(
                    navigateBack = { navController.popBackStack() },
                )
            }
        }


        composable(
            route = DestinasiSesiEntry.route
        ) {
            EntrySesiScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeSesi.route) {
                        popUpTo(DestinasiHomeSesi.route) { inclusive = true }
                    }
                },
                navController = navController // Tambahkan ini
            )
        }

        composable(
            route = DestinasiTerapisEntry.route
        ) {
            EntryTerapisScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeTerapis.route) {
                        popUpTo(DestinasiHomeTerapis.route) { inclusive = true }
                    }
                },
                navController = navController // Tambahkan ini
            )
        }
        composable(route = DestinasiHomeJenis.route) { // Rute untuk Home Jenis Terapi
            JenisTerapiScreen(
                navController = navController,
                navigateToItemEntry = { navController.navigate(DestinasiJenisTerapiEntry.route) },
                onDetailClick = { id_jenis_terapi ->
                    navController.navigate("${DestinasiDetailJenisTerapi.route}/$id_jenis_terapi")}
            )
        }

        composable(
            route = DestinasiJenisTerapiEntry.route
        ) {
            EntryJenisTerapiScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeJenis.route) {
                        popUpTo(DestinasiHomeJenis.route) { inclusive = true }
                    }
                },
                navController = navController // Tambahkan ini
            )
        }

    //jenis terapi
        composable(
            route = DestinasiDetailJenisTerapi.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailJenisTerapi.ID_JENIS_TERAPI){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id_jenis_terapi = backStackEntry.arguments?.getString(DestinasiDetailJenisTerapi.ID_JENIS_TERAPI)
            id_jenis_terapi?.let {
                DetailJenisTerapiView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { id_jenis_terapi ->
                        navController.navigate("${DestinasiEditJenisTerapi.route}/$id_jenis_terapi")
                        println(id_jenis_terapi)
                    }
                )
            }
        }

        composable(
            route = DestinasiEditJenisTerapi.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiEditJenisTerapi.ID_JENIS_TERAPI) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_jenis_terapi = it.arguments?.getString(DestinasiEditJenisTerapi.ID_JENIS_TERAPI)
            id_jenis_terapi?.let {id_jenis_terapi ->
                UpdateJenisTerapiView(
                    navigateBack = { navController.popBackStack() },
                )
            }
        }
        composable(route = DestinasiHomeSesi.route) {
            SesiTerapiScreen(
                navController = navController,
                navigateToItemEntry = { navController.navigate(DestinasiSesiEntry.route) },
                onDetailClick = { id_sesi ->
                    navController.navigate("${DestinasiDetailSesiTerapi.route}/$id_sesi")}

            )
        }

        composable(
            route = DestinasiDetailSesiTerapi.routeWithArgs,
            arguments = listOf(navArgument(DestinasiDetailSesiTerapi.ID_SESI){
                type = NavType.StringType
            })
        ){ backStackEntry ->
            val id_sesi = backStackEntry.arguments?.getString(DestinasiDetailSesiTerapi.ID_SESI)
            id_sesi?.let {
                DetailSesiTerapiView(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onEditClick = { id_sesi ->
                        navController.navigate("${DestinasiUpdateSesiTerapi.route}/$id_sesi")
                        println(id_sesi)
                    }
                )
            }
        }

        composable(
            route = DestinasiUpdateSesiTerapi.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateSesiTerapi.ID_SESI) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_sesi = it.arguments?.getString(DestinasiUpdateSesiTerapi.ID_SESI)
            id_sesi?.let { id_sesi ->
                SesiTerapiUpdateView(
                    navigateBack = { navController.popBackStack() },
                )
            }
        }


//        composable(
//            route = DestinasiEdit.routeWithArgs,
//            arguments = listOf(navArgument(DestinasiEdit.idPasien){
//                type = NavType.StringType
//            })
//        ){
//            EditView(
//                navigateBack = {
//                    navController.popBackStack()
//                },
//                onNavigateUp = {
//                    navController.navigate(
//                        DestinasiEdit.route
//                    ){
//                        popUpTo(DestinasiHome.route){
//                            inclusive = true
//                        }
//                    }
//                }
//            )
//        }

    }
}
