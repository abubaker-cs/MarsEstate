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

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

// Base URL
private const val BASE_URL = "https://mars.udacity.com/"

// I am using Retrofit Builder with ScalarsConverterFactory and the Base URL
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

// Interface = We will define an interface that explains how retrofit talks to our web server using HTTP requests.
// I am implementing the MarsAPIService interface with @GET getProperties returning a String
interface MarsApiService {

    @GET("realestate")
    fun getProperties():
            Call<String>

}

// We are exposing our retrofit service to the rest of the application using a public object called MarsAPI
// Creating the MarsAPI object using Retrofit to implement the MarsAPIService
object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}