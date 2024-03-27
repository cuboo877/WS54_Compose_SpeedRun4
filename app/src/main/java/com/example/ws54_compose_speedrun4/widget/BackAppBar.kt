package com.example.ws54_compose_speedrun4.widget

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.ws54_compose_speedrun4.MainActivity
import com.example.ws54_compose_speedrun4.R
import com.example.ws54_compose_speedrun4.service.SimpleNavController

object BackAppBar {
    @Composable
    fun build(navController: SimpleNavController,previousData:Map<String,Any>){
        TopAppBar(
            title = { Text(text = stringResource(id = androidx.compose.ui.R.string.navigation_menu))},
            navigationIcon = {
                IconButton(onClick = { navController.push(MainActivity.Screen.Home, previousData) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
    }
    
}