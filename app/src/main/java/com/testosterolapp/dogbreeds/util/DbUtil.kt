package com.testosterolapp.dogbreeds.util

import android.content.Context
import android.util.Log
import com.testosterolapp.dogbreeds.data.Breed
import com.testosterolapp.dogbreeds.data.BreedPicture
import com.testosterolapp.dogbreeds.database.DaoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException




class DbUtil {

    companion object{

        fun launchCoroutineForInsertBreedDataIntoDatabase(dataJsonObject: JSONObject, context: Context){
            CoroutineScope(Dispatchers.IO).launch {
                insertBreedData(dataJsonObject, context)
            }
        }

        private suspend fun insertBreedData(dataJsonObject: JSONObject, context: Context) {
            val daoRepository = DaoRepository(context)
            val jsonArr = dataJsonObject.getJSONObject("message")
            val i: Iterator<String> = jsonArr.keys()
            var fk = 0
            while (i.hasNext()) {
                val key = i.next()
                try {
                    val value: JSONArray = jsonArr.get(key) as JSONArray
                    if(value.length() == 0){
                        fk++
                        daoRepository.insertBreed(Breed(key))
                        daoRepository.fetchBreedPicturesData(fk, key)
                    }else{
                        for(j in 0 until value.length()){
                            fk++
                            val specificBreedName = key + "/" + value.get(j).toString()
                            daoRepository.insertBreed(Breed(specificBreedName))
                            daoRepository.fetchBreedPicturesData(fk, specificBreedName)
                        }
                    }
                } catch (e: JSONException) {
                    Log.d("DbUtil", "JSONException: $e")
                }
            }
        }

        fun launchCoroutineForInsertBreedPicturesDataIntoDatabase(dataJsonObject: JSONObject,
                                                                  context: Context, fk: Int){
            CoroutineScope(Dispatchers.IO).launch {
                insertBreedPicturesData(dataJsonObject, context, fk)
            }
        }

        private suspend fun insertBreedPicturesData(dataJsonObject: JSONObject, context: Context,
                                                    fk: Int) {
            val daoRepository = DaoRepository(context)
            val jsonArr = dataJsonObject.getJSONArray("message")
            for(j in 0 until jsonArr.length()){
                daoRepository.insertPictureOfBreed(BreedPicture(fk, jsonArr.get(j).toString(), false))
            }
        }
    }
}