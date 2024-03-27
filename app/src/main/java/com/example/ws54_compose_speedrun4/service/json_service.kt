package com.example.ws54_compose_speedrun4.service

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

fun readJsonFromAssets(context:Context, name:String):String{
    return context.assets.open(name).bufferedReader().use { it.readText() }
}

fun jsonToMap(jsonString:String):Map<String,Any>{
    val gson = Gson()
    val type = object :TypeToken<Map<String,Any>>(){}.type
    return gson.fromJson(jsonString, type)
}