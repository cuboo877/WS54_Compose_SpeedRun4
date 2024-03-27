package com.example.ws54_compose_speedrun4.widget

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import com.example.ws54_compose_speedrun4.service.SimpleNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object HomeAppBar {
    @Composable
    fun build(title:String, scope:CoroutineScope, drawerState: DrawerState){
        TopAppBar(
            title = { Text(text = title)},
            navigationIcon = {
                IconButton(onClick = {scope.launch { drawerState.open() }}) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                }
            }
        )
    }
}