package com.example.ws54_compose_speedrun4.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ws54_compose_speedrun4.service.SimpleNavController
import com.example.ws54_compose_speedrun4.widget.BackAppBar
import com.example.ws54_compose_speedrun4.R
import com.example.ws54_compose_speedrun4.service.getTranslated
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

object RegionPage {
    @Composable
    fun build(navController: SimpleNavController, previousData:Map<String,Any> ,allData:Map<String,Any>){
        val cityDataList = allData.values.toList() as List<Map<String,Any>>
        val openDialog = remember {
            mutableStateOf(false)
        }
        val unselect = remember {
            mutableStateOf(cityDataList)
        }
        val select = remember {
            mutableStateOf(listOf<Map<String,Any>>())
        }
        Scaffold(
            topBar = { BackAppBar.build(navController = navController, previousData = previousData) },
            floatingActionButton ={ FloatingActionButton(onClick = { openDialog.value = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                if(openDialog.value){
                    AlertDialog(
                        confirmButton = {},
                        onDismissRequest = { openDialog.value = false },
                        title = { Text(text = stringResource(id = R.string.add_a_region))},
                        text = {
                            val lazyListState = rememberLazyListState()
                            LazyColumn(state = lazyListState){
                                items(unselect.value.size){
                                    val city = unselect.value[it]
                                    val current = city.get("current") as Map<String,Any>
                                    Text(text = getTranslated(value = current.get("name")), modifier = Modifier.clickable {
                                        unselect.value = unselect.value.minus(city)
                                        select.value = select.value.plus(city)
                                        openDialog.value = false
                                    })
                                }
                            }
                        }
                    )
                }
            }}
        ) {
            it
            val dataList = remember {
                mutableStateListOf<Region>()
            }

            for (region in select.value) {
                val data = region.get("current") as Map<String,Any>
                dataList.add(Region(
                    name = getTranslated(value = data.get("name").toString()),
                    lat = data.get("lat") as Double,
                    lon = data.get("lon") as Double,
                    description = getTranslated(value = data.get("description")),
                    temp =( data.get("temp_c") as Double).toInt(),
                    rain = (data.get("daily_chance_of_rain") as Double).toInt()
                ))
            }

            val userAgent = "test"
            Configuration.getInstance().userAgentValue = userAgent
            AndroidView(factory = {
                context ->
                MapView(context).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    controller.setZoom(9.5)
                    controller.setCenter(GeoPoint(23.5,121.5))
                }
            },
                update = {
                    mapView ->
                    for(region in dataList){
                        val marker = Marker(mapView)
                        marker.position = GeoPoint(region.lat, region.lon)
                        marker.title = region.name
                        marker.snippet = "${region.temp}C / ${region.description} / ${region.rain}%"
                        marker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM)
                        mapView.overlays.add(marker)
                        marker.setOnMarkerClickListener{
                            it, view ->
                            marker.showInfoWindow()
                            true
                        }
                    }
                }
            )
        }
    }
}

data class Region(
    val name:String,
    val lat:Double,
    val lon:Double,
    val description:String,
    val temp:Int,
    val rain:Int
)