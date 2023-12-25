package com.example.note.router

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.note.router.Router.HOME
import com.example.note.router.Router.NOTE_DETAIL
import com.example.note.router.Router.NOTE_SEARCH
import com.example.note.ui.page.home.HomeNavigator
import com.example.note.ui.page.note_detail.NoteDetailNavigator
import com.example.note.ui.page.search.NoteSearchNavigator

object Router {
    const val HOME = "home"
    const val NOTE_DETAIL = "noteDetail"
    const val NOTE_SEARCH = "noteSearch"
}

@Composable
fun NoteAppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = HOME,
    ) {
        composable(HOME) {
            HomeNavigator(

            )
        }
        composable(NOTE_DETAIL) {
            NoteDetailNavigator(

            )
        }
        composable(NOTE_SEARCH) {
            NoteSearchNavigator(

            )
        }
    }
}