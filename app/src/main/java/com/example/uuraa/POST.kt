package com.example.uuraa

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
var TOKEN = ""
class POST {
    companion object{
        val url1 = "https://api.cfif31.ru/tokens/Auth?username=user&password=user"
        val url2 = "https://api.cfif31.ru/tokens/Poets"
        val scope = MainScope()
        fun getToken(onResponse:(token:String)->Unit){
            scope.launch(Dispatchers.IO){
                (URL(url1).openConnection() as HttpURLConnection).run {
                    requestMethod = "POST"
                    doOutput = true
                    addRequestProperty("Content-Type","application/json")
                    connect()
                    val response = inputStream.bufferedReader().readText()
                    val array = JSONObject(response)
                    val token = array.getString("access_token")
                    scope.launch(Dispatchers.Main){
                        onResponse(token)
                    }
                }
            }

        }
        fun getUser(context: Context){
            scope.launch(Dispatchers.IO){
                var user = JSONObject()
                user.put("id",6)
                user.put("firstName","GD")
                user.put("middleName","GOD")
                user.put("lastName","GOD")
                user.put("description","PROSTO UCHU ATO ZA DEN' DO CHEMPIONATA")
                (URL(url2+"/6").openConnection() as HttpURLConnection).run {
                    requestMethod = "PUT"
                    doOutput = true
                    addRequestProperty("Content-Type","application/json")
                    addRequestProperty("Authorization","bearer $TOKEN")
                    val writer = outputStream.bufferedWriter()
                    writer.write(user.toString())
                    writer.flush()
                    outputStream.flush()
                    connect()

                    val error = responseCode
                    val message = responseMessage
                    scope.launch(Dispatchers.Main){
                        Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}