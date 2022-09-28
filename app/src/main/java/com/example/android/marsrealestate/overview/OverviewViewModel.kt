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

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // TODO 10 - rename _response to _status
    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val status: LiveData<String>
        get() = _status

    // TODO 11 -
    // LiveData Mars Property with an internal Mutable and external LiveData
    private val _property = MutableLiveData<MarsProperty>()

    val property: LiveData<MarsProperty>
        get() = _property


    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }


    // TODO 12 -
    /**
     * Sets the value of the response LiveData to the Mars API status or the successful number of
     * Mars properties retrieved.
     */
    private fun getMarsRealEstateProperties() {

        // Coroutine
        viewModelScope.launch {
            try {
                val listResult = MarsApi.retrofitService.getProperties()
                _status.value = "Success: ${listResult.size} Mars properties retrieved"
                // _response.value = "Success: ${listResult.size} Mars properties retrieved"

                if (listResult.size > 0) {
                    _property.value = listResult[0]
                }

            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }

    }
    // TO DO 08 - Callback<List<MarsProperty>> instead of Callback<String>
//    private fun getMarsRealEstateProperties() {
//        // TO DO 02 Call the MarsApi to enqueue the Retrofit request, implementing the callbacks
//        MarsApi.retrofitService.getProperties().enqueue(object : Callback<List<MarsProperty>> {
//            // Invoked for a received HTTP response.
//            override fun onResponse(
//                call: Call<List<MarsProperty>>,
//                response: Response<List<MarsProperty>>
//            ) {
//                _response.value = "Success: ${response.body()?.size} Mars properties retrieved"
//            }
//
//            // 404
//            override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
//                _response.value = "Failure: " + t.message
//            }
//
//        })
//    }
}
