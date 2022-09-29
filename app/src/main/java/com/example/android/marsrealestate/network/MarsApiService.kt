/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.network

/**
 * TODO 01 Network Layer:
 * The API that the ViewModel uses to communicate with the web service.
 *
 * Retrofit Basics:
 * What retrofit needs to build our Network API
 * 1. Base URL of our web service
 * 2. Convertor factory that allows Retrofit to return the server response in a useful format
 */

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Base URL
private const val BASE_URL = "https://mars.udacity.com/"

// TODO 05 - Moshi Builder to create a Moshi object with the KotlinJsonAdapterFactory


/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// I am using Retrofit Builder with ScalarsConverterFactory and the Base URL
// TODO 06 - Use MoshiConverterFactory.create(moshi) instead of ScalarsConverterFactory.create()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// Interface = We will define an interface that explains how retrofit talks to our web server using HTTP requests.
// I am implementing the MarsAPIService interface with @GET getProperties returning a String
// TODO 07 - Use Call<List<MarsProperty>> instead of Call<String>

/**
 * Returns a Coroutine [List] of [MarsProperty] which can be fetched with await() if in a Coroutine scope.
 * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
 * HTTP method
 */
interface MarsApiService {

    @GET("realestate")
    suspend fun getProperties(@Query("filter") type: String):
            List<MarsProperty>

}

// We are exposing our retrofit service to the rest of the application using a public object called MarsAPI
// Creating the MarsAPI object using Retrofit to implement the MarsAPIService

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}

/**
 * It defines constants to match the query values our web service expects.
 */
enum class MarsApiFilter(val value: String) {
    SHOW_RENT("rent"),
    SHOW_BUY("buy"),
    SHOW_ALL("all")
}
