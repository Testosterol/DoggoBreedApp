package com.testosterolapp.dogbreeds.serverCommunication

import android.content.Context
import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.testosterolapp.dogbreeds.util.DbUtil.Companion.launchCoroutineForInsertBreedDataIntoDatabase
import com.testosterolapp.dogbreeds.util.DbUtil.Companion.launchCoroutineForInsertBreedPicturesDataIntoDatabase
import org.json.JSONException
import org.json.JSONObject

class GetServerData {

    fun getBreedsData(context: Context?) {
    val strReq: StringRequest = object : StringRequest(
        Method.GET, Url.URL_DATA,
        Response.Listener { response: String? ->

            // server response
            var jsonObj: JSONObject? = null
            try {
                jsonObj = JSONObject(response!!)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            launchCoroutineForInsertBreedDataIntoDatabase(jsonObj!!, context!!)
        },
        Response.ErrorListener { error: VolleyError ->
            Log.e(TAG, "Failed to retrieve server data$error")
        }) {
        override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
            Log.i(TAG, "Status code from the server: " + response.statusCode)
            return super.parseNetworkResponse(response)
        }

        override fun getBodyContentType(): String {
            return "application/json; charset=utf-8"
        }
    }
    AppController.getInstance(context!!)!!.addToRequestQueue(strReq)
}

    fun getBreedPicturesData(context: Context?, breedName: String, fk: Int) {
        val strReq: StringRequest = object : StringRequest(
            Method.GET, "https://dog.ceo/api/breed/$breedName/images",
            Response.Listener { response: String? ->

                // server response
                var jsonObj: JSONObject? = null
                try {
                    jsonObj = JSONObject(response!!)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                launchCoroutineForInsertBreedPicturesDataIntoDatabase(jsonObj!!, context!!, fk)
            },
            Response.ErrorListener { error: VolleyError ->
                Log.e(TAG, "Failed to retrieve server data$error")
            }) {
            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                Log.i(TAG, "Status code from the server: " + response.statusCode)
                return super.parseNetworkResponse(response)
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }
        AppController.getInstance(context!!)!!.addToRequestQueue(strReq)
    }

    companion object {
        val TAG: String = GetServerData::class.java.simpleName
    }
}