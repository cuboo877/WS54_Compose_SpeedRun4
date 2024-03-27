package com.example.ws54_compose_speedrun4.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ws54_compose_speedrun4.service.SimpleNavController
import com.example.ws54_compose_speedrun4.service.getIconId
import com.example.ws54_compose_speedrun4.service.getTranslated
import com.example.ws54_compose_speedrun4.widget.HomeAppBar
import com.example.ws54_compose_speedrun4.widget.NavDrawerContent

object HomePage {
    @Composable
    fun build(navController: SimpleNavController, cityData:Map<String,Any>){
        val scope = rememberCoroutineScope()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val current = cityData.get("current") as Map<String,Any>
        val forecast = cityData.get("forecast") as Map<String,Any>
        val hours = forecast.get("hourly") as List<Map<String,Any>>
        val days = forecast.get("day") as List<Map<String,Any>>
        ModalDrawer(drawerState = drawerState,drawerContent =  {NavDrawerContent.build(
            navController = navController,
            scope = scope,
            drawerState = drawerState,
            previousData = cityData
        )}) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { HomeAppBar.build(title = getTranslated(value = current.get("name")), scope = scope, drawerState = drawerState)}
            ) {
                it
                val lazyListState = rememberLazyListState()
                LazyColumn(
                    Modifier.fillMaxWidth(),
                    state = lazyListState,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(15.dp)
                ){
                    item{
                        currentTempDisplay(current)
                        Spacer(modifier = Modifier.height(20.dp))
                        hourlyTempDisplay(hours)
                        Spacer(modifier = Modifier.height(20.dp))
                        dailyTempDisplay(days)
                        Spacer(modifier = Modifier.height(20.dp))
                        pm25Display(current)
                    }
                }
            }
        }
    }

    @Composable
    fun currentTempDisplay(current:Map<String,Any>){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Text(text = "${current.get("temp_c")}C", fontSize = 40.sp)
                Image(painter = painterResource(id = getIconId(value = current.get("description"))), contentDescription = null,Modifier.size(60.dp))
            }
            Text(text = "${current.get("maxTemp_c")}C / ${current.get("minTemp_c")}C", fontSize = 40.sp)
            Text(text = getTranslated(value = current.get("description")), fontSize = 40.sp)
        }
    }

    @Composable
    fun hourlyTempDisplay(hours:List<Map<String,Any>>){
        val lazyListState = rememberLazyListState()
        LazyRow(state = lazyListState, modifier = Modifier
            .height(155.dp)
            .clip(RoundedCornerShape(35.dp))
            .background(
                Color.LightGray
            )){
            items(hours.size){
                _buildHourColumn(hours[it])
            }
        }
    }

    @Composable
    fun _buildHourColumn(data:Map<String,Any>){
        Column(modifier = Modifier.padding( PaddingValues(10.dp)), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = data.get("time").toString())
            Image(painter = painterResource(id = getIconId(value = data.get("description"))), contentDescription = null,Modifier.size(60.dp))
            Text(text = "${data.get("daily_chance_of_rain")}%")
            Text(text = "${data.get("temp_c")}C")
        }
    }

    @Composable
    fun dailyTempDisplay(days:List<Map<String,Any>>){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(35.dp))
                .background(
                    Color.LightGray
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            days.forEach{
                _buildDayDataRow(it)
            }
        }


    }
    @Composable
    fun _buildDayDataRow(day:Map<String,Any>){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = day.get("date").toString())
            Image(painter = painterResource(id = getIconId(value = day.get("description"))), contentDescription = null, Modifier.size(45.dp))
            Text(text = "${day.get("maxTemp_c")}C / ${day.get("minTemp_c")}C")
            Text(text = "${day.get("daily_chance_of_rain")}%")
        }
    }

    @Composable
    fun pm25Display(current:Map<String,Any>){
        Column(
            modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(35.dp))
            .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(text = "PM25")
            Divider(thickness = 3.dp, modifier = Modifier.fillMaxWidth(0.9f))
            Text(text = current.get("pm25").toString(), fontSize = 40.sp)
        }
    }
}