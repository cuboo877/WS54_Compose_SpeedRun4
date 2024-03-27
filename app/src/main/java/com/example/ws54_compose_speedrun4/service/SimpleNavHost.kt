package com.example.ws54_compose_speedrun4.service

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ws54_compose_speedrun4.MainActivity
import com.example.ws54_compose_speedrun4.page.ChoosePage
import com.example.ws54_compose_speedrun4.page.HomePage
import com.example.ws54_compose_speedrun4.page.RegionPage

@Composable
fun simpleNavHost(navController: SimpleNavController, data:Map<String,Any>){
    Box(modifier = Modifier.fillMaxSize()){
        when(navController.currentScreen.value.first){
            MainActivity.Screen.Home -> HomePage.build(
                navController = navController,
                cityData = navController.currentScreen.value.second as Map<String, Any>
            )

            MainActivity.Screen.Region -> RegionPage.build(
                navController = navController,
                previousData = navController.currentScreen.value.second as Map<String, Any>,
                allData = data
            )

            MainActivity.Screen.Choose -> ChoosePage.build(
                navController = navController,
                allData = data
            )
        }
    }
}