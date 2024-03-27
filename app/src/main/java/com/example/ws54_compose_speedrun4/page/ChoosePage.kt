package com.example.ws54_compose_speedrun4.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ws54_compose_speedrun4.MainActivity
import com.example.ws54_compose_speedrun4.service.SimpleNavController
import com.example.ws54_compose_speedrun4.service.getTranslated

object ChoosePage {
    @Composable
    fun build(navController: SimpleNavController, allData:Map<String,Any>){
        val cityDataList = allData.values.toList() as List<Map<String,Any>>
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(15.dp)
        ){
            items(cityDataList.size){
                val current = cityDataList[it].get("current") as Map<String,Any>
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(35.dp))
                    .background(Color.LightGray)
                    .clickable {
                        navController.push(MainActivity.Screen.Home, cityDataList[it])
                    }, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
                )
                {
                    Text(text = getTranslated(value = current.get("name")))

                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}